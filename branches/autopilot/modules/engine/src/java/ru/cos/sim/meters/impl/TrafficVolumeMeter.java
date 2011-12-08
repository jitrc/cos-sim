/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.framework.ModesInitData;
import ru.cos.sim.meters.impl.data.TrafficVolume;
import ru.cos.sim.road.link.Segment;

/**
 * OMFG
 * @author zroslaw
 */
public class TrafficVolumeMeter extends AbstractMeter<TrafficVolume> {

	public TrafficVolumeMeter(int id, ModesInitData modesInitData,	Segment segment, float position) {
		super(
				id,
				MeterType.TrafficVolumeMeter,
				modesInitData, 
				new TrafficVolumeMeasurer(segment, position), 
				new DumbAverageDataCollectorFactory<TrafficVolume>()
				);
	}
}
