/**
 * 
 */
package ru.cos.sim.road.init.initializers;

import java.util.Map;

import ru.cos.sim.road.RoadNetwork;
import ru.cos.sim.road.init.data.LaneData;
import ru.cos.sim.road.init.data.LinkData;
import ru.cos.sim.road.init.data.RoadNetworkData;
import ru.cos.sim.road.init.data.SegmentData;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.link.Link;
import ru.cos.sim.road.link.Segment;

/**
 * 
 * @author zroslaw
 */
public class LaneInitializer {

	public static void initLane(Lane lane, RoadNetwork roadNetwork,	RoadNetworkData roadNetworkData) {
		Segment segment = lane.getSegment();
		Link link = segment.getLink();
		Map<Integer,LinkData> linksData = roadNetworkData.getLinks();
		LinkData linkData = linksData.get(link.getId());
		Map<Integer,SegmentData> segmentsData = linkData.getSegments();
		SegmentData segmentData = segmentsData.get(segment.getId());
		LaneData laneData = segmentData.getLanes()[lane.getIndex()];

		Segment nextSegment = segment.getNextSegment();
		Lane nextLane = null;
		Segment prevSegment = segment.getPrevSegment();
		Lane prevLane = null;

		if (nextSegment!=null){
			int nextLaneIndex = laneData.getNextLaneIndex();
			if (nextLaneIndex>-1)
				nextLane = nextSegment.getLanes()[laneData.getNextLaneIndex()];
		}
		if (prevSegment!=null){
			int prevLaneIndex = laneData.getPrevLaneIndex();
			if (prevLaneIndex>-1)
				prevLane = prevSegment.getLanes()[laneData.getPrevLaneIndex()];
		}
		lane.setNext(nextLane);
		lane.setPrev(prevLane);
	}

}
