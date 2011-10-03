package ru.cos.sim.driver.route;

import ru.cos.sim.driver.RoadRoute;
import ru.cos.sim.vehicle.Vehicle;

public class StaticRouteProvider implements RouteProvider {
	public StaticRouteProvider(RoadRoute initialRoute) {
		this.route = initialRoute;
	}
	
	private final RoadRoute route;
	
	@Override
	public void act(float dt) {
		
	}
	
	@Override
	public RoadRoute getCurrentRoute() {
		return route;
	}
	
	@Override
	public void setDestinationNodeId(int destinationNodeId) {
		
	}

	@Override
	public void setVehicle(Vehicle vehicle) {
		
	}
}
