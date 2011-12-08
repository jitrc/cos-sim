package ru.cos.sim.ras.duo.algo.weighting;

import java.util.List;

import ru.cos.sim.agents.tlns.data.RegularTLNData;
import ru.cos.sim.ras.duo.DataAggregator;
import ru.cos.sim.ras.duo.PathExtensions;
import ru.cos.sim.ras.duo.Weighter;
import ru.cos.sim.ras.duo.digraph.roadnet.LinkEdge;
import ru.cos.sim.ras.duo.digraph.roadnet.TransitionEdge;
import ru.cos.sim.ras.duo.helper.RegularTrafficLightsCalculator;
import ru.cos.sim.ras.duo.helper.TransitionEnumerator;

public class QueueingTimeWeighter implements Weighter {
	public QueueingTimeWeighter(LinkEdge link, DataAggregator queueLengthAggregator, float phaseSpeed) {
		this.queueLengthAggregator = queueLengthAggregator;
		this.phaseRatio = getPhaseRatio(link);
		this.phaseSpeed = phaseSpeed;
	}
	
	private DataAggregator queueLengthAggregator;
	
	private float phaseRatio;
	private float phaseSpeed;
	
	protected float getPhaseRatio(LinkEdge link) {
		List<TransitionEdge> transitions = TransitionEnumerator.getOutgoingTransitions(link);
		if (transitions.size() > 0 && transitions.get(0).getLightsNetworkData() instanceof RegularTLNData) {
			RegularTLNData lights = (RegularTLNData)transitions.get(0).getLightsNetworkData();
			Iterable<Integer> lightIds = TransitionEnumerator.getTrafficLightsIds(transitions);
			return RegularTrafficLightsCalculator.getPhaseRatio(lights, lightIds);
		} else
			return 1;
	}
	
	@Override
	public float getWeight(PathExtensions extensions) {
		return queueLengthAggregator.getAggregate() * phaseRatio  / phaseSpeed;
	}

	@Override
	public String toString() {
		return "Weight: " + getWeight(null);
	}
}
