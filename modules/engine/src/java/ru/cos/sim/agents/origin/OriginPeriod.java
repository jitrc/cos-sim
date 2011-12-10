/**
 * 
 */
package ru.cos.sim.agents.origin;

import java.util.HashSet;
import java.util.Set;



import ru.cos.sim.driver.CompositeDriver;
import ru.cos.sim.driver.Driver;
import ru.cos.sim.driver.Driver.DriverType;
import ru.cos.sim.engine.RoadNetworkUniverse;
import ru.cos.sim.utils.ProbabilityArray;
import ru.cos.sim.vehicle.RegularVehicle;
import ru.cos.sim.vehicle.Vehicle;
import ru.cos.sim.vehicle.VehicleFactory;
import ru.cos.sim.vehicle.Vehicle.VehicleType;
import ru.cos.sim.vehicle.init.data.VehicleData;


/**
 * 
 * @author zroslaw
 */
public class OriginPeriod {
	
	private float duration;
	
	private int numberOfVehicles;
	
	private ProbabilityArray<Integer> destinations;
	
	private ProbabilityArray<VehicleData> vehicleProfiles;
	
	private RoadNetworkUniverse universe;
	
	private float periodTimer = 0;
	
	private int numberOfOriginatedVehicles = 0;

	/**
	 * Initialize period before its beginning.
	 */
	public void init() {
		periodTimer = 0;
		numberOfOriginatedVehicles = 0;
	}
	
	/**
	 * Checks if period is active.<br>
	 * Period is active when its was active for a time less than its duration. 
	 * @return active status
	 */
	public boolean isActive(){
		return periodTimer<duration;
	}

	/**
	 * Originate set of vehicle's instances for another time step dt;
	 * @param dt time step passed
	 * @return set of originated vehicle's instances
	 */
	public Set<Vehicle> originateVehicles(float dt) {
		Set<Vehicle> originatedVehicles = new HashSet<Vehicle>();
		if (periodTimer>duration) return originatedVehicles;
		
		periodTimer+=dt;
		
		// calculate number of vehicles that must be already originated on this step
		int vehiclesToBeOriginated =  (int) (numberOfVehicles*periodTimer/duration);
		// originate one more vehicle until 
		// number of vehicles to be originated exceeds number of already originated vehicles
		while (vehiclesToBeOriginated-numberOfOriginatedVehicles>0){
			originatedVehicles.add(originateVehicle());
		}
		
		return originatedVehicles;
	}
	
	/**
	 * Originate one more vehicle
	 * @return instance of originated vehicle
	 */
	private Vehicle originateVehicle(){
		VehicleData vehicleData = vehicleProfiles.getArbitraryElement();
		Vehicle vehicle = VehicleFactory.createVehicle(vehicleData, universe);

		// set destinationNodeId for RegularVehicle-CompositeDriver pair
		if(vehicle.getVehicleType()==VehicleType.RegularVehicle){
			Driver driver = ((RegularVehicle)vehicle).getDriver();
			if (driver.getDriverType()==DriverType.Composite){
				CompositeDriver compositeDriver = (CompositeDriver)driver;
				int destinationNodeId = destinations.getArbitraryElement();
				compositeDriver.setDestinationNodeId(destinationNodeId);
			}
		}
		numberOfOriginatedVehicles++;
		return vehicle;
	}
	
	public void setUniverse(RoadNetworkUniverse universe) {
		this.universe = universe;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public float getDuration() {
		return duration;
	}

	public ProbabilityArray<Integer> getDestinations() {
		return destinations;
	}

	public void setDestinations(ProbabilityArray<Integer> destinations) {
		this.destinations = destinations;
	}

	public ProbabilityArray<VehicleData> getVehicleProfiles() {
		return vehicleProfiles;
	}

	public void setVehicleProfiles(ProbabilityArray<VehicleData> vehicleProfiles) {
		this.vehicleProfiles = vehicleProfiles;
	}

	public void setNumberOfVehicles(int numberOfVehicles) {
		this.numberOfVehicles = numberOfVehicles;
	}

	public int getNumberOfVehicles() {
		return numberOfVehicles;
	}

}
