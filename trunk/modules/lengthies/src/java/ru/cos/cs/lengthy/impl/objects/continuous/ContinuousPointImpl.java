/**
 * 
 */
package ru.cos.cs.lengthy.impl.objects.continuous;

import ru.cos.cs.lengthy.Lengthy;
import ru.cos.cs.lengthy.impl.objects.PointImpl;
import ru.cos.cs.lengthy.objects.continuous.Continuous;
import ru.cos.cs.lengthy.objects.continuous.ContinuousPoint;

/**
 * 
 * @author zroslaw
 */
public class ContinuousPointImpl extends PointImpl implements ContinuousPoint {

	protected Continuous continuous;
	protected float positionOnContinuous;
	
	/**
	 * @param continuous
	 * @param positionOnObservable
	 */
	public ContinuousPointImpl(Continuous continuous, float positionOnContinuous, Lengthy lengthy, float position) {
		this.lengthy = lengthy;
		this.position = position;
		this.continuous = continuous;
		this.positionOnContinuous = positionOnContinuous;
	}

	@Override
	public float getPositionOnContinuous() {
		return positionOnContinuous;
	}

	@Override
	public Continuous getContinuous() {
		return continuous;
	}

	@Override
	public void setPositionOnContinuous(float positionOnContinuous) {
		this.positionOnContinuous = positionOnContinuous;
	}

	@Override
	public void setContinuous(Continuous continuous) {
		this.continuous = continuous;
	}

	@Override
	public ContinuousPoint.Type getContinuousPointType() {
		// TODO
		return null;
	}	

}
