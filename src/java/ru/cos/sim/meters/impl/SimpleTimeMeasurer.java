/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.framework.Measurer;
import ru.cos.sim.meters.impl.data.Time;

/**
 *
 * @author zroslaw
 */
public class SimpleTimeMeasurer implements Measurer<Time> {

	private Time time = new Time(0);
	
	@Override
	public Time getInstantData() {
		return time;
	}

	@Override
	public void measure(float dt) {
		// do nothing
	}

	public void setTime(float time){
		this.time.setValue(time);
	}
}
