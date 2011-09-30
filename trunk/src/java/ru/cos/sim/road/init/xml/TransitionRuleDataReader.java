/**
 * 
 */
package ru.cos.sim.road.init.xml;


import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.road.init.data.TransitionRuleData;

/**
 * 
 * @author zroslaw
 */
public class TransitionRuleDataReader {
	
	public static final String ID = "id"; 
	public static final String LENGTH = "length";
	public static final String WIDTH = "width";
	public static final String SOURCE_LINK_ID = "sourceLinkId";
	public static final String SOURCE_LANE_INDEX = "sourceLaneIndex";
	public static final String DESTINATION_LINK_ID = "destinationLinkId";
	public static final String DESTINATION_LANE_INDEX = "destinationLaneIndex";

	public static final Namespace NS = MDFReader.MDF_NAMESPACE;

	public static TransitionRuleData read(Element trElement) {
		TransitionRuleData trData = new TransitionRuleData();

		Element idElement = trElement.getChild(ID, NS);
		trData.setId(Integer.parseInt(idElement.getText()));

		Element lengthtElement = trElement.getChild(LENGTH, NS);
		trData.setLength(Float.parseFloat(lengthtElement.getText()));
		
		Element widthElement = trElement.getChild(WIDTH, NS);
		trData.setWidth(Float.parseFloat(widthElement.getText()));
		
		Element srcLinkIdElement = trElement.getChild(SOURCE_LINK_ID, NS);
		Element srcLaneIndexElement = trElement.getChild(SOURCE_LANE_INDEX, NS);
		trData.setSourceLinkId(Integer.parseInt(srcLinkIdElement.getText()));
		trData.setSourceLaneIndex(Integer.parseInt(srcLaneIndexElement.getText()));
		
		Element dstLinkIdElement = trElement.getChild(DESTINATION_LINK_ID, NS);
		Element dstLaneIndexElement = trElement.getChild(DESTINATION_LANE_INDEX, NS);
		trData.setDestinationLinkId(Integer.parseInt(dstLinkIdElement.getText()));
		trData.setDestinationLaneIndex(Integer.parseInt(dstLaneIndexElement.getText()));
		
		return trData;
	}

}
