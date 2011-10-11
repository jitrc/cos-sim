/**
 * 
 */
package ru.cos.sim.road.init.factories;

import ru.cos.sim.road.init.data.LaneData;
import ru.cos.sim.road.init.data.SegmentData;
import ru.cos.sim.road.init.data.SignData;
import ru.cos.sim.road.init.data.TrapeziumSegmentData;
import ru.cos.sim.road.init.factories.exceptions.RoadNetworkFactoryException;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.link.Segment;
import ru.cos.sim.road.objects.Sign;

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
		
		// create and put set of signs on this segment 
		if (segmentData.getSigns()!=null)
			for (SignData signData:segmentData.getSigns()){
				Sign sign = SignFactory.createSign(signData);
				// put sign instances on each lane on specified position
				for (Lane lane:lanes){
					lane.putPoint(sign, signData.getPosition());
					sign = sign.clone();
				}
			}
		
		return segment;
	}

}
