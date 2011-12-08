/**
 * 
 */
package ru.cos.sim.meters.framework;



/**
 *
 * @author zroslaw
 */
public class HistoryDataCollector<T extends MeasuredData<T>> implements PeriodDataCollector<T, MeasurementHistory<T>> {
	
	private float clock = 0;
	
	private float logInterval = 10;
	private float timeToNewInterval = -1; // start new interval on next step anyway
	
//	private T previousValue; // previous value
	
	private MeasurementHistory<T> history = new MeasurementHistory<T>();

	public HistoryDataCollector(float logInterval){
		this.logInterval = logInterval;
	}

	public HistoryDataCollector(float time, float logInterval){
		this.clock = time;
		this.logInterval = logInterval;
	}
	
	@Override
	public void considerInstantData(T instantData, float dt) {
		clock+=dt;
		timeToNewInterval-=dt;
//		if (instantData.equals(previousValue)) return;
		T currentInstData = instantData.clone();
//		previousValue = currentInstData;
		if (timeToNewInterval<=0){
			Measurement<T> measurement = new Measurement<T>(currentInstData, clock);
			history.addMeasurement(measurement);
			timeToNewInterval = logInterval;
		}
	}

	@Override
	public MeasurementHistory<T> getPeriodData() {
		return history.clone();
	}
	
}
