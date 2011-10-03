/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

import ru.cos.sim.utils.Hand;

/**
 * 
 * @author zroslaw
 */
public class ControlCommand {
	
	protected float acceleration;

	protected Hand turn;

	public float getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(float torque) {
		this.acceleration = torque;
	}

	public Hand getTurn() {
		return turn;
	}

	public void setTurn(Hand turn) {
		this.turn = turn;
	}

}
