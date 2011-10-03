/**
 * 
 */
package ru.cos.sim.road.init.data;

import ru.cos.sim.road.link.Segment.SegmentType;

/**
 * 
 * @author zroslaw
 */
public abstract class SegmentData {
	
	protected int id;
	
	protected float length;
	
	protected int prevSegmentId;
	
	protected int nextSegmentId;
	
	protected LaneData[] lanes;
	
	public abstract SegmentType getSegmentType();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public int getPrevSegmentId() {
		return prevSegmentId;
	}

	public void setPrevSegmentId(int prevSegmentId) {
		this.prevSegmentId = prevSegmentId;
	}

	public int getNextSegmentId() {
		return nextSegmentId;
	}

	public void setNextSegmentId(int nextSegmentId) {
		this.nextSegmentId = nextSegmentId;
	}

	public LaneData[] getLanes() {
		return lanes;
	}

	public void setLanes(LaneData[] lanes) {
		this.lanes = lanes;
	}
	
}
