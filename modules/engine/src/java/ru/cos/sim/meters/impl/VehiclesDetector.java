/**
 * 
 */
package ru.cos.sim.meters.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.cos.cs.lengthy.Lengthy;
import ru.cos.cs.lengthy.Observation;
import ru.cos.sim.road.objects.RoadObject;
import ru.cos.sim.road.objects.RoadObject.RoadObjectType;
import ru.cos.sim.utils.Pair;
import ru.cos.sim.vehicle.Vehicle;

/**
 * Detect vehicles on some set of lengthies
 * @author zroslaw
 */
public class VehiclesDetector {

	protected float observedDistance = 5f; // 5 meters
	
	protected List<Pair<Lengthy,Float>> lengthiesAndPositions;
	protected Set<Vehicle> detectedVehicles = new HashSet<Vehicle>();
	protected Set<Vehicle> newcomers = new HashSet<Vehicle>();
	
	public void detect(){
		newcomers.clear();
		Set<Vehicle> newSetOfVehicles = new HashSet<Vehicle>();
		for (Pair<Lengthy,Float> pair:lengthiesAndPositions){
			float position = pair.getSecond();
			Lengthy lengthy = pair.getFirst();
			// collect all Observations together
			Collection<Observation> all = new HashSet<Observation>();
			List<Observation> forawrd = lengthy.observeForward(position, observedDistance, null);
			List<Observation> backward = lengthy.observeBackward(position, observedDistance, null);
			all.addAll(forawrd);
			all.addAll(backward);
			// collect new and purge old sets of observable vehicles
			for (Observation observation:all){
				RoadObject roadObject = (RoadObject) observation.getPoint();
				if (roadObject.getRoadObjectType()==RoadObjectType.Vehicle){
					Vehicle vehicleAgent = (Vehicle)roadObject;
					if (!detectedVehicles.contains(vehicleAgent)){
						newcomers.add(vehicleAgent);
					}
					newSetOfVehicles.add(vehicleAgent);
				}
			}
		}
		detectedVehicles = newSetOfVehicles;
	}
	
	public Set<Vehicle> getNewVehicles(){
		return newcomers;
	}
	
	public Set<Vehicle> getVehicles(){
		return detectedVehicles;
	}

	public void setObservedDistance(float observedDistance) {
		this.observedDistance = observedDistance;
	}
}
