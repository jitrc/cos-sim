/**
 * 
 */
package ru.cos.sim.agents.tlns.data;

import ru.cos.sim.agents.tlns.TLNType;

/**
 * 
 * @author zroslaw
 */
public class PeakAwareTLNData extends TrafficLightNetworkData {

	private float scheduleTimeShift;
	
	private ScheduleTableData regularSchedule;
	
	private ScheduleTableData peakSchedule;
	
	private float peakPeriodStart;
	
	private float peakPeriodDuration;
	
	public float getScheduleTimeShift() {
		return scheduleTimeShift;
	}

	public void setScheduleTimeShift(float scheduleTimeShift) {
		this.scheduleTimeShift = scheduleTimeShift;
	}

	public ScheduleTableData getRegularSchedule() {
		return regularSchedule;
	}

	public void setRegularSchedule(ScheduleTableData regularSchedule) {
		this.regularSchedule = regularSchedule;
	}

	public ScheduleTableData getPeakSchedule() {
		return peakSchedule;
	}

	public void setPeakSchedule(ScheduleTableData peakSchedule) {
		this.peakSchedule = peakSchedule;
	}

	public float getPeakPeriodStart() {
		return peakPeriodStart;
	}

	public void setPeakPeriodStart(float peakPeriodStart) {
		this.peakPeriodStart = peakPeriodStart;
	}

	public float getPeakPeriodDuration() {
		return peakPeriodDuration;
	}

	public void setPeakPeriodDuration(float peakPeriodDuration) {
		this.peakPeriodDuration = peakPeriodDuration;
	}

	@Override
	public TLNType getTLNType() {
		return TLNType.PeakAwareTrafficLightNetwork;
	}
	
}
