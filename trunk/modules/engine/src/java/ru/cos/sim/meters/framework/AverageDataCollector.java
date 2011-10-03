/**
 * 
 */
package ru.cos.sim.meters.framework;

/**
 *
 * @author zroslaw
 */
public abstract class AverageDataCollector<T> implements PeriodDataCollector<T, T> {

	@Override
	public abstract void considerInstantData(T instantData, float dt);

	@Override
	public abstract T getPeriodData();

}
