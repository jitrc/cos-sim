package ru.cos.sim.ras.duo.algo.weighting.packs;

import ru.cos.sim.ras.duo.DataAggregator;
import ru.cos.sim.ras.duo.WeightProvider;
import ru.cos.sim.ras.duo.algo.Solution;
import ru.cos.sim.ras.duo.algo.StandardWeightProvider.LinkWeighterPack;
import ru.cos.sim.ras.duo.algo.aggregating.PatchingQueueLengthAggregator;
import ru.cos.sim.ras.duo.algo.aggregating.PredictedQueueLengthAggregator;
import ru.cos.sim.ras.duo.digraph.roadnet.LinkEdge;

public class PatchingQueueAwareLinkWeighterPack extends PredictedQueueAwareLinkWeighterPack {

	public PatchingQueueAwareLinkWeighterPack(Solution solution, LinkWeighterPack base, WeightProvider travelTimeWeightProvider) {
		super(solution, base, travelTimeWeightProvider);
	}

	@Override
	protected PredictedQueueLengthAggregator createQueueLengthAggregator(LinkEdge link, DataAggregator flowAggregator, DataAggregator flowCapacityAggregator) {
		return new PatchingQueueLengthAggregator(link, flowAggregator, flowCapacityAggregator, getTravelTimeWeightProvider(), getCommonParameters());
	}
}
