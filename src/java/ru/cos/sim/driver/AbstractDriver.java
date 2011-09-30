/**
 * 
 */
package ru.cos.sim.driver;

import ru.cos.sim.vehicle.RegularVehicle;

/**
 * 
 * @author zroslaw
 */
public abstract class AbstractDriver implements Driver{

	protected int destinationNodeId;
	
	protected RegularVehicle vehicle;

	@Override
	public RegularVehicle getVehicle() {
		return vehicle;
	}

	@Override
	public void setVehicle(RegularVehicle vehicle) {
		this.vehicle = vehicle;
	}

	public int getDestinationNodeId() {
		return destinationNodeId;
	}

	public void setDestinationNodeId(int destinationNodeId) {
		this.destinationNodeId = destinationNodeId;
	}

}
