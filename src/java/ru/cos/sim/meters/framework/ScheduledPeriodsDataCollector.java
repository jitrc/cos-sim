/**
 * 
 */
package ru.cos.sim.meters.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author zroslaw
 */
public abstract class ScheduledPeriodsDataCollector<T,K extends MeasuredData<K>> {
	
	protected List<TimePeriod> scheduledPeriods;
	protected List<TimePeriod> futurePeriods;
	
	protected List<PeriodData<K>> finishedPeriodsData = new ArrayList<PeriodData<K>>();
	protected Map<PeriodData<K>, PeriodDataCollector<T,K>> activeMeters = new HashMap<PeriodData<K>, PeriodDataCollector<T,K>>();
	protected PeriodDataCollectorFactory<T,K> periodDataCollectorFactory;
	
	protected float clock = 0;
	
	public ScheduledPeriodsDataCollector(float time, List<TimePeriod> scheduledPeriods, PeriodDataCollectorFactory<T,K> periodDataCollectorFactory){
		clock = time;
		this.scheduledPeriods = scheduledPeriods;
		this.periodDataCollectorFactory = periodDataCollectorFactory;
		this.futurePeriods = new ArrayList<TimePeriod>(scheduledPeriods.size());
		futurePeriods.addAll(scheduledPeriods);
	}
	
	public void considerInstantData(T instantMeasuredData, float dt){
		// time counting
		clock+=dt;		
		// run active meters,
		// delete them from active set, if it is time already
		Set<PeriodData<K>> finishedPeriods = new HashSet<PeriodData<K>>();
		for (PeriodData<K> pmd:activeMeters.keySet()){
			// run meter
			PeriodDataCollector<T,K> periodDataCollector = activeMeters.get(pmd);
			periodDataCollector.considerInstantData(instantMeasuredData, dt);
//			pmd.setActualOn(clock);
			// check if meter should be stopped
			TimePeriod timePeriod = pmd.getTimePeriod();
			if (clock>=timePeriod.getTimeTo()){
				// stop meter, finalize results
				finishedPeriods.add(pmd);
			}
		}
		for (PeriodData<K> finishedPmd: finishedPeriods){
			PeriodDataCollector<T,K> periodDataCollector = activeMeters.remove(finishedPmd);
			finishedPmd.setActualOn(clock);
			finishedPmd.setMeasuredData(periodDataCollector.getPeriodData());
			finishedPeriodsData.add(finishedPmd);
		}
		
		// activate new periods, if any
		List<TimePeriod> periodsToActivate = new ArrayList<TimePeriod>(); 
		for(TimePeriod period:futurePeriods){
			if (clock>=period.getTimeFrom()){
				// move period from list of future periods to current periods list
				periodsToActivate.add(period);
				// create and activate new meter for this period
				PeriodDataCollector<T,K> newPeriodDataCollector = periodDataCollectorFactory.getInstance();//createAverageModule();
				PeriodData<K> periodMeasuredData = new PeriodData<K>(period);
				// activate meter and add period measured data to set of results
				activeMeters.put(periodMeasuredData, newPeriodDataCollector);
			}
		}
		futurePeriods.removeAll(periodsToActivate);
	}
	
	public ScheduledData<K> getOutput(){
		Set<PeriodData<K>> resultSet = new HashSet<PeriodData<K>>();
		resultSet.addAll(finishedPeriodsData);
		for (PeriodData<K> periodData:activeMeters.keySet()){
			PeriodDataCollector<T,K> periodDataCollector = activeMeters.get(periodData);
			periodData.setActualOn(clock);
			periodData.setMeasuredData(periodDataCollector.getPeriodData());
			resultSet.add(periodData);
		}
		return new ScheduledData<K>(resultSet);
	}
}
