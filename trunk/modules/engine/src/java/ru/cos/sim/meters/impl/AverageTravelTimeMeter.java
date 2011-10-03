/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.meters.*;
import ru.cos.sim.meters.framework.ModesInitData;
import ru.cos.sim.meters.impl.data.*;

/**
 *
 * @author zroslaw
 */
public class AverageTravelTimeMeter extends AbstractMeter<Time> {

	public AverageTravelTimeMeter(int id,
			ModesInitData modesInitData) {
		super(id, MeterType.AverageTravelTimeMeter, modesInitData, null, new DumbAverageDataCollectorFactory<Time>());
	}

}
