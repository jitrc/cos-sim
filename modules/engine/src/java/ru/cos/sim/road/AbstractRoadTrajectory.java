/**
 * 
 */
package ru.cos.sim.road;

import ru.cos.cs.lengthy.impl.RegularLengthyImpl;

/**
 * Abstract implementation of {@linkplain RoadTrajectory} interface.
 * @author zroslaw
 */
public abstract class AbstractRoadTrajectory extends RegularLengthyImpl implements RoadTrajectory {

	protected float width = 0;
	
	public AbstractRoadTrajectory(float length) {
		super(length);
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float setWidth(float width) {
		return this.width = width;
	}

	@Override
	public boolean isLane() {
		return false;
	}

	@Override
	public boolean isTransitionRules() {
		return false;
	}

}
