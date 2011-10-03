/**
 * 
 */
package ru.cos.sim.agents.tlns.factories;

import ru.cos.sim.agents.tlns.PeakAwareTLN;
import ru.cos.sim.agents.tlns.ScheduleTable;
import ru.cos.sim.agents.tlns.data.PeakAwareTLNData;
import ru.cos.sim.agents.tlns.data.ScheduleTableData;
import ru.cos.sim.engine.RoadNetworkUniverse;

/**
 * 
 * @author zroslaw
 */
public class PeakAwareTLNFactory {

	public static PeakAwareTLN createPeakAwareTLN(
			PeakAwareTLNData tlnData, RoadNetworkUniverse universe) {
		PeakAwareTLN peakAwareTLN = new PeakAwareTLN();
		
		// create schedules for 
		ScheduleTableData regularScheduleData = tlnData.getRegularSchedule();
		ScheduleTableData peakScheduleData = tlnData.getPeakSchedule();
		ScheduleTable regularSchedule = ScheduleTableFactory.createScheduleTable(regularScheduleData);
		ScheduleTable peakSchedule = ScheduleTableFactory.createScheduleTable(peakScheduleData);
		peakAwareTLN.setPeakSchedule(peakSchedule);
		peakAwareTLN.setRegularSchedule(regularSchedule);
		
		peakAwareTLN.setPeakPeriodStart(tlnData.getPeakPeriodStart());
		peakAwareTLN.setPeakPeriodDuration(tlnData.getPeakPeriodDuration());
		
		peakAwareTLN.shiftSchedule(tlnData.getScheduleTimeShift());
		
		return peakAwareTLN;
	}

}
