/**
 * 
 */
package ru.cos.sim.road.init.xml;


import org.jdom.Element;

import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.road.init.data.LaneData;

/**
 * @author zroslaw
 *
 */
public class LaneDataReader {

	public static final String INDEX = "index";
	public static final String LENGTH = "length";
	public static final String WIDTH = "width";
	public static final String PREV_LANE_INDEX = "prevLaneIndex";
	public static final String NEXT_LANE_INDEX = "nextLaneIndex";

	public static LaneData read(Element laneElement) {
		LaneData laneData = new LaneData();

		Element indexElement = laneElement.getChild(INDEX,MDFReader.MDF_NAMESPACE);
		laneData.setIndex(Integer.parseInt(indexElement.getText()));

		Element lengthtElement = laneElement.getChild(LENGTH,MDFReader.MDF_NAMESPACE);
		laneData.setLength(Float.parseFloat(lengthtElement.getText()));
		
		Element widthElement = laneElement.getChild(WIDTH,MDFReader.MDF_NAMESPACE);
		laneData.setWidth(Float.parseFloat(widthElement.getText()));

		Element prevLaneIdElement = laneElement.getChild(PREV_LANE_INDEX,MDFReader.MDF_NAMESPACE);
		laneData.setPrevLaneIndex(Integer.parseInt(prevLaneIdElement.getText()));

		Element nextLaneIdElement = laneElement.getChild(NEXT_LANE_INDEX,MDFReader.MDF_NAMESPACE);
		laneData.setNextLaneIndex(Integer.parseInt(nextLaneIdElement.getText()));
		
		return laneData;
	}

}
