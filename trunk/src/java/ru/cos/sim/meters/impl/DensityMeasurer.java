/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.*;
import ru.cos.sim.meters.framework.Measurer;
import ru.cos.sim.meters.impl.data.*;
import ru.cos.sim.road.link.*;

/**
 *
 * @author zroslaw
 */
public class DensityMeasurer implements Measurer<Density>{

	private Density instantDensity = new Density(0);

	private LinkDetector startDetector;
	private LinkDetector endDetector;
	private float length;
	
	private int vehiclesNumber;
	
	public DensityMeasurer(
			Segment statSegment, float startPosition,
			Segment endSegment, float endPosition,
			float length){
		this.length = length;
		startDetector = new LinkDetector(statSegment, startPosition);
		endDetector = new LinkDetector(endSegment, endPosition);
	}

	@Override
	public void measure(float dt) {
		startDetector.detect();
		endDetector.detect();
		vehiclesNumber+=startDetector.getNewVehicles().size();
		vehiclesNumber-=endDetector.getNewVehicles().size();
		instantDensity.setValue(vehiclesNumber*1000.0/length);
	}
	
	@Override
	public Density getInstantData() {
		return instantDensity;
	}

}
