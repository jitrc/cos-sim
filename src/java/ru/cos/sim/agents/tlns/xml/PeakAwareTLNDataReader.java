/**
 * 
 */
package ru.cos.sim.agents.tlns.xml;

import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.agents.tlns.data.PeakAwareTLNData;
import ru.cos.sim.agents.tlns.data.ScheduleTableData;
import ru.cos.sim.mdf.MDFReader;

/**
 * 
 * @author zroslaw
 */
public class PeakAwareTLNDataReader {

	public static final Namespace NS = MDFReader.MDF_NAMESPACE;

	public static final String SCHEDULE_TIME_SHIFT = "scheduleTimeShift";
	public static final String REGULAR_SCHEDULE = "RegularSchedule";
	public static final String PEAK_SCHEDULE = "PeakSchedule";
	public static final String PEAK_PERIOD = "PeakPeriod";
	public static final String START_TIME = "startTime";
	public static final String DURATION = "duration";
	
	public static PeakAwareTLNData read(Element tlnElement) {
		PeakAwareTLNData peakAwareTLNData = new PeakAwareTLNData();
		
		Element shiftElement = tlnElement.getChild(SCHEDULE_TIME_SHIFT, NS);
		peakAwareTLNData.setScheduleTimeShift(Float.parseFloat(shiftElement.getText()));

		Element regularScheduleElement = tlnElement.getChild(REGULAR_SCHEDULE, NS);
		ScheduleTableData regularSchedule = ScheduleTableDataReader.read(regularScheduleElement);
		peakAwareTLNData.setRegularSchedule(regularSchedule);

		Element peakScheduleElement = tlnElement.getChild(PEAK_SCHEDULE, NS);
		ScheduleTableData peakchedule = ScheduleTableDataReader.read(peakScheduleElement);
		peakAwareTLNData.setPeakSchedule(peakchedule);

		Element peakPeriodElement = tlnElement.getChild(PEAK_PERIOD, NS);
		Element peakStartTimeElement = peakPeriodElement.getChild(START_TIME, NS);
		peakAwareTLNData.setPeakPeriodStart(Float.parseFloat(peakStartTimeElement.getText()));
		Element peakDurationElement = peakPeriodElement.getChild(DURATION, NS);
		peakAwareTLNData.setPeakPeriodDuration(Float.parseFloat(peakDurationElement.getText()));
		
		return peakAwareTLNData;
	}

}
