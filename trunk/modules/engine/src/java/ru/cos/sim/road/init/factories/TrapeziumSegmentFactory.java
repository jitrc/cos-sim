/**
 * 
 */
package ru.cos.sim.road.init.factories;

import ru.cos.sim.road.init.data.TrapeziumSegmentData;
import ru.cos.sim.road.link.TrapeziumSegment;

/**
 * 
 * @author zroslaw
 */
public class TrapeziumSegmentFactory {

	public static TrapeziumSegment createSegment(TrapeziumSegmentData segmentData) {
		TrapeziumSegment segment = new TrapeziumSegment(segmentData.getId());
		segment.setTrapeziumShift(segmentData.getTrapeziumShift());
		return segment;
	}

}
