package ru.cos.sim.ras.duo.algo;

import ru.cos.sim.ras.duo.Parameters;
import ru.cos.sim.ras.duo.PathFinder;
import ru.cos.sim.ras.duo.RoadDigraph;
import ru.cos.sim.ras.duo.algo.pathfinding.BestOnePathSelector;
import ru.cos.sim.ras.duo.algo.pathfinding.PlainDijkstraPathFinder;
import ru.cos.sim.ras.duo.algo.pathfinding.ProbabilisticPathSelector;
import ru.cos.sim.ras.duo.algo.weighting.packs.PatchingQueueAwareLinkWeighterPack;
import ru.cos.sim.ras.duo.algo.weighting.packs.PredictedQueueAwareLinkWeighterPack;
import ru.cos.sim.ras.duo.algo.weighting.packs.QueueAwareLinkWeighterPack;
import ru.cos.sim.ras.duo.algo.weighting.packs.SegmentedAverageSpeedLinkWeighterPack;
import ru.cos.sim.ras.duo.dijkstra.DijkstraProcessor.PathSelector;

public class DefaultSolution extends StandardSolution {

	private static final int QUEUE_MODE_USUAL = 0;
	private static final int QUEUE_MODE_PATCHING = 1;
	private static final int QUEUE_MODE_PREDICTION = 2;
	
	private static final int QUEUE_MODE = QUEUE_MODE_PREDICTION;
	
	public DefaultSolution(RoadDigraph graph, Parameters parameters) {
		super(graph, parameters);
		
		StandardWeightProvider weightProvider = new StandardWeightProvider();
		
		this.queueWeightingPack = new QueueAwareLinkWeighterPack(this, new SegmentedAverageSpeedLinkWeighterPack(this));
		weightProvider.setLinkWeighterFactory(queueWeightingPack);
		
		if (QUEUE_MODE == QUEUE_MODE_PREDICTION) {
			this.additionalQueueWeightingPack = new PredictedQueueAwareLinkWeighterPack(this, queueWeightingPack, weightProvider);
			weightProvider.setLinkWeighterFactory(additionalQueueWeightingPack);
		}

		if (QUEUE_MODE == QUEUE_MODE_PATCHING) {
			this.additionalQueueWeightingPack = new PatchingQueueAwareLinkWeighterPack(this, queueWeightingPack, weightProvider);
			weightProvider.setLinkWeighterFactory(additionalQueueWeightingPack);
		}
		
		this.weightProvider = weightProvider;
		
		PathSelector.Factory pathSelector;
		if (parameters.getTemperatureProfile().getMaxTemperature() > 0)
			pathSelector = new ProbabilisticPathSelector.BestPathSpeedCongestionFactory(weightProvider, parameters);
		else
			pathSelector = new BestOnePathSelector.Factory();
		
		this.pathFinder = new PlainDijkstraPathFinder(graph, weightProvider, pathSelector);
	}
	
	private StandardWeightProvider weightProvider;
	private PathFinder pathFinder;
	
	private QueueAwareLinkWeighterPack queueWeightingPack;
	private PredictedQueueAwareLinkWeighterPack additionalQueueWeightingPack;
	
	@Override
	protected StandardWeightProvider getWeightProvider() {
		return weightProvider;
	}
	
	@Override
	protected PathFinder getPathFinder() {
		return pathFinder;
	}

	public static class Factory implements Solution.Factory {
		@Override
		public Solution createSolution(RoadDigraph graph, Parameters parameters) {
			return new DefaultSolution(graph, parameters);
		}
	}
}
