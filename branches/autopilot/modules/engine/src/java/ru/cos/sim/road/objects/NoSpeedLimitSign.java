/**
 * 
 */
package ru.cos.sim.road.objects;

/**
 * No speed limit sign.
 * @author zroslaw
 */
public class NoSpeedLimitSign extends Sign {

	@Override
	public final SignType getSignType() {
		return SignType.NoSpeedLimitSign;
	}

}
