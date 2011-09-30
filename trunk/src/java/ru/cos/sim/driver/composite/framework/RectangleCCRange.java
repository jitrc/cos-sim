/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

import static ru.cos.sim.utils.Hand.*;

import ru.cos.sim.driver.DriverException;
import ru.cos.sim.utils.Hand;

/**
 * 
 * @author zroslaw
 */
public class RectangleCCRange implements CCRange, Cloneable {
	
	/**
	 * Range of torque values.
	 * First number is lowest, second is largest in the range.
	 */
	protected FloatInterval accelerationRange = new FloatInterval();

	/**
	 * Range of lane change directions.
	 */
	protected HandRange turnRange = new HandRange();
	
	/**
	 * Priority of the range
	 */
	protected Priority priority = Priority.Lowest;
	
	@Override
	public CCRange intersect(CCRange ccRange) {
		if (ccRange==null) return this.clone();
		RectangleCCRange recRange = (RectangleCCRange)ccRange;

		int comparation = this.priority.compareTo(recRange.getPriority());
		if (comparation<0) 
			throw new DriverException("Priorities are not in order!");
		
		FloatInterval accelerationRange = 
			this.accelerationRange.intersection(recRange.getAccelerationRange());
		HandRange turnRange =
			this.turnRange.intersection(recRange.getTurnRange());

		if (accelerationRange.isEmpty() || turnRange.isEmpty()){
			// intersection of ranges is empty
			// therefore return this range because it has higher priority
			return (CCRange) this.clone();
		}
		
		RectangleCCRange result = new RectangleCCRange();
		result.setAccelerationRange(accelerationRange);
		result.setTurnRange(turnRange);
		result.setPriority(ccRange.getPriority());
			
		return result;
	}

	public FloatInterval getAccelerationRange() {
		return accelerationRange;
	}

	public void setAccelerationRange(FloatInterval torqueRange) {
		this.accelerationRange = torqueRange;
	}

	public HandRange getTurnRange() {
		return turnRange;
	}

	public void setTurnRange(HandRange turnRange) {
		this.turnRange = turnRange;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@Override
	public RectangleCCRange clone() {
		RectangleCCRange result;
		try {
			result = (RectangleCCRange) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Exception in RectangleCCRange..");
		}
		result.setAccelerationRange(accelerationRange.clone());
		result.setTurnRange(turnRange.clone());
		return result;
	}

	@Override
	public ControlCommand controlCommand() {
		ControlCommand result = new ControlCommand();
		
		// set highest acceleration
		result.setAcceleration(this.accelerationRange.getHigher());
		
		Hand turn;
		if (turnRange.contains(Left))
			turn = Left;
		else if (turnRange.contains(Right))
			turn = Right;
		else 
			turn = null;
		result.setTurn(turn);
		
		return result;
	}

}
