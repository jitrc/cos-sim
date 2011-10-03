/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.framework.AverageDataCollector;
import ru.cos.sim.meters.framework.AverageDataCollectorFactory;
import ru.cos.sim.meters.framework.MeasuredData;

/**
 * Factory for {@link DumbAverageDataCollector}.
 * @author zroslaw
 */
public class DumbAverageDataCollectorFactory<T extends MeasuredData<T>> implements AverageDataCollectorFactory<T> {

	@Override
	public AverageDataCollector<T> getInstance() {
		return new DumbAverageDataCollector<T>();
	}

}
