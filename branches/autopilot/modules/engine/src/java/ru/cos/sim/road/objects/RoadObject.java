/**
 * 
 */
package ru.cos.sim.road.objects;

import ru.cos.cs.lengthy.objects.Point;
import ru.cos.sim.Movement;
import ru.cos.sim.utils.Hand;

/**
 * Object on the road which location can be described by triplet of
 * lengthy, position and shift. Shift - it is the distance from the object to the 
 * lengthy's curve. Shift is measured in meters and is 
 * positive if it is on the right from lengthy, negative if on the left.
 * @author zroslaw
 */
public interface RoadObject extends Point {
	
	/**
	 * Types of road objects
	 * @author zroslaw
	 */
	public enum RoadObjectType{
		Vehicle,
		NodeFork,
		NodeJoin,
		EndOfLane,
		StartOfLane,
		TrafficLight,
		/**
		 * Sign on the road
		 */
		RoadSign, 
		/**
		 * Simplest block obstacle
		 */
		Block, 
		/**
		 * Trajectories intersection point in the node
		 */
		IntersectionPoint, 
		/**
		 * Stop line in the intersection
		 */
		StopLine, 
		/**
		 * Any object that can be treated as obstacle for a vehicle
		 */
		Obstacle,
	}
	
	/**
	 * Retrieve object's shift.
	 * @return shift of the object
	 */
	public float getShift();
	
	/**
	 * Set shift for the road object
	 * @param shift shift value to be set
	 */
	public void setShift(float shift);
	
	/**
	 * Get object's speed.
	 * @return speed of the object
	 */
	public Movement getMovement();
	
	/**
	 * Get type of road object.
	 * @return road object type
	 */
	public RoadObjectType getRoadObjectType();

	public boolean isOnLink();
	
	public boolean isOnNode();
	
	/**
	 * Retrieves object's shift hand.
	 * @return shift==0?null:shift>0?Right:Left;
	 */
	public Hand getShiftHand();
}
