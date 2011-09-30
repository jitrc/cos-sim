/**
 * 
 */
package ru.cos.sim.meters.impl.data;

import ru.cos.sim.meters.framework.MeasuredValue;


/**
 * 
 * @author zroslaw
 */
@SuppressWarnings("serial")
public class TrafficVolume extends MeasuredValue<TrafficVolume>{

	public TrafficVolume(Number value) {
		super(value);
	}

	@Override
	public String getUnit() {
		return "pcu";
	}

}
