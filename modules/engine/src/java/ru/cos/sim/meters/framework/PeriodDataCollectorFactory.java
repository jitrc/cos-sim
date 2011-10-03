package ru.cos.sim.meters.framework;

public interface PeriodDataCollectorFactory<T,K> {

	public PeriodDataCollector<T, K> getInstance();
	
}
