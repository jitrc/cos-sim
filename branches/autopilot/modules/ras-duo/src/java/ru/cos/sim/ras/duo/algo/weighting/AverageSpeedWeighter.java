package ru.cos.sim.ras.duo.algo.weighting;

import ru.cos.sim.ras.duo.DataAggregator;
import ru.cos.sim.ras.duo.PathExtensions;
import ru.cos.sim.ras.duo.Weighter;

public class AverageSpeedWeighter implements Weighter {

	public AverageSpeedWeighter(float linkLength, DataAggregator speedAggregator) {
		this.length = linkLength;
		this.speedAggregator = speedAggregator;
	}

	private float length;
	private DataAggregator speedAggregator;
	
	@Override
	public float getWeight(PathExtensions extensions) {
		return length / speedAggregator.getAggregate();
	}
	
	@Override
	public String toString() {
		return "Weight: " + getWeight(null);
	}
}
