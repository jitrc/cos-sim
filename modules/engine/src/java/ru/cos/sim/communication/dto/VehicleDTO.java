/**
 * 
 */
package ru.cos.sim.communication.dto;

import ru.cos.sim.road.init.data.LocationData;
import ru.cos.sim.vehicle.Vehicle;
import ru.cos.sim.vehicle.Vehicle.VehicleClass;

/**
 * Visible vehicle on the visualizer screen
 * @author zroslaw
 */
public class VehicleDTO extends AbstractDTO {
	
	private int agentId;
	private String vehicleId;
	private float length;
	private float width;
	private LocationData location;
	private float speed;
	private VehicleClass vehicleClass;
	
	public VehicleDTO(Vehicle vehicle) {
		this.agentId = vehicle.getAgentId();
		this.vehicleId = vehicle.getVehicleId();
		this.location = LocationData.createInstance(vehicle);
		this.speed = vehicle.getSpeed();
		this.vehicleClass = vehicle.getVehicleClass();
		this.length = vehicle.getLength();
		this.width = vehicle.getWidth();
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setVehicleClass(VehicleClass vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	public LocationData getLocation() {
		return location;
	}

	public void setLocation(LocationData location) {
		this.location = location;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public VehicleClass getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleType(VehicleClass vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	@Override
	public final DTOType getDynamicObjectType() {
		return DTOType.VehicleDTO;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

}