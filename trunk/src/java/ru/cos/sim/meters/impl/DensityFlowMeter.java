/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.*;
import ru.cos.sim.meters.framework.ModesInitData;
import ru.cos.sim.meters.impl.data.*;
import ru.cos.sim.road.link.*;

/**
 *
 * @author zroslaw
 */
public class DensityFlowMeter extends AbstractMeter<DensityFlow> {

	public DensityFlowMeter(
			int id,
			ModesInitData modesInitData,
			Segment startSegment, float startPosition,
			Segment endSegment, float endPosition,
			float length, float measuringTime) {
		super(
				id, 
				MeterType.DensityFlowMeter, 
				modesInitData, 
				new DFMeasurer(startSegment, startPosition, endSegment, endPosition, length, measuringTime), 
				new DumbAverageDataCollectorFactory<DensityFlow>());
	}

}
