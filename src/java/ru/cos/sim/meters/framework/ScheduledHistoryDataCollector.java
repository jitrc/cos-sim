/**
 * 
 */
package ru.cos.sim.meters.framework;

import java.util.List;


/**
 *
 * @author zroslaw
 */
public class ScheduledHistoryDataCollector<T extends MeasuredData<T>> extends ScheduledPeriodsDataCollector<T, MeasurementHistory<T>> {

//	public ScheduledHistoryDataCollector(
//			float time,
//			List<TimePeriod> scheduledPeriods, float logInterval) {
//		super(time, scheduledPeriods, new HistoryDataCollectorFactory<T>(logInterval));
//	}
	
	public ScheduledHistoryDataCollector(
			float time,
			List<TimePeriod> scheduledPeriods, float logInterval) {
		super(time, scheduledPeriods, new NormalizedHistoryCollectorFactory<T>(logInterval));
	}
	
	
	
}
