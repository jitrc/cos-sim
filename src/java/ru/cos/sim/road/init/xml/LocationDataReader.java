/**
 * 
 */
package ru.cos.sim.road.init.xml;


import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.road.init.data.LinkLocationData;
import ru.cos.sim.road.init.data.LocationData;
import ru.cos.sim.road.init.data.NodeLocationData;
import ru.cos.sim.road.init.xml.exceptions.XMLReaderException;

/**
 * Reader for location data.
 * @author zroslaw
 */
public class LocationDataReader {

	public static final String LINK_ID = "linkId";
	public static final String SEGMENT_ID = "segmentId";
	public static final String LANE_INDEX = "laneIndex";
	public static final String NODE_ID = "nodeId";
	public static final String TR_ID = "transitionRuleId";
	public static final String POSITION = "position";
	public static final String SHIFT = "shift";
	
	public static final Namespace NS = MDFReader.MDF_NAMESPACE;

	public static LocationData read(Element locationElement){
		LocationData result;
		LocationData.LocationType locationType = LocationData.LocationType.valueOf(locationElement.getName());
		switch (locationType){
		case LinkLocation:
			result = readLinkLocation(locationElement);
			break;
		case NodeLocation:
			result = readNodeLocation(locationElement);
			break;
		default:
			throw new XMLReaderException("Unexpected location type "+locationType);	
		}
		
		Element positionElement = locationElement.getChild(POSITION, NS);
		result.setPosition(Float.parseFloat(positionElement.getText()));
		
		Element shiftElement = locationElement.getChild(SHIFT, NS);
		if (shiftElement!=null)
			result.setShift(Float.parseFloat(shiftElement.getText()));
		
		return result;
	}

	private static LocationData readNodeLocation(Element locationElement) {
		NodeLocationData nodeLocationData = new NodeLocationData();
		
		Element nodeIdElement = locationElement.getChild(NODE_ID, NS);
		nodeLocationData.setNodeId(Integer.parseInt(nodeIdElement.getText()));
		
		Element trIdElement = locationElement.getChild(TR_ID, NS);
		nodeLocationData.setTransitionRuleId(Integer.parseInt(trIdElement.getText()));
		
		return nodeLocationData;
	}

	private static LocationData readLinkLocation(Element locationElement) {
		LinkLocationData linkLocationData = new LinkLocationData();
		
		Element linkIdElement = locationElement.getChild(LINK_ID, NS);
		linkLocationData.setLinkId(Integer.parseInt(linkIdElement.getText()));
		
		Element segmentIdElement = locationElement.getChild(SEGMENT_ID, NS);
		linkLocationData.setSegmentId(Integer.parseInt(segmentIdElement.getText()));
		
		Element laneIndexElement = locationElement.getChild(LANE_INDEX, NS);
		linkLocationData.setLaneIndex(Integer.parseInt(laneIndexElement.getText()));
		
		return linkLocationData;
	}
	
}
