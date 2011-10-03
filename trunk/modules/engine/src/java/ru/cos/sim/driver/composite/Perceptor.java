/**
 * 
 */
package ru.cos.sim.driver.composite;

import ru.cos.sim.road.RoadTrajectory;
import ru.cos.sim.road.link.Lane;

/**
 * Perceptor responsible for creation percepts for a driver.
 * @author zroslaw
 */
public class Perceptor {
//
//	private Driver driver;
	
	private TrajectoryPerceptor trajectoryPerceptor;

	/**
	 * Create perceptor on the base of driver instance
	 * @param driver
	 */
	public Perceptor(CompositeDriver driver) {
//		this.driver = driver;
		this.trajectoryPerceptor = new TrajectoryPerceptor(driver);
	}
	
	public Percepts createPercepts(RoadTrajectory trajectory, float position){
		Percepts percepts = new Percepts();
		
		TrajectoryPercepts currentTrajectoryPercepts = trajectoryPerceptor.createPercepts(trajectory,position);
		TrajectoryPercepts leftTrajectoryPercepts = null;
		TrajectoryPercepts rightTrajectoryPercepts = null;
		
		if (trajectory.isLane()){
			Lane lane = (Lane)trajectory;
			if (!lane.isLeftmost()){
				float leftPosition = lane.getLeftPosition(position);
				leftTrajectoryPercepts = 
					trajectoryPerceptor.createPercepts(lane.getLeftLane(), leftPosition);
			}
			if (!lane.isRightmost()){
				float rightPosition = 
					lane.getRightPosition(position);
				rightTrajectoryPercepts = 
					trajectoryPerceptor.createPercepts(lane.getRightLane(), rightPosition);
			}
		}
		
		percepts.setLeftPercepts(leftTrajectoryPercepts);
		percepts.setCurrentPercepts(currentTrajectoryPercepts);
		percepts.setRightPercepts(rightTrajectoryPercepts);
		
		return percepts;
	}
}
