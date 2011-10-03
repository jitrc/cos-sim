/**
 * 
 */
package ru.cos.sim.agents.tlns.xml;

import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.agents.tlns.data.LogicalTrafficLightData;
import ru.cos.sim.agents.tlns.data.PlacementData;
import ru.cos.sim.mdf.MDFReader;

/**
 * Reader for logical traffic light xml representation.
 * @author zroslaw
 */
public class LogicalTLDataReader {

	public static final Namespace NS = MDFReader.MDF_NAMESPACE;

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String PLACEMENT = "Placement";

	public static LogicalTrafficLightData read(Element trafficLightElement) {
		LogicalTrafficLightData trafficLightData = new LogicalTrafficLightData();

		Element idElement = trafficLightElement.getChild(ID, NS);
		trafficLightData.setId(Integer.parseInt(idElement.getText()));

		Element nameElement = trafficLightElement.getChild(NAME, NS);
		trafficLightData.setName(nameElement.getText());
		
		Element placementElement = trafficLightElement.getChild(PLACEMENT,NS);
		PlacementData placementData = PlacementDataReader.read(placementElement);
		trafficLightData.setPlacement(placementData);
		
		return trafficLightData;
	}

}
