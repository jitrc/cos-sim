/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.framework.ModesInitData;
import ru.cos.sim.meters.impl.data.VehicleAppearanceHeadwayHistogram;
import ru.cos.sim.road.link.Segment;

/**
 *
 * @author zroslaw
 */
public class VAHMeter extends AbstractMeter<VehicleAppearanceHeadwayHistogram> {

	public VAHMeter(
			int id,
			ModesInitData modesInitData,
			Segment segment,
			float position,
			float timeBin) {
		super(
				id, 
				MeterType.VehiclesAppearanceHeadwayMeter, 
				modesInitData, 
				new VAHMeasurer(segment, position, timeBin), 
				new DumbAverageDataCollectorFactory<VehicleAppearanceHeadwayHistogram>());
	}

}
