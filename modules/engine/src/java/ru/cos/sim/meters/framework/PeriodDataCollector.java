/**
 * 
 */
package ru.cos.sim.meters.framework;

/**
 *
 * @author zroslaw
 */
public interface PeriodDataCollector<T, K> {

	public void considerInstantData(T instantData, float dt);
	
	public K getPeriodData();
	
}
