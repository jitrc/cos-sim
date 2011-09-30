package ru.cos.sim.agents.tlns.xml;

import java.util.HashMap;
import java.util.Map;

import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.agents.tlns.TrafficLightSignal;
import ru.cos.sim.agents.tlns.data.TimePeriodData;
import ru.cos.sim.mdf.MDFReader;

/**
 * 
 * @author zroslaw
 */
public class TimePeriodDataReader {
	
	private static final Namespace NS = MDFReader.MDF_NAMESPACE;

	private static final String DURATION = "duration";
	private static final String TRAFFIC_LIGHT_SIGNAL = "TrafficLightSignal";
	private static final String TRAFFIC_LIGHT_ID = "trafficLightId";
	private static final String SIGNAL = "signal";

	public static TimePeriodData read(Element timePeriodElement) {
		TimePeriodData timePeriodData = new TimePeriodData();
		
		Element durationElement = timePeriodElement.getChild(DURATION,NS);
		timePeriodData.setDuration(Float.parseFloat(durationElement.getText()));
		
		Map<Integer, TrafficLightSignal> trafficLightsSignals = new HashMap<Integer,TrafficLightSignal>();
		for (Object tlSignalObj:timePeriodElement.getChildren(TRAFFIC_LIGHT_SIGNAL,NS)){
			Element tlSignalElement = (Element)tlSignalObj;
			
			Element trafficLightIdElement = tlSignalElement.getChild(TRAFFIC_LIGHT_ID,NS);
			int trafficLightId = Integer.parseInt(trafficLightIdElement.getText());
			
			Element signalElement = tlSignalElement.getChild(SIGNAL,NS);
			TrafficLightSignal signal = TrafficLightSignal.valueOf(signalElement.getText());
			
			trafficLightsSignals.put(trafficLightId, signal);
		}
		timePeriodData.setTrafficLightSignals(trafficLightsSignals);
		
		return timePeriodData;
	}

}
