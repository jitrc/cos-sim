/**
 * 
 */
package ru.cos.sim.agents.tlns;

import java.util.List;

/**
 * Simple repeatable schedule.
 * @author zroslaw
 */
public class ScheduleTable {
	
	/**
	 * List of periods to repeat.
	 */
	private List<TimePeriod> periods;
	
	/**
	 * Sum of all period's durations
	 */
	private float scheduleDuration;
	
	/**
	 * Index of current period.
	 */
	private int currentPeriodIndex = -1;
	
	private TimePeriod nextPeriod;

	public TimePeriod getNextPeriod() {
		TimePeriod result = nextPeriod;
		currentPeriodIndex++;
		if (currentPeriodIndex>=periods.size())
			currentPeriodIndex=0;
		nextPeriod = periods.get(currentPeriodIndex);
		nextPeriod.start();
		return result;
	}
	
	public void setTLNPeriods(List<TimePeriod> periods){
		this.periods = periods;
		this.scheduleDuration = 0;
		for (TimePeriod period:periods){
			this.scheduleDuration+=period.getPeriodDuration();
		}
		this.nextPeriod=periods.get(0);
	}
	
	/**
	 * Shift schedule on the specified time. 
	 * @param timeShift time duration to shift the schedule
	 */
	public void shift(float timeShift){
		float truncatedShift = timeShift - (int)(timeShift/scheduleDuration);
		int index = -1;
		for (TimePeriod period:periods){
			index++;
			if (truncatedShift<period.getPeriodDuration()){
				period.setPeriodTimer(truncatedShift);
				break;
			}
			truncatedShift-=period.getPeriodDuration();
		}
	}

}
