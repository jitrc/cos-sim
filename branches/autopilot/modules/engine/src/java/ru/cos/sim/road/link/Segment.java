/**
 * 
 */
package ru.cos.sim.road.link;

import ru.cos.sim.road.RoadTrajectory;
import ru.cos.sim.utils.Hand;

/**
 * Segment is a peace of one way road with uniform segmentGeometryData, i.e.
 * straight, circle or something else.
 * Segment contains array of lanes, in the segment number of lanes is constant.
 * @author zroslaw
 */
public abstract class Segment{
	
	/**
	 * Types of segments
	 * @author zroslaw
	 */
	public enum SegmentType{
		/**
		 * Trapezium segment
		 */
		TrapeziumSegment
	}

	/**
	 * Segment agentId
	 */
	protected int id;
	
	protected float length;
	
	protected Link parentLink;
	
	protected Segment nextSegment;
	
	protected Segment prevSegment;

	/**
	 * Array of lanes in this segment
	 */
	protected Lane[] lanes;
	
	/**
	 * Constructor
	 * @param agentId unique segment agentId in the link
	 */
	public Segment(int id) {
		this.id = id;
	}
	
	/**
	 * Calculate next (adjacent) position on next (left or right) lane.
	 * @param laneIndex index of lane from which to calculate position on next lane
	 * @param position position on current lane
	 * @param hand hand of next lane
	 * @return appropriate position on next lane
	 */
	public abstract float calculateAdjacentPosition(int laneIndex, float position, Hand hand);
	
	public abstract SegmentType getSegmentType();
	
	/**
	 * Retrieve segment agentId
	 * @return segment agentId
	 */
	public int getId(){
		return id;
	}	
	/**
	 * Retrieve parent link
	 * @return link instance that contains this segment
	 */
	public Link getLink() {
		return parentLink;
	}

	/**
	 * Set parent link
	 * @param parentLink link instance that contains this segment
	 */
	public void setLink(Link parentLink) {
		this.parentLink = parentLink;
	}
	
	/**
	 * Retrieve array of lanes of this segment
	 * @return array of lanes
	 */
	public Lane[] getLanes(){
		return lanes;
	}

	/**
	 * Get segment length
	 * @return length of this road segment
	 */
	public float getLength() {
		return length;
	}

	/**
	 * Set length of this road segment
	 * @param length of this road segment
	 */
	public void setLength(float length) {
		this.length = length;
	}

	/**
	 * Set array of lanes of this segment
	 * @param lanes
	 */
	public void setLanes(Lane[] lanes) {
		this.lanes = lanes;
	}

	public Segment getNextSegment() {
		return nextSegment;
	}

	public void setNextSegment(Segment nextSegment) {
		this.nextSegment = nextSegment;
	}

	public Segment getPrevSegment() {
		return prevSegment;
	}

	public void setPrevSegment(Segment prevSegment) {
		this.prevSegment = prevSegment;
	}

	public RoadTrajectory getLane(int laneIndex) {
		return lanes[laneIndex];
	}
	
}
