/**
 * 
 */
package ru.cos.sim.driver.composite.cases;

import java.util.List;

import ru.cos.cs.lengthy.Observation;
import ru.cos.cs.lengthy.objects.Point;
import ru.cos.sim.driver.CompositeDriver;
import ru.cos.sim.driver.composite.CompositeDriverParameters;
import ru.cos.sim.driver.composite.Obstacle;
import ru.cos.sim.driver.composite.Perception;
import ru.cos.sim.driver.composite.Percepts;
import ru.cos.sim.driver.composite.TrajectoryPercepts;
import ru.cos.sim.driver.composite.cases.utils.IDMCalculator;
import ru.cos.sim.driver.composite.framework.AbstractBehaviorCase;
import ru.cos.sim.driver.composite.framework.CCRange;
import ru.cos.sim.driver.composite.framework.Priority;
import ru.cos.sim.driver.composite.framework.RectangleCCRange;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.node.NodeFork;
import ru.cos.sim.road.node.NodeForkPoint;
import ru.cos.sim.road.node.TransitionRule;
import ru.cos.sim.road.objects.RoadObject;
import ru.cos.sim.road.objects.RoadObject.RoadObjectType;
import ru.cos.sim.vehicle.Vehicle;

/**
 * 
 * @author zroslaw
 */
public class RespectNodeCase extends AbstractBehaviorCase {
	/**
	 * Characteristic time needed for vehicle to cross the node
	 */
	private static float nodeCrossingTime = 2.f;
	
	private IDMCalculator idmCalculator;

	public RespectNodeCase(CompositeDriver driver) {
		super(driver);
		idmCalculator = new IDMCalculator();
	}

	public void init(CompositeDriverParameters parameters){
		this.idmCalculator.init(parameters);
	}
	
	@Override
	public CCRange behave(float dt) {
		Percepts percepts = driver.getPercepts();
		TrajectoryPercepts currentPercepts = percepts.getCurrentPercepts();
		
		Perception frontForkPerception = currentPercepts.getFrontFork();
		Perception frontJoinPerception = currentPercepts.getFrontJoin();
		if (frontForkPerception==null ||
			frontJoinPerception==null ||
			frontForkPerception.getDistance()>frontJoinPerception.getDistance()) 
			return null;
		
		NodeFork nodeFork = ((NodeForkPoint)frontForkPerception.getRoadObject()).getNodeFork();
		
		// retrieve desired TR
		TransitionRule transitionRule = (TransitionRule) driver.chooseNextLengthy(nodeFork);
		
		// calculate length of the queue that may form this vehicle with vehicles on transition rule
		float queueLength = driver.getVehicle().getLength()+2.f; // plus 2 meters for min distance
		List<Point> points = transitionRule.getRegularPoints();
		for (Point point:points){
			RoadObject roadObject = (RoadObject)point;
			if (roadObject.getRoadObjectType()==RoadObjectType.Vehicle){
				Vehicle vehicle = (Vehicle)roadObject;
				queueLength+=vehicle.getLength()+2.f;
			}
		}
		
		// check that it is enough space on the outgoing lane to accommodate the queue
		Lane outgoingLane = transitionRule.getOutgoingLane();
		List<Observation> observations = outgoingLane.observeForward(0, queueLength, null);
		for (Observation observation:observations){
			RoadObject roadObject = (RoadObject)observation.getPoint();
			if (Obstacle.getObstacleType(roadObject)!=null){
				// obstacle was found 
				float futureQueueAccommodation = 
					new Perception(observation,driver.getVehicle()).getDistance() +
					roadObject.getSpeed()*nodeCrossingTime;
				if (futureQueueAccommodation>queueLength) return null;
				RectangleCCRange ccRange = new RectangleCCRange();
				float acceleration = idmCalculator.calculate(driver.getVehicle(), frontForkPerception);
				ccRange.getAccelerationRange().setHigher(acceleration);
				ccRange.setPriority(Priority.RespectNodeCase);
				return ccRange;
			}
		}
		
		return null;
	}

}
