/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.framework.ModesInitData;
import ru.cos.sim.meters.impl.data.Density;
import ru.cos.sim.road.link.Segment;

/**
 *
 * @author zroslaw
 */
public class DensityMeter extends AbstractMeter<Density> {

	public DensityMeter(
			int id, 
			ModesInitData modesInitData,
			Segment startSegment, float startPosition,
			Segment endSegment, float endPosition,
			float length) {
		super(
				id, 
				MeterType.DensityMeter, 
				modesInitData, 
				new DensityMeasurer(startSegment, startPosition, endSegment, endPosition, length), 
				new DumbAverageDataCollectorFactory<Density>());

	}

}
