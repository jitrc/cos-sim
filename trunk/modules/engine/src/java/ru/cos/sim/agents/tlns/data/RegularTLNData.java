/**
 * 
 */
package ru.cos.sim.agents.tlns.data;

import ru.cos.sim.agents.tlns.TLNType;

/**
 * 
 * @author zroslaw
 */
public class RegularTLNData extends TrafficLightNetworkData {

	private float scheduleTimeShift;
	
	private ScheduleTableData scheduleTable;
	
	public float getScheduleTimeShift() {
		return scheduleTimeShift;
	}

	public void setScheduleTimeShift(float scheduleTimeShift) {
		this.scheduleTimeShift = scheduleTimeShift;
	}

	public ScheduleTableData getScheduleTable() {
		return scheduleTable;
	}

	public void setScheduleTable(ScheduleTableData scheduleTable) {
		this.scheduleTable = scheduleTable;
	}

	@Override
	public TLNType getTLNType() {
		return TLNType.RegularTrafficLightNetwork;
	}

}
