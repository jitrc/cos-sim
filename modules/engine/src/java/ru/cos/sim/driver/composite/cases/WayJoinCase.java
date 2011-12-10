/**
 * 
 */
package ru.cos.sim.driver.composite.cases;

import java.util.List;
import java.util.Set;

import ru.cos.cs.lengthy.Observation;
import ru.cos.cs.lengthy.RegularLengthy;
import ru.cos.sim.driver.CompositeDriver;
import ru.cos.sim.driver.composite.CompositeDriverParameters;
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
import ru.cos.sim.road.objects.RoadObject;
import ru.cos.sim.road.objects.RoadObject.RoadObjectType;
import ru.cos.sim.vehicle.Vehicle;

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
		Perception frontObstaclePerception = currentPercepts.getFrontObstacle();
		if (frontJoinPerception==null) return null;
		if (frontObstaclePerception!=null &&
			frontObstaclePerception.getActualDistance()<frontJoinPerception.getActualDistance())
			return null;
		
		NodeJoin nodeJoin = ((NodeJoinPoint)frontJoinPerception.getRoadObject()).getNodeJoin();
		float joinDistance = frontJoinPerception.getActualDistance();
		Set<RegularLengthy> joinedWays = nodeJoin.getJoinedLengthies();
		
		float nearestDistance = 0;
		// find nearest vehicle on joined lengthies
		Vehicle nearestVehicle = null;
		for (RegularLengthy joinedLengthy:joinedWays){
			if (joinedLengthy==driver.getVehicle().getLengthy()) continue;
			List<Observation> backwardObservations = 
				joinedLengthy.observeBackward(
						joinedLengthy.getLength(),
						joinDistance, 
						null);
			//find first vehicle
			Observation vehicleObservation = null;
			for (Observation observation:backwardObservations){
				RoadObject roadObject = (RoadObject) observation.getPoint();
				if (roadObject.getRoadObjectType()==RoadObjectType.Vehicle){
					vehicleObservation = observation;
				}					
			}
			
			if (vehicleObservation==null) continue;
			Vehicle vehicle = (Vehicle) vehicleObservation.getPoint();
			float distanceToJoin = -vehicleObservation.getDistance();
			if (distanceToJoin>nearestDistance && distanceToJoin<joinDistance){
				nearestDistance = distanceToJoin;
				nearestVehicle = vehicle;
			}
		}
		
		if (nearestVehicle==null) return null;
		
		// calculate acceleration using "phantom" vehicle
		RectangleCCRange ccRange = new RectangleCCRange();
		idmCalculator.setSpeed(driver.getVehicle().getMovement().getSpeed());
		idmCalculator.setFrontVehicleSpeed(nearestVehicle.getMovement().getSpeed());
		idmCalculator.setDistance(2*joinDistance-nearestDistance-nearestVehicle.getHalfLength()-driver.getVehicle().getHalfLength());
		float acceleration = idmCalculator.calculate();
		ccRange.getAccelerationRange().setHigher(acceleration);
		ccRange.setPriority(Priority.WayJoinCase);
		return ccRange;
	}

}
