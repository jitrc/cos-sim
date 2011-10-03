package ru.cos.sim.driver.route;

import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.node.TransitionRule;
import ru.cos.sim.vehicle.Vehicle;

public abstract class AbstractRouteProvider implements RouteProvider {

	private int destinationNodeId;
	private Vehicle vehicle;

	private boolean isInitialized = false;
	protected final void checkInitialization() {
		if (!isInitialized) {
			init();
			isInitialized = true;
		}
	}
	
	protected final void dropInitialization() {
		if (isInitialized) {
			deinit();
			isInitialized = false; 
		}
	}
	
	protected void init() {

	}
	
	protected void deinit() {
		
	}

	protected int getDestinationNodeId() {
		return destinationNodeId;
	}
	
	@Override
	public void setDestinationNodeId(int destinationNodeId) {
		this.destinationNodeId = destinationNodeId;
		dropInitialization();
	}
	
	protected Vehicle getVehicle() {
		return vehicle;
	}
	
	@Override
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
		dropInitialization();
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
