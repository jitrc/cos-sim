/**
 * 
 */
package ru.cos.sim.road.init.xml;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.road.exceptions.RoadNetworkException;
import ru.cos.sim.road.init.data.LaneData;
import ru.cos.sim.road.init.data.NoSpeedLimitSignData;
import ru.cos.sim.road.init.data.SegmentData;
import ru.cos.sim.road.init.data.SignData;
import ru.cos.sim.road.init.data.SpeedLimitSignData;
import ru.cos.sim.road.init.data.TrapeziumSegmentData;
import ru.cos.sim.road.init.xml.exceptions.XMLReaderException;
import ru.cos.sim.road.link.Segment.SegmentType;

/**
 * 
 * @author zroslaw
 */
public class SegmentDataReader {

	public static final Namespace NS = MDFReader.MDF_NAMESPACE;
	
	public static final String SEGMENT = "Segment"; 
	public static final String ID = "id"; 
	public static final String LENGTH = "length"; 
	public static final String PREV_SEGMENT_ID = "prevSegmentId"; 
	public static final String NEXT_SEGMENT_ID = "nextSegmentId";
	public static final String GEOMETRY = "Geometry"; 
	public static final String LANES = "Lanes"; 
	public static final String LANE = "Lane"; 
	public static final String TRAPEZIUM_SHIFT = "trapeziumShift"; 
	public static final String ROAD_SIGNS = "RoadSigns"; 
	public static final String SPEED_LIMIT_SIGN = "SpeedLimitSign";
	public static final String NO_SPEED_LIMIT_SIGN = "NoSpeedLimitSign";
	public static final String SPEED_LIMIT = "speedLimit";
	public static final String POSITION = "position";

	public static SegmentData read(Element segmentElement) {
		
		SegmentData segmentData;
		
		SegmentType segmentType = SegmentType.valueOf(segmentElement.getName());
		
		switch (segmentType){
		case TrapeziumSegment:
			TrapeziumSegmentData trapeziumSegmentData = new TrapeziumSegmentData();
			trapeziumSegmentData.setTrapeziumShift(Float.parseFloat(segmentElement.getChildText(TRAPEZIUM_SHIFT, NS)));
			segmentData = trapeziumSegmentData;
			break;
		default:
			throw new RoadNetworkException("Unexpected segment type "+segmentType);
		}

		Element idElement = segmentElement.getChild(ID, NS);
		segmentData.setId(Integer.parseInt(idElement.getText()));
		
		Element lengthtElement = segmentElement.getChild(LENGTH, NS);
		segmentData.setLength(Float.parseFloat(lengthtElement.getText()));

		Element prevSegmentIdElement = segmentElement.getChild(PREV_SEGMENT_ID, NS);
		segmentData.setPrevSegmentId(Integer.parseInt(prevSegmentIdElement.getText()));

		Element nextSegmentIdElement = segmentElement.getChild(NEXT_SEGMENT_ID, NS);
		segmentData.setNextSegmentId(Integer.parseInt(nextSegmentIdElement.getText()));
		
		List<?> laneElementsList = segmentElement.getChild(LANES, NS).getChildren(LANE, NS); 
		LaneData[] lanes = new LaneData[laneElementsList.size()];
		for (Object laneObj:laneElementsList){
			Element laneElement = (Element)laneObj;
			LaneData laneData = LaneDataReader.read(laneElement);
			lanes[laneData.getIndex()]=laneData;
		}
		segmentData.setLanes(lanes);
		
		// Read signs
		Set<SignData> signs = new HashSet<SignData>();
		Element roadSignsElement = segmentElement.getChild(ROAD_SIGNS,NS);
		if (roadSignsElement!=null){
			for (Object signObj:roadSignsElement.getChildren()){
				Element signElement = (Element) signObj;
				SignData signData = null;
				if (signElement.getName().equals(SPEED_LIMIT_SIGN)){
					SpeedLimitSignData speedLimitSignData = new SpeedLimitSignData();
					Element speedLimitElement = signElement.getChild(SPEED_LIMIT, NS);
					speedLimitSignData.setSpeedLimit(Float.parseFloat(speedLimitElement.getText()));
					signData = speedLimitSignData;
				}else if (signElement.getName().equals(NO_SPEED_LIMIT_SIGN)){
					signData = new NoSpeedLimitSignData();
				}else
					throw new XMLReaderException("Unknown sign name "+signElement.getName());
				// set position
				Element positionElement = signElement.getChild(POSITION, NS);
				signData.setPosition(Float.parseFloat(positionElement.getText()));
				signs.add(signData);
			}
		}
		segmentData.setSigns(signs);
		
		return segmentData;
	}

}
