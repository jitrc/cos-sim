/**
 * 
 */
package ru.cos.sim.road.init.data;

import ru.cos.sim.road.objects.Sign.SignType;

/**
 * 
 * @author zroslaw
 */
public class SpeedLimitSignData extends SignData {
	
	private float speedLimit;

	@Override
	public final SignType getSignType() {
		return SignType.SpeedLimitSign;
	}

	public void setSpeedLimit(float speedLimit) {
		this.speedLimit = speedLimit;
	}

	public float getSpeedLimit() {
		return speedLimit;
	}

}
