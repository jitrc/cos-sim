/**
 * 
 */
package ru.cos.sim.road.init.factories;

import java.util.Map;
import java.util.TreeMap;

import ru.cos.sim.road.init.data.LinkData;
import ru.cos.sim.road.init.data.SegmentData;
import ru.cos.sim.road.link.Link;
import ru.cos.sim.road.link.Segment;

/**
 * 
 * @author zroslaw
 */
public class LinksFactory {

	public static Link createLink(LinkData linkData) {
		Link link = new Link(linkData.getId());
		link.setLength(linkData.getLength());
		
		Map<Integer,Segment> segments = new TreeMap<Integer, Segment>();
		for (SegmentData segmentData:linkData.getSegments().values()){
			Segment segment = SegmentFactory.createSegment(segmentData);
			segment.setLink(link);
			segments.put(segment.getId(), segment);
		}
		link.setSegments(segments);
		
		return link;
	}

}
