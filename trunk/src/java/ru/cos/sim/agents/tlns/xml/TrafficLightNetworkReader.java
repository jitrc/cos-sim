/**
 * 
 */
package ru.cos.sim.agents.tlns.xml;

import java.util.HashSet;
import java.util.Set;

import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.agents.tlns.TLNType;
import ru.cos.sim.agents.tlns.data.LogicalTrafficLightData;
import ru.cos.sim.agents.tlns.data.TrafficLightNetworkData;
import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.road.init.xml.exceptions.XMLReaderException;

/**
 * @author zroslaw
 *
 */
public class TrafficLightNetworkReader {

	public static final Namespace NS = MDFReader.MDF_NAMESPACE;

	public static final String REGULAR_NODE_ID = "regularNodeId";
	public static final String TRAFFIC_LIGHTS = "TrafficLights";
	public static final String TRAFFIC_LIGHT = "TrafficLight";

	public static TrafficLightNetworkData read(Element tlnElement) {

		TrafficLightNetworkData trafficLightNetworkData;
		TLNType tlnType = TLNType.valueOf(tlnElement.getName());

		switch(tlnType){
		case RegularTrafficLightNetwork:
			trafficLightNetworkData = RegularTLNDataReader.read(tlnElement);
			break;
		case PeakAwareTrafficLightNetwork:
			trafficLightNetworkData = PeakAwareTLNDataReader.read(tlnElement);
			break;
		default:
			throw new XMLReaderException("Unexpected traffic light network type "+tlnType);
		}

		Element nodeIdElement = tlnElement.getChild(REGULAR_NODE_ID,NS);
		trafficLightNetworkData.setRegularNodeId(Integer.parseInt(nodeIdElement.getText()));

		Element trafficLightsElement = tlnElement.getChild(TRAFFIC_LIGHTS,NS);
		Set<LogicalTrafficLightData> trafficLights = new HashSet<LogicalTrafficLightData>();
		for (Object trafficLightObj:trafficLightsElement.getChildren(TRAFFIC_LIGHT,NS)){
			Element trafficLightElement = (Element)trafficLightObj;
			LogicalTrafficLightData trafficLightData = LogicalTLDataReader.read(trafficLightElement);
			trafficLights.add(trafficLightData);
		}
		trafficLightNetworkData.setTrafficLightsData(trafficLights);

		return trafficLightNetworkData;
	}

}
