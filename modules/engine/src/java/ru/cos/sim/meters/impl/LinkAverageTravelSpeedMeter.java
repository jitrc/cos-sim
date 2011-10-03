/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.framework.ModesInitData;
import ru.cos.sim.meters.impl.data.Speed;
import ru.cos.sim.road.link.Link;

/**
 *
 * @author zroslaw
 */
public class LinkAverageTravelSpeedMeter extends AbstractMeter<Speed> {

	public LinkAverageTravelSpeedMeter(
			int id,
			ModesInitData modesInitData,
			Link link) {
		super(id, MeterType.LinkAverageTravelSpeedMeter, modesInitData, new LATSMeasurer(link), new DumbAverageDataCollectorFactory<Speed>());
	}

}
