/**
 * 
 */
package ru.cos.sim.road.objects;

import ru.cos.sim.road.exceptions.RoadNetworkException;

/**
 * Abstract road sign 
 * @author zroslaw
 */
public abstract class Sign extends AbstractRoadObject implements Cloneable{

	public enum SignType{
		SpeedLimitSign,
		NoSpeedLimitSign
	}
	
	@Override
	public RoadObjectType getRoadObjectType() {
		return RoadObjectType.RoadSign;
	}

	public abstract SignType getSignType();

	@Override
	public Sign clone(){
		try {
			return (Sign) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RoadNetworkException(e);
		}
	}

}
