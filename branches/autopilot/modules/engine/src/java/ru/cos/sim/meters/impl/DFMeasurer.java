/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.framework.Measurer;
import ru.cos.sim.meters.impl.data.Density;
import ru.cos.sim.meters.impl.data.DensityFlow;
import ru.cos.sim.meters.impl.data.Flow;
import ru.cos.sim.road.link.Segment;

/**
 * Density-Flow measurer
 * @author zroslaw
 */
public class DFMeasurer implements Measurer<DensityFlow> {

	private DensityFlow instantDensityFlow = 
		new DensityFlow(new Density(0), new Flow(0));
	
	private DensityMeasurer densityMeasurer;
	private FlowMeasurer flowMeasurer;
	
	
	public DFMeasurer(
			Segment startSegment, float startPosition,
			Segment endSegment, float endPosition,
			float length, float measuringTime){
		this.densityMeasurer = 
			new DensityMeasurer(
					startSegment, 
					startPosition, 
					endSegment, 
					endPosition, 
					length);
		this.flowMeasurer = new FlowMeasurer(endSegment, endPosition);
	}
	
	@Override
	public DensityFlow getInstantData() {
		return instantDensityFlow;
	}

	@Override
	public void measure(float dt) {
		densityMeasurer.measure(dt);
		flowMeasurer.measure(dt);
		instantDensityFlow.setFlow(flowMeasurer.getInstantData());
		instantDensityFlow.setDensity(densityMeasurer.getInstantData());
	}

}
