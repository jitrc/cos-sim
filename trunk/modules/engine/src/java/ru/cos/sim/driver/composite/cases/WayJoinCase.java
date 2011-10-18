/**
 * 
 */
package ru.cos.sim.driver.composite.cases;

import java.util.List;
import java.util.Set;

import ru.cos.cs.lengthy.Observation;
import ru.cos.cs.lengthy.RegularLengthy;
import ru.cos.sim.driver.composite.CompositeDriver;
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
import ru.cos.sim.road.node.NodeJoin;
import ru.cos.sim.road.node.NodeJoinPoint;

/**
 * 
 * @author zroslaw
 */
public class WayJoinCase extends AbstractBehaviorCase {
	
	private IDMCalculator idmCalculator;

	public WayJoinCase(CompositeDriver driver) {
		super(driver);
		this.idmCalculator = new IDMCalculator();
	}
	
	public void init(CompositeDriverParameters parameters){
		this.idmCalculator.init(parameters);
	}

	@Override
	public CCRange behave(float dt) {
		Percepts percepts = driver.getPercepts();
		TrajectoryPercepts currentPercepts = percepts.getCurrentPercepts();
		Perception frontJoinPerception = currentPercepts.getFrontJoin();
		if (frontJoinPerception==null) return null;
		
		NodeJoin nodeJoin = ((NodeJoinPoint)frontJoinPerception.getRoadObject()).getNodeJoin();
		float joinDistance = frontJoinPerception.getDistance();
		Set<RegularLengthy> joinedWays = nodeJoin.getJoinedLengthies();
		
		float minDistance = joinDistance;
		// find nearest movable obstacle
		Perception nearestObstacle = null;
		for (RegularLengthy joinedLengthy:joinedWays){
			if (joinedLengthy==driver.getVehicle().getLengthy()) continue;
			List<Observation> backwardObservations = 
				joinedLengthy.observeBackward(
						joinedLengthy.getLength(),
						joinDistance+5, 
						null);
			Observation behindObstacleObservation = Obstacle.findFirstObstacle(backwardObservations);
			if (behindObstacleObservation==null) continue;
			
			Perception behindObstacle = new Perception(behindObstacleObservation,driver.getVehicle());
			float obstacleDistance = -behindObstacle.getDistance();
			if (obstacleDistance>=minDistance) continue;

			if(nearestObstacle==null){
				nearestObstacle = behindObstacle;
				minDistance = obstacleDistance;
			}
		}
		
		if (nearestObstacle==null) return null;
		
		// calculate acceleration
		RectangleCCRange ccRange = new RectangleCCRange();
		idmCalculator.setSpeed(driver.getVehicle().getSpeed());
		idmCalculator.setFrontVehicleSpeed(nearestObstacle.getRoadObject().getSpeed());
		idmCalculator.setDistance(joinDistance);
		float acceleration = idmCalculator.calculate();
		ccRange.getAccelerationRange().setHigher(acceleration);
		ccRange.setPriority(Priority.WayJoinCase);
		return ccRange;
	}

}
