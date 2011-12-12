package ru.cos.sim.driver.route;

import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.node.TransitionRule;
import ru.cos.sim.vehicle.Vehicle;

public abstract class AbstractRouteProvider implements RouteProvider {

	private int destinationNodeId;
	private Vehicle vehicle;

	protected int getDestinationNodeId() {
		return destinationNodeId;
	}
	
	public AbstractRouteProvider(int destinationNodeId) {
		this.destinationNodeId = destinationNodeId;
	}
	
	@Override
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	protected int getSourceLinkId() {
		Object lengthy = vehicle.getLengthy();
		if (lengthy instanceof Lane)
			return ((Lane)lengthy).getLink().getId();
		else if (lengthy instanceof TransitionRule)
			return ((TransitionRule)lengthy).getNextLink().getId();
		else
			throw new RuntimeException("Lengthy type " + lengthy.getClass() + " is not supported by " + getClass());
	}

}
