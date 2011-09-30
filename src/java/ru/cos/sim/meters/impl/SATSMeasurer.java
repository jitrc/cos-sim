/**
 * 
 */
package ru.cos.sim.meters.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ru.cos.sim.meters.framework.Measurer;
import ru.cos.sim.meters.impl.data.Speed;
import ru.cos.sim.road.link.Segment;
import ru.cos.sim.vehicle.Vehicle;

/**
 * Section Average Travel Speed Measurer
 * @author zroslaw
 */
public class SATSMeasurer implements Measurer<Speed> {
	
	private Map<Vehicle,Float> incomingTime = new HashMap<Vehicle, Float>();;
	private float speedSumm = 0;
	private float arrivedVehiclesCounter = 0;
	private float clock = 0;
	private float sectionLength;

	private LinkDetector startDetector;
	private LinkDetector endDetector;
	
	private Speed satSpeed = new Speed(0);
	
	public SATSMeasurer(
			Segment startSegment, float startPosition,
			Segment endSegment, float endPosition,
			float sectionLength){
		startDetector = new LinkDetector(startSegment, startPosition);
		endDetector = new LinkDetector(endSegment, endPosition);
		this.sectionLength = sectionLength;
	}
	
	@Override
	public Speed getInstantData() {
		return satSpeed;
	}

	@Override
	public void measure(float dt) {
		clock+=dt;
		startDetector.detect();
		Set<Vehicle> incomingVehicles = startDetector.getNewVehicles();
		for (Vehicle vehicle:incomingVehicles){
			incomingTime.put(vehicle, clock);
		}
		
		endDetector.detect();
		Set<Vehicle> arrivedVehicles = endDetector.getNewVehicles();
		for (Vehicle vehicle:arrivedVehicles){
			arrivedVehiclesCounter++;
			speedSumm += sectionLength/(clock-incomingTime.remove(vehicle));
		}
		
		satSpeed.setValue(speedSumm/arrivedVehiclesCounter);
	}
}

/**
 * Comparison of vehicles by id.
 * To store in fast tree map.
 * @author zroslaw
 */
class VehicleComparator implements Comparator<Vehicle>{
	@Override
	public int compare(Vehicle v1, Vehicle v2) {
		if (v1.getAgentId()>v2.getAgentId())
			return 1;
		if (v1.getAgentId()<v2.getAgentId())
			return -1;
		return 0;
	}
}
