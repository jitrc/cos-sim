/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

import static ru.cos.sim.utils.Hand.Left;
import static ru.cos.sim.utils.Hand.Right;
import ru.cos.sim.utils.Hand;

/**
 * 
 * @author zroslaw
 */
public class RectangleCCRange extends CCRange {
	
	/**
	 * Range of torque values.
	 */
	protected FloatInterval accelerationRange = new FloatInterval();

	/**
	 * Range of lane change directions.
	 */
	protected HandRange turnRange = new HandRange();
	
	@Override
	public CCRange intersect(CCRange ccRange) {
		if (ccRange==null) return this.clone();
		RectangleCCRange recRange = (RectangleCCRange)ccRange;
		
		FloatInterval accelerationRange = 
			this.accelerationRange.intersection(recRange.getAccelerationRange());
		HandRange turnRange =
			this.turnRange.intersection(recRange.getTurnRange());
		
		RectangleCCRange result = new RectangleCCRange();
		result.setAccelerationRange(accelerationRange);
		result.setTurnRange(turnRange);
			
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

	@Override
	public RectangleCCRange clone() {
		RectangleCCRange result = (RectangleCCRange) super.clone();
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

	@Override
	public boolean isEmpty() {
		return accelerationRange.isEmpty() || turnRange.isEmpty();
	}

}
