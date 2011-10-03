/**
 * 
 */
package ru.cos.sim.meters.framework;

/**
 *
 * @author zroslaw
 */
public interface AverageDataCollectorFactory<T> extends	PeriodDataCollectorFactory<T, T> {

	public AverageDataCollector<T> getInstance();
	
}
