/**
 * 
 */
package ru.cos.cs.lengthy.impl.objects;

import ru.cos.cs.lengthy.Lengthy;
import ru.cos.cs.lengthy.objects.Point;

/**
 * 
 * @author zroslaw
 */
public class PointImpl implements Point {

	protected Lengthy lengthy;
	protected float position;
	
	public PointImpl() {}
	
	public PointImpl(Lengthy lengthy, float position) {
		this.lengthy = lengthy;
		this.position = position;
	}

	@Override
	public Lengthy getLengthy() {
		return lengthy;
	}

	@Override
	public float getPosition() {
		return position;
	}

	@Override
	public void setLengthy(Lengthy lengthy) {
		this.lengthy=lengthy;
	}

	@Override
	public void setPosition(float position) {
		this.position=position;
	}

	@Override
	public PointType getPointType() {
		return PointType.Regular;
	}

}
