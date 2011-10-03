/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.framework.Measurer;
import ru.cos.sim.meters.impl.data.Speed;
import ru.cos.sim.road.link.Link;

/**
 * Link Average Travel Speed measurer
 * @author zroslaw
 */
public class LATSMeasurer implements Measurer<Speed> {

	private SATSMeasurer satsMeasurer;	
	
	public LATSMeasurer(Link link){
		this.satsMeasurer = new SATSMeasurer(
				link.getFirstSegment(), 0,
				link.getLastSegment(), link.getLastSegment().getLength(),
				link.getLength());
	}
	
	@Override
	public Speed getInstantData() {
		return satsMeasurer.getInstantData();
	}

	@Override
	public void measure(float dt) {
		satsMeasurer.measure(dt);
	}

}
