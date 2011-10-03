/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.framework.ModesInitData;
import ru.cos.sim.meters.impl.data.Speed;

/**
 *
 * @author zroslaw
 */
public class NetworkAverageTravelSpeedMeter extends AbstractMeter<Speed> {

	public NetworkAverageTravelSpeedMeter(int id, ModesInitData modesInitData) {
		super(
				id, 
				MeterType.NetworkAverageTravelSpeedMeter, 
				modesInitData, 
				null, 
				new DumbAverageDataCollectorFactory<Speed>());
	}

}
