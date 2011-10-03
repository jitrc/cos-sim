/**
 * 
 */
package ru.cos.sim.vehicle.init.data;

import ru.cos.sim.agents.TrafficAgent.TrafficAgentType;
import ru.cos.sim.driver.data.DriverData;
import ru.cos.sim.vehicle.Vehicle.VehicleClass;
import ru.cos.sim.vehicle.Vehicle.VehicleType;

/**
 * Regular vehicle data.
 * @author zroslaw
 */
public class RegularVehicleData extends VehicleData {
	
	protected float length;
	
	protected float width;
	
	protected VehicleClass vehicleClass;
	
	protected DriverData driverData;
	
	@Override
	public final VehicleType getVehicleType() {
		return VehicleType.RegularVehicle;
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

	public DriverData getDriverData() {
		return driverData;
	}

	public void setDriverData(DriverData driverData) {
		this.driverData = driverData;
	}

	@Override
	public VehicleClass getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(VehicleClass vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	@Override
	public final TrafficAgentType getTrafficAgentType() {
		return TrafficAgentType.Vehicle;
	}

}
