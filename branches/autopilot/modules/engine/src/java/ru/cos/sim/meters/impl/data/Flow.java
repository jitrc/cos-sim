/**
 * 
 */
package ru.cos.sim.meters.impl.data;

import ru.cos.sim.meters.framework.MeasuredValue;

/**
 * Flow of the traffic stream on the road.
 * Measured in the vehicles per hour units.
 * @author zroslaw
 */
@SuppressWarnings("serial")
public class Flow extends MeasuredValue<Flow> {

	public Flow(Number value) {
		super(value);
	}
	
	@Override
	public String getUnit() {
		return "veh/h";
	}


}
