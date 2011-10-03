/**
 * 
 */
package ru.cos.sim.meters.framework;

/**
 *
 * @author zroslaw
 */
public class NormalizedHistoryCollector<T extends MeasuredData<T>> implements PeriodDataCollector<T, MeasurementHistory<T>> {

	private T firstValue = null;
	private PeriodDataCollector<T,MeasurementHistory<T>> historyDataCollector;
	
	
	public NormalizedHistoryCollector(PeriodDataCollector<T,MeasurementHistory<T>> historyDataCollector) {
		this.historyDataCollector = historyDataCollector;
	}

	/* (non-Javadoc)
	 * @see ru.cos.traffic.model.meters.PeriodDataCollector#considerInstantData(java.lang.Object, float)
	 */
	@Override
	public void considerInstantData(T instantData, float dt) {
		if (firstValue==null){
			firstValue = instantData;
		}else{
			// normalize value
			instantData.normalize(firstValue);
			historyDataCollector.considerInstantData(instantData, dt);
		}
	}

	@Override
	public MeasurementHistory<T> getPeriodData() {
		return historyDataCollector.getPeriodData();
	}

}
