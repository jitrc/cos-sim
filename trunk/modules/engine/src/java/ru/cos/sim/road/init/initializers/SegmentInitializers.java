/**
 * 
 */
package ru.cos.sim.road.init.initializers;

import java.util.Map;

import ru.cos.sim.road.RoadNetwork;
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
public class SegmentInitializers {

	public static void initSegment(Segment segment, RoadNetwork roadNetwork,RoadNetworkData roadNetworkData) {
		Link link = segment.getLink();
		Map<Integer,LinkData> linksData = roadNetworkData.getLinks();
		LinkData linkData = linksData.get(link.getId());
		Map<Integer,SegmentData> segmentsData = linkData.getSegments();
		SegmentData segmentData = segmentsData.get(segment.getId());

		Segment nextSegment = link.getSegments().get(segmentData.getNextSegmentId());
		Segment prevSegment = link.getSegments().get(segmentData.getPrevSegmentId());
		segment.setNextSegment(nextSegment);
		segment.setPrevSegment(prevSegment);
		
		for (Lane lane:segment.getLanes()){
			LaneInitializer.initLane(lane, roadNetwork, roadNetworkData);
		}
	}

}
