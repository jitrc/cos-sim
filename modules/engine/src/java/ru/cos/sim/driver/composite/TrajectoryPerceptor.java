/**
 * 
 */
package ru.cos.sim.driver.composite;

import java.util.List;
import java.util.Vector;

import ru.cos.cs.lengthy.Observation;
import ru.cos.sim.driver.CompositeDriver;
import ru.cos.sim.road.RoadTrajectory;
import ru.cos.sim.road.objects.RoadObject;

/**
 * Trajectory perceptor responsible for creating percepts for a particular trajectory.
 * @author zroslaw
 */
public class TrajectoryPerceptor {

	private float frontVisibleRange = 200.f;

	private float backVisibleRange = 100.f;
	
	private CompositeDriver driver;

	public TrajectoryPerceptor(CompositeDriver driver) {
		this.driver = driver;
	}

	/**
	 * Create percepts for specified trajectory and from specified position.
	 * @param trajectory trajectory to perceive from
	 * @param position position to perceive from
	 * @return
	 */
	public TrajectoryPercepts createPercepts(RoadTrajectory trajectory, float position) {
		TrajectoryPercepts percepts = new TrajectoryPercepts();
		List<Observation> frontObservations = trajectory.observeForward(position, frontVisibleRange, driver.getRouter());
		List<Observation> backObservations = trajectory.observeBackward(position, backVisibleRange, null);

		Perception frontObstacle = null;
		Perception backObstacle = null;
		Perception frontJoin = null;
		Perception frontFork = null;
		List<Perception> frontTrafficLights = new Vector<Perception>();
		List<Perception> roadSigns = new Vector<Perception>();

		// parse front observation
		for (Observation observation:frontObservations){
			RoadObject roadObject = (RoadObject) observation.getPoint();
			switch (roadObject.getRoadObjectType()) {
			case Block:
			case EndOfLane:
			case Vehicle:
				if (frontObstacle==null && roadObject!=driver.getVehicle())
					frontObstacle = new Perception(observation, driver.getVehicle());
				break;
			case NodeFork:
				if (frontFork==null)
					frontFork = new Perception(observation, driver.getVehicle());
				break;
			case NodeJoin:
				if (frontJoin==null)
					frontJoin = new Perception(observation, driver.getVehicle());
				break;
			case TrafficLight:
				frontTrafficLights.add(new Perception(observation, driver.getVehicle()));
				break;
			case RoadSign:
				roadSigns.add(new Perception(observation, driver.getVehicle()));
				break;
			}
		}

		// parse backward observation
		for (Observation observation:backObservations){
			RoadObject roadObject = (RoadObject)observation.getPoint();
			if (Obstacle.getObstacleType(roadObject)!=null && roadObject!=driver.getVehicle()){
					backObstacle = new Perception(observation, driver.getVehicle());
			}
			if (backObstacle!=null) break;
		}
		
		percepts.setBackObstacle(backObstacle);
		percepts.setFrontObstacle(frontObstacle);
		percepts.setFrontFork(frontFork);
		percepts.setFrontJoin(frontJoin);
		percepts.setFrontTrafficLights(frontTrafficLights);
		percepts.setRoadSigns(roadSigns);
		
		return percepts;
	}
}
