package ru.cos.sim.ras.duo.algo.weighting.packs;

import ru.cos.sim.ras.duo.DataAggregator;
import ru.cos.sim.ras.duo.DataSources;
import ru.cos.sim.ras.duo.Weighter;
import ru.cos.sim.ras.duo.algo.Solution;
import ru.cos.sim.ras.duo.algo.StandardWeightProvider;
import ru.cos.sim.ras.duo.algo.StandardWeightProvider.LinkWeighterPack;
import ru.cos.sim.ras.duo.algo.aggregating.PerItemDataCollector;
import ru.cos.sim.ras.duo.algo.aggregating.QueueLengthAggregator;
import ru.cos.sim.ras.duo.algo.weighting.AdditiveWeighter;
import ru.cos.sim.ras.duo.algo.weighting.QueueingTimeWeighter;
import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.digraph.roadnet.LinkEdge;

public class QueueAwareLinkWeighterPack extends WeighterPack implements StandardWeightProvider.LinkWeighterPack, StandardWeightProvider.LinkReportingPack {
	public QueueAwareLinkWeighterPack(Solution solution, LinkWeighterPack base) {
		super(solution);
		this.base = base;
	}
	
	private LinkWeighterPack base;

	private PerItemDataCollector<Edge, DataSources> queueLengthAggregators = new PerItemDataCollector<Edge, DataSources>();
	
	@Override
	public void collectData(DataSources medium) {
		base.collectData(medium);
		queueLengthAggregators.collectData(medium.getEdge(), medium);
	}

	protected QueueLengthAggregator createQueueLengthAggregator(LinkEdge link) {
		return new QueueLengthAggregator(getCommonParameters());
	}
	
	private Weighter createQueueingTimeWeighter(LinkEdge link) {
		QueueLengthAggregator queueLengthAggregator = createQueueLengthAggregator(link); 
		queueLengthAggregators.add(link, queueLengthAggregator);
		return new QueueingTimeWeighter(link, queueLengthAggregator, getCommonParameters().getQueuing().getQueuePhaseSpeed());
	}
	
	@Override
	public Weighter createWeighter(LinkEdge link) {
		AdditiveWeighter weighter = new AdditiveWeighter();
		weighter.add(base.createWeighter(link));
		weighter.add(createQueueingTimeWeighter(link));
		return weighter;
	}

	public static final int REPORT_TYPE_QUEUE_LENGTH = 0;
	
	@Override
	public float report(LinkEdge link, int reportType) {
		Object aggregator = null;
		switch (reportType) {
			case REPORT_TYPE_QUEUE_LENGTH:
				aggregator = queueLengthAggregators.getCollector(link); break;
		}
		if (aggregator != null)
			return ((DataAggregator)aggregator).getAggregate();
		else
			return -1;
	}
}
