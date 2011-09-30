/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.cs.lengthy.Lengthy;
import ru.cos.sim.meters.framework.Measurer;
import ru.cos.sim.meters.impl.data.TrafficVolume;
import ru.cos.sim.road.link.Segment;

/**
 *
 * @author zroslaw
 */
public class TrafficVolumeMeasurer implements Measurer<TrafficVolume> {

	private VehiclesDetector detector;
	
	private int cumulativeVolume = 0;
	private TrafficVolume trafficVolume = new TrafficVolume(0);
	
	public TrafficVolumeMeasurer(Segment segment, float position) {
		detector = new LinkDetector(segment, position);		
	}

	public TrafficVolumeMeasurer(Lengthy lengthy, float position) {
		detector = new LengthyDetector(lengthy, position);		
	}
	
	@Override
	public TrafficVolume getInstantData() {
		return trafficVolume;
	}

	@Override
	public void measure(float dt) {
		detector.detect();
		cumulativeVolume+=detector.getNewVehicles().size();
		trafficVolume.setValue(cumulativeVolume);
	}
	
	public void reset(){
		cumulativeVolume = 0;
	}
}
