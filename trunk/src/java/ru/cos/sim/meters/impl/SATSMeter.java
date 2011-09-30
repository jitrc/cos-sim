/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.framework.ModesInitData;
import ru.cos.sim.meters.impl.data.Speed;
import ru.cos.sim.road.link.Segment;

/**
 *
 * @author zroslaw
 */
public class SATSMeter extends AbstractMeter<Speed>{

	public SATSMeter(
			int id, 
			ModesInitData modesInitData,
			Segment startSegment, float startPosition,
			Segment endSegment, float endPosition,
			float length) {
		super(
				id, 
				MeterType.SectionAverageTravelSpeedMeter, 
				modesInitData, 
				new SATSMeasurer(startSegment, startPosition, endSegment, endPosition, length), 
				new DumbAverageDataCollectorFactory<Speed>());

	}

}
