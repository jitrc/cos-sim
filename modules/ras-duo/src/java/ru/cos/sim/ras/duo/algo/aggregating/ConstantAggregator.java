package ru.cos.sim.ras.duo.algo.aggregating;

import ru.cos.sim.ras.duo.DataAggregator;

public class ConstantAggregator implements DataAggregator {
	public ConstantAggregator(float value) {
		this.value = value;
	}
	
	private float value;
	
	@Override
	public float getAggregate() {
		return value;
	}
}
