/**
 * 
 */
package ru.cos.sim.road.init.data;

import ru.cos.sim.exceptions.TrafficSimulationException;
import ru.cos.sim.road.RoadTrajectory;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.node.TransitionRule;
import ru.cos.sim.vehicle.Vehicle;

/**
 * Data about specific location in the road network.
 * @author zroslaw
 */
public abstract class LocationData {
	
	public enum LocationType{
		LinkLocation,
		NodeLocation
	}
	
	protected float position;
	
	protected float shift;

	public abstract LocationType getLocationType();
	
	public float getPosition() {
		return position;
	}

	public void setPosition(float position) {
		this.position = position;
	}

	public float getShift() {
		return shift;
	}

	public void setShift(float shift) {
		this.shift = shift;
	}

	public static LocationData createInstance(RoadTrajectory roadTrajectory) {
		LocationData locationData;
		switch (roadTrajectory.getRoadTrajectoryType()){
			case Lane:
				locationData = new LinkLocationData((Lane)roadTrajectory);
				break;
			case TransitionRule:
				locationData = new NodeLocationData((TransitionRule)roadTrajectory);
				break;
			default:
				throw new TrafficSimulationException("Unexpected road trajectory type "+roadTrajectory.getRoadTrajectoryType());
		}
		return locationData;
	}

	public static LocationData createInstance(Vehicle vehicle) {
		LocationData locationData = createInstance((RoadTrajectory) vehicle.getLengthy());
		locationData.setPosition(vehicle.getPosition());
		locationData.setShift(vehicle.getShift());
		return locationData;
	}
	
}
