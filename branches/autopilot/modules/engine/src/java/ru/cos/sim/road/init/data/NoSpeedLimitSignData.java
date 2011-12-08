/**
 * 
 */
package ru.cos.sim.road.init.data;

import ru.cos.sim.road.objects.Sign.SignType;

/**
 * 
 * @author zroslaw
 */
public class NoSpeedLimitSignData extends SignData {

	@Override
	public final SignType getSignType() {
		return SignType.NoSpeedLimitSign;
	}

}
