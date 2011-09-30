/**
 * 
 */
package ru.cos.sim.road.objects;

/**
 * Abstract road sign 
 * @author zroslaw
 */
public abstract class Sign extends AbstractRoadObject {

	public enum SignType{
		SpeedLimitSign,
		NoSpeedLimitSign
	}
	
	@Override
	public RoadObjectType getRoadObjectType() {
		return RoadObjectType.RoadSign;
	}

	public abstract SignType getSignType();

}
