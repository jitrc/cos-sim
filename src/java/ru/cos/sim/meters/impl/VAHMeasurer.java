/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.framework.Measurer;
import ru.cos.sim.meters.impl.data.VehicleAppearanceHeadwayHistogram;
import ru.cos.sim.road.link.Segment;

/**
 * VehicleAgent Appearance Headway Histogram Measurer
 * @author zroslaw
 */
public class VAHMeasurer implements Measurer<VehicleAppearanceHeadwayHistogram> {

	private LinkDetector linkDetector;
	private float headway = 0; 
	private boolean isWaiting = true;// wait for first vehicleAgent to start measurement
	
	private VehicleAppearanceHeadwayHistogram instantHistogram;
	
	public VAHMeasurer(Segment segment,float position, float timeBin){
		linkDetector = new LinkDetector(segment, position);
		instantHistogram = new VehicleAppearanceHeadwayHistogram(timeBin);
	}
	
	@Override
	public VehicleAppearanceHeadwayHistogram getInstantData() {
		return instantHistogram;
	}

	@Override
	public void measure(float dt) {
		linkDetector.detect();
		int newcomersNumber = linkDetector.getNewVehicles().size();
		if(isWaiting){
			if (newcomersNumber>0) 
				isWaiting=false;
			return;
		}
		headway+=dt;
		if(newcomersNumber>0){
			instantHistogram.addData(newcomersNumber,headway);
			headway=0;
		}
	}
}
