/**
 * 
 */
package ru.cos.sim.vehicle;

import ru.cos.sim.engine.RoadNetworkUniverse;
import ru.cos.sim.exceptions.TrafficSimulationException;
import ru.cos.sim.road.RoadTrajectory;
import ru.cos.sim.road.init.data.LocationData;
import ru.cos.sim.vehicle.init.data.RegularVehicleData;
import ru.cos.sim.vehicle.init.data.VehicleData;

/**
 * 
 * @author zroslaw
 */
public class VehicleFactory{

	/**
	 * Create vehicle instance by data about it
	 * @param vehicleData data about vehicle
	 * @return instance of the vehicle
	 */
	public static Vehicle createVehicle(VehicleData vehicleData, RoadNetworkUniverse universe) {
		Vehicle vehicle;
		
		switch (vehicleData.getVehicleType()){
		case RegularVehicle:
			RegularVehicleData regularVehicleData = (RegularVehicleData)vehicleData;
			vehicle = RegularVehicleFactory.createRegularVehicle(regularVehicleData, universe);
			break;
		default:
			throw new TrafficSimulationException("Unexpected vehicle type "+vehicleData.getVehicleType());
		}
		
		LocationData location = vehicleData.getLocationData();
		if (location!=null){
			RoadTrajectory trajectory = universe.getTrajectory(location);
			trajectory.putPoint(vehicle, location.getPosition());
			vehicle.setShift(location.getShift());
		}
		
		vehicle.setSpeed(vehicleData.getSpeed());
		
		return vehicle;
	}

}
