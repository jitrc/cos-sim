package ru.cos.sim.ras.duo.algo.weighting;

import ru.cos.sim.ras.duo.DataAggregator;
import ru.cos.sim.ras.duo.PathExtensions;
import ru.cos.sim.ras.duo.Weighter;

public class TravelTimeWeighter implements Weighter {
	public TravelTimeWeighter(DataAggregator travelTimeAggregator) {
		this.travelTimeAggregator = travelTimeAggregator;
	}
	
	private DataAggregator travelTimeAggregator;
	
	@Override
	public float getWeight(PathExtensions extensions) {
		return travelTimeAggregator.getAggregate();
	}

}
