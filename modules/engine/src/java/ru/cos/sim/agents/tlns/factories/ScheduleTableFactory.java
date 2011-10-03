/**
 * 
 */
package ru.cos.sim.agents.tlns.factories;

import java.util.ArrayList;
import java.util.List;

import ru.cos.sim.agents.tlns.ScheduleTable;
import ru.cos.sim.agents.tlns.TimePeriod;
import ru.cos.sim.agents.tlns.data.ScheduleTableData;
import ru.cos.sim.agents.tlns.data.TimePeriodData;

/**
 * 
 * @author zroslaw
 */
public class ScheduleTableFactory {

	public static ScheduleTable createScheduleTable(ScheduleTableData scheduleTableData) {
		ScheduleTable scheduleTable = new ScheduleTable();
		List<TimePeriod> timePeriods = new ArrayList<TimePeriod>();
		for (TimePeriodData timePeriodData:scheduleTableData.getTimePeriods()){
			TimePeriod timePeriod = new TimePeriod();
			timePeriod.setPeriodDuration(timePeriodData.getDuration());
			timePeriod.setTrafficLightSignals(timePeriodData.getTrafficLightSignals());
			timePeriods.add(timePeriod);
		}
		scheduleTable.setTLNPeriods(timePeriods);
		return scheduleTable;
	}

}
