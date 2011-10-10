/**
 * 
 */
package ru.cos.sim.road.objects;

/**
 * @author zroslaw
 *
 */
public class SpeedLimitSign extends Sign {

	private float speedLimit;
	
	@Override
	public SignType getSignType() {
		return SignType.SpeedLimitSign;
	}

	public float getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(float speedLimit) {
		this.speedLimit = speedLimit;
	}

}
