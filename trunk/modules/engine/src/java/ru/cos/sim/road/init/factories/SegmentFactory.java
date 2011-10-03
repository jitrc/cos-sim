/**
 * 
 */
package ru.cos.sim.road.init.factories;

import ru.cos.sim.road.init.data.LaneData;
import ru.cos.sim.road.init.data.SegmentData;
import ru.cos.sim.road.init.data.TrapeziumSegmentData;
import ru.cos.sim.road.init.factories.exceptions.RoadNetworkFactoryException;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.link.Segment;

/**
 * 
 * @author zroslaw
 */
public class SegmentFactory {

	public static Segment createSegment(SegmentData segmentData) {
		Segment segment = null;
		
		// create segment instance
		switch(segmentData.getSegmentType()){
		case TrapeziumSegment: 
			segment = TrapeziumSegmentFactory.createSegment((TrapeziumSegmentData) segmentData); 
			break;
		default: 
			throw new RoadNetworkFactoryException("Unexpectd segment type "+segmentData.getSegmentType());
		}
		
		segment.setLength(segmentData.getLength());
		
		// create array of lanes
		Lane[] lanes = new Lane[segmentData.getLanes().length];
		for (int i = 0;i<segmentData.getLanes().length;i++){
			LaneData laneData = segmentData.getLanes()[i];
			if (laneData.getIndex()!=i)
				throw new RoadNetworkFactoryException("Incorrect lane index "+laneData.getIndex()+", expecpted "+i);
			Lane lane = LaneFactory.createLane(laneData);
			lane.setSegment(segment);
			lanes[i]=lane;
		}
		segment.setLanes(lanes);
		
		return segment;
	}

}
