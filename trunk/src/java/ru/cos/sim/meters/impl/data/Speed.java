/**
 * 
 */
package ru.cos.sim.meters.impl.data;

import ru.cos.sim.meters.framework.MeasuredValue;

/**
 * Speed measured value, in meters per second.
 * @author zroslaw
 */
@SuppressWarnings("serial")
public class Speed extends MeasuredValue<Speed> {

	public Speed(Number value) {
		super(value);
	}
	
	@Override
	public String getUnit() {
		return "m/s";
	}


}
