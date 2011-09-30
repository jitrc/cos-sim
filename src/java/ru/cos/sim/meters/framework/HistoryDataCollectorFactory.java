/**
 * 
 */
package ru.cos.sim.meters.framework;


/**
 *
 * @author zroslaw
 */
public class HistoryDataCollectorFactory<T extends MeasuredData<T>> implements PeriodDataCollectorFactory<T, MeasurementHistory<T>> {
	
	protected float logInterval = 10;

	public HistoryDataCollectorFactory(float logInterval){
		this.logInterval = logInterval;
	}
	
	@Override
	public PeriodDataCollector<T,MeasurementHistory<T>> getInstance() {
		return new HistoryDataCollector<T>(logInterval);
	}

}
