/**
 * 
 */
package ru.cos.sim.road;

import ru.cos.cs.lengthy.Lengthy;

/**
 * Trajectory on road network that has its width.
 * @author zroslaw
 */
public interface RoadTrajectory extends Lengthy{

	/**
	 * RoadObjectType of road trajectory
	 * @author zroslaw
	 */
	public enum RoadTrajectoryType{
		Lane,
		NodeFork,
		NodeJoin,
		TransitionRule
	}

	/**
	 * Width of the road network trajectory.
	 * @return trajectory's width
	 */
	public float getWidth();
	
	/**
	 * Set width of the road  trajectory.
	 * @param width width to be set
	 */
	public float setWidth(float width);
	
	/**
	 * Get road trajectory type
	 * @return type of road trajectory
	 */
	public RoadTrajectoryType getRoadTrajectoryType();

	/**
	 * Check for lane
	 * @return true if the trajectory is lane
	 */
	public boolean isLane();

	/**
	 * Check for transition rule
	 * @return true if the trajectory id transition rule
	 */
	public boolean isTransitionRules();

}
