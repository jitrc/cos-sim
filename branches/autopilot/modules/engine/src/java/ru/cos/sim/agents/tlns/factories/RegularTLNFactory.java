/**
 * 
 */
package ru.cos.sim.agents.tlns.factories;

import ru.cos.sim.agents.tlns.RegularTLN;
import ru.cos.sim.agents.tlns.ScheduleTable;
import ru.cos.sim.agents.tlns.TrafficLightNetwork;
import ru.cos.sim.agents.tlns.data.RegularTLNData;
import ru.cos.sim.agents.tlns.data.ScheduleTableData;
import ru.cos.sim.engine.RoadNetworkUniverse;

/**
 * 
 * @author zroslaw
 */
public class RegularTLNFactory {

	public static TrafficLightNetwork createRegularTLN(RegularTLNData tlnData,
			RoadNetworkUniverse universe) {
		RegularTLN regulartTLN = new RegularTLN();
		
		// initialize schedule
		ScheduleTableData scheduleTableData = tlnData.getScheduleTable();
		ScheduleTable scheduleTable = ScheduleTableFactory.createScheduleTable(scheduleTableData);
		// shift time table
		scheduleTable.shift(tlnData.getScheduleTimeShift());
		//set schedule to regular tln
		regulartTLN.setSchedule(scheduleTable);
		
		return regulartTLN;
	}

}
