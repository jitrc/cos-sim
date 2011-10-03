/**
 * 
 */
package ru.cos.sim.meters.framework;

import java.util.List;


/**
 *
 * @author zroslaw
 */
public class ScheduledAverageDataCollector<T extends MeasuredData<T>> extends ScheduledPeriodsDataCollector<T, T> {

	public ScheduledAverageDataCollector(
			float time,
			List<TimePeriod> scheduledPeriods,
			AverageDataCollectorFactory<T> averageDataCollectorFactory) {
		super(time, scheduledPeriods, averageDataCollectorFactory);
	}

}
