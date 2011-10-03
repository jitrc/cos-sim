/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.framework.AverageDataCollector;
import ru.cos.sim.meters.framework.MeasuredData;

/**
 * Simplest average data collector.
 * Returns last instant data as average data.
 * @author zroslaw
 */
public class DumbAverageDataCollector<T extends MeasuredData<T>> extends AverageDataCollector<T> {

	private T lastInstantData;
	
	@Override
	public void considerInstantData(T instantData, float dt) {
		lastInstantData = instantData.clone();
	}

	@Override
	public T getPeriodData() {
		return lastInstantData;
	}

}
