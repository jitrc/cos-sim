/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.framework.Measurer;
import ru.cos.sim.meters.impl.data.Flow;
import ru.cos.sim.road.link.Segment;
import ru.cos.sim.utils.ExpiringList;

/**
 *
 * @author zroslaw
 */
public class FlowMeasurer implements Measurer<Flow> {

	private float clock = 0;
	private float measuringTime = 60;
	private ExpiringList<Integer> appearedVehicles = new ExpiringList<Integer>();
	private LinkDetector linkDetector;

	public FlowMeasurer(Segment segment, float position){
		linkDetector = new LinkDetector(segment, position);
	}
	
	public FlowMeasurer(Segment segment, float position, float measuringTime){
		linkDetector = new LinkDetector(segment, position);
		this.measuringTime = measuringTime;
	}

	@Override
	public void measure(float dt) {
		clock+=dt;
		linkDetector.detect();
		appearedVehicles.add(clock, linkDetector.getNewVehicles().size());
	}
	
	@Override
	public Flow getInstantData() {
		appearedVehicles.extractAllOlderThan(clock - measuringTime);
		
		int total = 0;
		for (int count : appearedVehicles)
			total += count;
		
		return new Flow(total / measuringTime * 3600); 
	}
	
}
