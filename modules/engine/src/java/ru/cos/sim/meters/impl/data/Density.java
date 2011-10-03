/**
 * 
 */
package ru.cos.sim.meters.impl.data;

import ru.cos.sim.meters.framework.MeasuredValue;


/**
 * Measured density value.
 * In vehicles per meter. 
 * @author zroslaw
 */
@SuppressWarnings("serial")
public class Density extends MeasuredValue<Density> {

	public Density(Number value) {
		super(value);
	}

	@Override
	public String getUnit() {
		return "veh/km";
	}


}
