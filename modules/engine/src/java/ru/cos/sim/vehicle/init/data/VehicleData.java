/**
 * 
 */
package ru.cos.sim.vehicle.init.data;

import ru.cos.sim.agents.TrafficAgentData;
import ru.cos.sim.road.init.data.LocationData;
import ru.cos.sim.vehicle.Vehicle.VehicleClass;
import ru.cos.sim.vehicle.Vehicle.VehicleType;

/**
 * Common vehicle agent data
 * @author zroslaw
 */
public abstract class VehicleData implements TrafficAgentData {
	
	protected String vehicleId;
	
	protected LocationData locationData;
	
	protected float speed;

	public abstract VehicleType getVehicleType();
	
	public abstract VehicleClass getVehicleClass();

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public LocationData getLocationData() {
		return locationData;
	}

	public void setLocationData(LocationData locationData) {
		this.locationData = locationData;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
}
