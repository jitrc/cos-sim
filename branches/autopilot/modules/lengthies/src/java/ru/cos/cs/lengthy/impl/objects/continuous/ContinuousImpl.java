/**
 * 
 */
package ru.cos.cs.lengthy.impl.objects.continuous;

import java.util.List;
import java.util.Vector;

import ru.cos.cs.lengthy.Lengthy;
import ru.cos.cs.lengthy.objects.continuous.Continuous;
import ru.cos.cs.lengthy.objects.continuous.ContinuousPoint;

/**
 * 
 * @author zroslaw
 */
public class ContinuousImpl implements Continuous{

	protected float length = Float.NaN;
	protected ContinuousPoint backPoint;
	protected ContinuousPoint frontPoint;
	protected List<Lengthy> occupiedLengthies = new Vector<Lengthy>();
	
	public ContinuousImpl(float length) {
		this.length = length;
		backPoint = new ContinuousPointImpl(this,0, null, Float.NaN);
		frontPoint = new ContinuousPointImpl(this, length, null, Float.NaN);
	}

	@Override
	public float length() {
		return length;
	}

	@Override
	public ContinuousPoint getBackPoint() {
		return backPoint;
	}

	@Override
	public ContinuousPoint getFrontPoint() {
		return frontPoint;
	}

	@Override
	public List<Lengthy> getOccupiedLengthies() {
		return occupiedLengthies;
	}

	public void setLength(float length) {
		this.length=length;
	}

}
