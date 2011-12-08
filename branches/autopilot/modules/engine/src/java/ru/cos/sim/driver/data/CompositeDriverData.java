/**
 * 
 */
package ru.cos.sim.driver.data;

import java.util.List;

import ru.cos.sim.driver.Driver.DriverType;
import ru.cos.sim.driver.composite.CompositeDriverParameters;


/**
 * 
 * @author zroslaw
 */
public class CompositeDriverData extends DriverData {

	protected List<Integer> route;
	
	protected int destinationNodeId;

	public CompositeDriverParameters getParameters() {
		return parameters;
	}

	public void setParameters(CompositeDriverParameters parameters) {
		this.parameters = parameters;
	}

	protected CompositeDriverParameters parameters;
	
	@Override
	public final DriverType getDriverType() {
		return DriverType.Composite;
	}

	public List<Integer> getRoute() {
		return route;
	}

	public void setRoute(List<Integer> route) {
		this.route = route;
	}

	public int getDestinationNodeId() {
		return destinationNodeId;
	}

	public void setDestinationNodeId(int destinationNodeId) {
		this.destinationNodeId = destinationNodeId;
	}
	
}
