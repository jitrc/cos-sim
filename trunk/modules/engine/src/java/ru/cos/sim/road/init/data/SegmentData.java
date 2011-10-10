/**
 * 
 */
package ru.cos.sim.road.init.data;

import java.util.Set;

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
	
	protected Set<SignData> signs;
	
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

	public Set<SignData> getSigns() {
		return signs;
	}

	public void setSigns(Set<SignData> signs) {
		this.signs = signs;
	}
	
}
