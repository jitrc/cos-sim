/**
 * 
 */
package ru.cos.sim.road.init.data;

import ru.cos.sim.road.objects.Sign;

/**
 * Abstract data set for signs.
 * @author zroslaw
 */
public abstract class SignData {

	protected float position;
	
	public abstract Sign.SignType getSignType();

	public float getPosition() {
		return position;
	}

	public void setPosition(float position) {
		this.position = position;
	}
	
}
