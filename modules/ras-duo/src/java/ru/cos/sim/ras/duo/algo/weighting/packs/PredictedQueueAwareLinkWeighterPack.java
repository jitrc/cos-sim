package ru.cos.sim.ras.duo.algo.weighting.packs;

import java.util.List;

import ru.cos.sim.agents.tlns.data.RegularTLNData;
import ru.cos.sim.ras.duo.DataAggregator;
import ru.cos.sim.ras.duo.DataSources;
import ru.cos.sim.ras.duo.WeightProvider;
import ru.cos.sim.ras.duo.Weighter;
import ru.cos.sim.ras.duo.algo.Solution;
import ru.cos.sim.ras.duo.algo.StandardWeightProvider;
import ru.cos.sim.ras.duo.algo.StandardWeightProvider.LinkWeighterPack;
import ru.cos.sim.ras.duo.algo.aggregating.ConstantAggregator;
import ru.cos.sim.ras.duo.algo.aggregating.FlowAggregator;
import ru.cos.sim.ras.duo.algo.aggregating.PerItemDataCollector;
import ru.cos.sim.ras.duo.algo.aggregating.PredictedQueueLengthAggregator;
import ru.cos.sim.ras.duo.algo.weighting.AdditiveWeighter;
import ru.cos.sim.ras.duo.algo.weighting.QueueingTimeWeighter;
import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.digraph.roadnet.LinkEdge;
import ru.cos.sim.ras.duo.digraph.roadnet.TransitionEdge;
import ru.cos.sim.ras.duo.helper.RegularTrafficLightsCalculator;
import ru.cos.sim.ras.duo.helper.TransitionEnumerator;

public class PredictedQueueAwareLinkWeighterPack extends WeighterPack implements StandardWeightProvider.LinkWeighterPack, StandardWeightProvider.LinkReportingPack {
	public PredictedQueueAwareLinkWeighterPack(Solution solution, LinkWeighterPack base, WeightProvider travelTimeWeightProvider) {
		super(solution);
		this.base = base;
		this.travelTimeWeightProvider = travelTimeWeightProvider;
	}
	
	private LinkWeighterPack base;
	
	private WeightProvider travelTimeWeightProvider;
	protected WeightProvider getTravelTimeWeightProvider() {
		return travelTimeWeightProvider;
	}
	
	private PerItemDataCollector<Edge, DataSources> queueLengthAggregators = new PerItemDataCollector<Edge, DataSources>();
	private PerItemDataCollector<Edge, DataSources> flowAggregators = new PerItemDataCollector<Edge, DataSources>();
	
	@Override
	public void collectData(DataSources medium) {
		base.collectData(medium);
		flowAggregators.collectData(medium.getEdge(), medium);
		queueLengthAggregators.collectData(medium.getEdge(), medium);
	}
	
	private float getFlowCapacity(LinkEdge link) {
		List<TransitionEdge> transitions = TransitionEnumerator.getOutgoingTransitions(link);
		if (transitions.size() > 0 && transitions.get(0).getLightsNetworkData() instanceof RegularTLNData) {
			RegularTLNData lights = (RegularTLNData)transitions.get(0).getLightsNetworkData();
			Iterable<Integer> lightIds = TransitionEnumerator.getTrafficLightsIds(transitions);
			float phaseRatio = RegularTrafficLightsCalculator.getPhaseRatio(lights, lightIds);
			return 1 / (1 + phaseRatio) * TransitionEnumerator.getUniqueDestinations(transitions).size() * getCommonParameters().getQueuing().getDestinationFlowCapacity();	
		} else 
			return Float.POSITIVE_INFINITY;
	}
 
	private PredictedQueueLengthAggregator createQueueLengthAggregator(LinkEdge link) {
		FlowAggregator flowAggregator = new FlowAggregator(link.getLinkId(), getCommonParameters());
		flowAggregators.add(link, flowAggregator);
		return createQueueLengthAggregator(link, flowAggregator, new ConstantAggregator(getFlowCapacity(link)));
	}
	
	protected PredictedQueueLengthAggregator createQueueLengthAggregator(LinkEdge link, DataAggregator flowAggregator, DataAggregator flowCapacityAggregator) {
		return new PredictedQueueLengthAggregator(link, flowAggregator, flowCapacityAggregator, getTravelTimeWeightProvider(), getCommonParameters());
	}
	
	private Weighter createPredictedQueueingTimeWeighter(LinkEdge link) {
		PredictedQueueLengthAggregator queueLengthAggregator = createQueueLengthAggregator(link);
		queueLengthAggregators.add(link, queueLengthAggregator);
		return new QueueingTimeWeighter(link, queueLengthAggregator, getCommonParameters().getQueuing().getQueuePhaseSpeed());
	}

	@Override
	public Weighter createWeighter(LinkEdge link) {
		AdditiveWeighter weighter = new AdditiveWeighter();
		weighter.add(base.createWeighter(link));
		weighter.add(createPredictedQueueingTimeWeighter(link));
		return weighter;
	}
	
	public static final int REPORT_TYPE_QUEUE_LENGTH = 0;
	public static final int REPORT_TYPE_FLOW = 1;
	
	@Override
	public float report(LinkEdge link, int reportType) {
		Object aggregator = null;
		switch (reportType) {
			case REPORT_TYPE_QUEUE_LENGTH:
				aggregator = queueLengthAggregators.getCollector(link); break;
			case REPORT_TYPE_FLOW:
				aggregator = flowAggregators.getCollector(link); break;
		}
		
		if (aggregator != null)
			return ((DataAggregator)aggregator).getAggregate();
		else
			return -1;
	}
}
