/**
 * 
 */
package ru.cos.sim.agents.tlns.xml;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.agents.tlns.data.ScheduleTableData;
import ru.cos.sim.agents.tlns.data.TimePeriodData;
import ru.cos.sim.mdf.MDFReader;

/**
 * 
 * @author zroslaw
 */
public class ScheduleTableDataReader {
	
	private static final Namespace NS = MDFReader.MDF_NAMESPACE;

	private static final String TIME_PERIODS = "TimePeriods";
	private static final String TIME_PERIOD = "TimePeriod";

	public static ScheduleTableData read(Element scheduleElement) {
		ScheduleTableData scheduleTableData = new ScheduleTableData();
		
		List<TimePeriodData> timePeriods = new ArrayList<TimePeriodData>();
		for (Object timePeriodObj:scheduleElement.getChild(TIME_PERIODS,NS).getChildren(TIME_PERIOD,NS)){
			Element timePeriodElement = (Element)timePeriodObj;
			TimePeriodData timePeriodData = TimePeriodDataReader.read(timePeriodElement);
			timePeriods.add(timePeriodData);			
		}
		
		scheduleTableData.setTimePeriods(timePeriods);
		
		return scheduleTableData;
	}
	
	
	
}
