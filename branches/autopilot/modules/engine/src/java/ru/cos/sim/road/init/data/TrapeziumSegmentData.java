/**
 * 
 */
package ru.cos.sim.road.init.data;

import ru.cos.sim.road.link.Segment.SegmentType;

/**
 * 
 * @author zroslaw
 */
public class TrapeziumSegmentData extends SegmentData {

	protected float trapeziumShift;

	public float getTrapeziumShift() {
		return trapeziumShift;
	}

	public void setTrapeziumShift(float trapeziumShift) {
		this.trapeziumShift = trapeziumShift;
	}

	@Override
	public final SegmentType getSegmentType() {
		return SegmentType.TrapeziumSegment;
	}

}
