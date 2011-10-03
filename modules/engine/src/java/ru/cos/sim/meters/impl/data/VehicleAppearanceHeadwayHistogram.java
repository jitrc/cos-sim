/**
 * 
 */
package ru.cos.sim.meters.impl.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ru.cos.sim.meters.framework.MeasuredData;

/**
 * 
 * @author zroslaw
 */
public class VehicleAppearanceHeadwayHistogram implements MeasuredData<VehicleAppearanceHeadwayHistogram> {

	private float timeStep = 1;
	private List<TrafficVolume> bars = new ArrayList<TrafficVolume>(10);
	
	public VehicleAppearanceHeadwayHistogram(float timeBin){
		this.timeStep = timeBin;
	}

	public void addData(int newcomersNumber, float headway) {
		// calculate bin id
		int binId = (int) (headway/timeStep);
		if (binId>bars.size()-1){
			// increase list of bars
			int numberOfExtraBars = binId-bars.size()+1;
			for(;numberOfExtraBars>0;numberOfExtraBars--){
				bars.add(new TrafficVolume(0));
			}
		}
		TrafficVolume tv = bars.get(binId);
		tv.setValue(tv.intValue()+newcomersNumber);
	}
	
	
	public float getTimeStep() {
		return timeStep;
	}
	public void setTimeStep(float timeStep) {
		this.timeStep = timeStep;
	}
	public List<TrafficVolume> getBars() {
		return bars;
	}
	
	@Override
	public VehicleAppearanceHeadwayHistogram clone() {
		VehicleAppearanceHeadwayHistogram result;
		try {
			result = (VehicleAppearanceHeadwayHistogram) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		List<TrafficVolume> bars = new Vector<TrafficVolume>();
		for(TrafficVolume tv:this.bars){
			bars.add((TrafficVolume) tv.clone());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("VAHH:tb"+timeStep+"-|");
		for (TrafficVolume tv:bars){
			result.append(tv.intValue()+"|");
		}
		return result.toString();
	}

	@Override
	public void normalize(VehicleAppearanceHeadwayHistogram norma) {
		throw new UnsupportedOperationException();
	}
	
}
