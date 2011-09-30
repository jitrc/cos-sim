/**
 * 
 */
package ru.cos.sim.road.init.xml;

import java.util.Map;
import java.util.TreeMap;


import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.road.init.data.LinkData;
import ru.cos.sim.road.init.data.SegmentData;

/**
 * 
 * @author zroslaw
 */
public class LinkDataReader {
	
	public static final Namespace NS = MDFReader.MDF_NAMESPACE;

	public static final String LINK = "Link"; 
	public static final String ID = "id"; 
	public static final String LENGTH = "length"; 
	public static final String SOURCE_NODE_ID = "sourceNodeId"; 
	public static final String DESTINATION_NODE_ID = "destinationNodeId";
	public static final String SEGMENTS = "Segments"; 
	public static final String SEGMENT = "Segment"; 

	public static LinkData read(Element linkElement) {
		LinkData linkData = new LinkData();
		
		Element idElement = linkElement.getChild(ID, NS);
		linkData.setId(Integer.parseInt(idElement.getText()));
		
		Element lengthtElement = linkElement.getChild(LENGTH, NS);
		linkData.setLength(Float.parseFloat(lengthtElement.getText()));

		Element sourceNodeIdElement = linkElement.getChild(SOURCE_NODE_ID, NS);
		linkData.setSourceNodeId(Integer.parseInt(sourceNodeIdElement.getText()));
		
		Element destinationNodeIdElement = linkElement.getChild(DESTINATION_NODE_ID, NS);
		linkData.setDestinationNodeId(Integer.parseInt(destinationNodeIdElement.getText()));
		
		Map<Integer, SegmentData> segmentsData = new TreeMap<Integer, SegmentData>();
		for (Object segmentObj:linkElement.getChild(SEGMENTS, NS).getChildren()){
			Element segmentElement = (Element)segmentObj;
			SegmentData segmentData = SegmentDataReader.read(segmentElement);
			segmentsData.put(segmentData.getId(), segmentData);
		}
		linkData.setSegments(segmentsData);
		
		return linkData;
	}

}
