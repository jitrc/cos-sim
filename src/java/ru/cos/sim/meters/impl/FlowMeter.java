/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.framework.ModesInitData;
import ru.cos.sim.meters.impl.data.Flow;
import ru.cos.sim.road.link.Segment;
/**
 *
 * @author zroslaw
 */
public class FlowMeter extends AbstractMeter<Flow> {

	public FlowMeter(
			int id,
			ModesInitData modesInitData, 
			Segment segment,
			float position,
			float measuringTime) {
		super(
				id, 
				MeterType.FlowMeter, 
				modesInitData, 
				new FlowMeasurer(segment, position, measuringTime), 
				new DumbAverageDataCollectorFactory<Flow>());

	}

}
