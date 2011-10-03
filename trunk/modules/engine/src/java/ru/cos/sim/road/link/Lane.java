/**
 * 
 */
package ru.cos.sim.road.link;

import java.util.List;
import java.util.Vector;

import ru.cos.cs.lengthy.Observation;
import ru.cos.cs.lengthy.RegularLengthy;
import ru.cos.cs.lengthy.Router;
import ru.cos.sim.exceptions.TrafficSimulationException;
import ru.cos.sim.road.AbstractRoadTrajectory;
import ru.cos.sim.utils.Hand;

/**
 * Lane of the road.
 * @author zroslaw
 */
public class Lane extends AbstractRoadTrajectory implements RegularLengthy{

	/**
	 * Index of the lane.
	 * Index is the number of the lane in the segment starting count 
	 * from 0 from last left lane. Single lane segment will have lane with index 0.
	 * Two lane segment will have left lane with index 0 and right lane with index 1.
	 * Lane index is it index in the segment's array of lanes.
	 */
	protected int index = 0;
	
	/**
	 * Parent segment
	 */
	protected Segment segment = null;

	protected StartOfLanePoint startPoint;
	
	protected EndOfLanePoint endPoint;

	public Lane(int index, float length) {
		super(length);
		this.index = index;
		startPoint = new StartOfLanePoint(this);
		endPoint = new EndOfLanePoint(this);
	}

	@Override
	public List<Observation> observeForward(float position,	float distance, Router router) {
		List<Observation> result;
		// if no next lengthy and observation distance exceeds lane length
		// then we must adjust distance and add EndOfLanePoint observation
		if (getNext()==null && length<distance+position){
			result = super.observeForward(position, length-position, router);
			result.add(new Observation(length-position, endPoint));
		}else
			result = super.observeForward(position, distance, router);
		return result;
	}

	@Override
	public List<Observation> observeBackward(float position, float distance, Router router) {
		List<Observation> result;
		// if no prev lengthy and observation distance comes out from lane start
		// then return all observable objects on the lane + StartOfLane point
		if (prev==null && position-distance<0){
			result = super.observeBackward(position, position, router);
			result.add(new Observation(-position, startPoint));
		}else
			result = super.observeBackward(position, distance, router);
		return result;
	}

	@Override
	public final RoadTrajectoryType getRoadTrajectoryType() {
		return RoadTrajectoryType.Lane;
	}
	
	/**
	 * Get lane's parent segment.
	 * @return parent segment
	 */
	public Segment getSegment(){
		return segment;
	}

	/**
	 * Set lane's parent segment.
	 * @param segment
	 */
	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	/**
	 * Set lane index.
	 * @see Lane#index
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * Get lane index.
	 * @see Lane#index
	 * @return lane index
	 */
	public int getIndex(){
		return index;
	}
	
	public Lane getRightLane(){
		if (index<segment.getLanes().length-1)
			return segment.getLanes()[index+1];
		else
			return null;
	}
	
	public Lane getLeftLane(){
		if (index>0)
			return segment.getLanes()[index-1];
		else
			return null;
	}

	public Link getLink() {
		return getSegment().getLink();
	}

	public boolean isRightmost() {
		return segment.getLanes().length-1==index;
	}

	public boolean isLeftmost() {
		return index==0;
	}

	@Override
	public boolean isLane() {
		return true;
	}

	public float getLeftPosition(float position) {
		if (isLeftmost())
			throw new TrafficSimulationException("Unable to retrive position on left lane, this lane is leftmost.");
		return segment.calculateAdjacentPosition(index, position, Hand.Left);
	}

	public float getRightPosition(float position) {
		if (isRightmost())
			throw new TrafficSimulationException("Unable to retrive position on right lane, this lane is rightmost.");
		return segment.calculateAdjacentPosition(index, position, Hand.Right);
	}
	
}
