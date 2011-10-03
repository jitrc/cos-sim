/**
 * 
 */
package ru.cos.sim.driver.composite.cases.utils;

import ru.cos.sim.driver.composite.Perception;
import ru.cos.sim.road.objects.AbstractRoadObject;
import ru.cos.sim.road.objects.RoadObject;
import ru.cos.sim.road.objects.RoadObject.RoadObjectType;
import ru.cos.sim.vehicle.Vehicle;

/**
 * Calculate MOBIL Model lane change decision.<br>
 * acc' (M') - acc (M) > p [ acc (B) + acc (B') - acc' (B) - acc' (B') ] + athr
 * @author zroslaw
 */
public class MOBILCalculator {

	private Perception Bnext; // backward vehicle on the next lane
	private Perception B; // backward vehicle on the current lane
	private Perception Fnext; // front vehicle on the next lane
	private Perception F; // front vehicle on the current lane

	private IDMCalculator idmCalculator;
	
	private float politeness = 0.5f;
	private float thresholdAcceleration = 0.2f;
	
	public MOBILCalculator() {
		idmCalculator = new IDMCalculator();
	}
	
	public void init(MOBILDriverParameters parameters){
		idmCalculator.init(parameters);
		politeness = parameters.getPoliteness();
	}

	/**
	 * Calculate turn criterion estimation.
	 * Estimation is in term of difference between vehicle's acceleration
	 * on the next lane and its current acceleration
	 * @param vehicle vehicle to which calculate lane change profit estimation 
	 * @param backObstacle first back obstacle on the vehicle's current lane
	 * @param frontObstacle first front obstacle on the vehicle's current lane
	 * @param nextLaneBackObstacle first back obstacle on the lane we estimate profit for
	 * @param nextLaneFrontObstacle first front obstacle on the lane we estimate profit for
	 * @return profit of lane changing
	 */
	public float calculate(
			Vehicle vehicle,
			Perception backObstacle,
			Perception frontObstacle,
			Perception nextLaneBackObstacle,
			Perception nextLaneFrontObstacle){
		
		F = frontObstacle;
		B = backObstacle;
		Fnext = nextLaneFrontObstacle;
		Bnext = nextLaneBackObstacle;
		
		// calculate accB
		float accB = calculateAcceleration(B, vehicle);
		float accBnext = calculateAcceleration(Bnext, Fnext);
		float accBafter = calculateAcceleration(B, F);
		float accBnextAfter = calculateAcceleration(Bnext, vehicle);
		float accM = calculateAcceleration(vehicle, F);
		float accMafter = calculateAcceleration(vehicle, Fnext);
		
		float leftPart = accMafter - accM;
		float rightPart = politeness*(accB-accBafter+accBnext-accBnextAfter)+thresholdAcceleration;
		
		return leftPart-rightPart;
	}

	private float calculateAcceleration(Vehicle vehicle, Perception frontPerception) {
		if (frontPerception==null) return idmCalculator.getMaxAcceleration();
		return calculateAcceleration(vehicle, frontPerception.getRoadObject(), frontPerception.getDistance());
	}

	private float calculateAcceleration(Perception perception, Perception obstacle) {
		if (perception!=null && perception.getRoadObjectType()==RoadObjectType.Vehicle){
			if (obstacle==null) return idmCalculator.getMaxAcceleration();
			return calculateAcceleration(
					(Vehicle)perception.getRoadObject(), 
					obstacle.getRoadObject(), 
					obstacle.getDistance()-perception.getDistance());
		}
		return 0;
	}

	/**
	 * Calculate acceleration for perception
	 * @param perception 
	 * @param vehicle
	 * @return 0 if perception is not vehicle, or estimated vehicle's perception
	 */
	private float calculateAcceleration(Perception perception, Vehicle vehicle) {
		if (perception!=null && perception.getRoadObjectType()==RoadObjectType.Vehicle){
			return calculateAcceleration((Vehicle)perception.getRoadObject(), vehicle, perception.getDistance());
		}
		return 0;
	}

	private float calculateAcceleration(Vehicle vehicle, RoadObject obstacle, float distance) {
		idmCalculator.setFrontVehicleSpeed(obstacle.getSpeed());
		idmCalculator.setSpeed(vehicle.getSpeed());
		idmCalculator.setDistance(distance);
		return idmCalculator.calculate();
	}
	
//	private float calculateAcceleration(RoadObject vehicle, Perception obstacle){
//		if (vehicle==null) vehicle = new Perception(-Float.MAX_VALUE, new DumbRoadObject(0)); // NO_BACKWARD_OBSTACLE;
//		if (obstacle==null) obstacle = new Perception(Float.MAX_VALUE, new DumbRoadObject(0)); // NO_FRONT_OBSTACLE;
//		idmCalculator.setFrontVehicleSpeed(obstacle.getRoadObject().getSpeed());
//		idmCalculator.setSpeed(vehicle.getRoadObject().getSpeed());
//		idmCalculator.setDistance(obstacle.getDistance()-vehicle.getDistance());
//		return idmCalculator.calculate();
//	}
	
}

class DumbRoadObject extends AbstractRoadObject{

	public DumbRoadObject(float speed){
		this.speed = speed;
	}
	
	@Override
	public RoadObjectType getRoadObjectType() {
		return null;
	}
	
}