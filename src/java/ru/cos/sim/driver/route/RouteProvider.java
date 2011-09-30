package ru.cos.sim.driver.route;

import ru.cos.sim.driver.RoadRoute;
import ru.cos.sim.vehicle.Vehicle;

public interface RouteProvider {

	public void act(float dt);
	
	public RoadRoute getCurrentRoute();
	
	public void setDestinationNodeId(int destinationNodeId);
	
	public void setVehicle(Vehicle vehicle);
}
