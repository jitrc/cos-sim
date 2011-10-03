/**
 * 
 */
package ru.cos.sim.meters.impl.data;

import ru.cos.sim.meters.framework.MeasuredValue;


/**
 * Time in seconds.
 * @author zroslaw
 */
@SuppressWarnings("serial")
public class Time extends MeasuredValue<Time> {

	public Time(Number value) {
		super(value);
	}

	@Override
	public String getUnit() {
		return "second(s)";
	}

}
