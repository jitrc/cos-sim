/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

/**
 * Interval of float values. 
 * @author zroslaw
 */
public class FloatInterval implements Cloneable{
	
	private float lowest = Float.NEGATIVE_INFINITY;
	
	private float higher = Float.POSITIVE_INFINITY;

	public FloatInterval() {
	}
	
	public FloatInterval(float lowest, float higher) {
		this.lowest = lowest;
		this.higher = higher;
	}

	public FloatInterval intersection(FloatInterval floatInterval){
		FloatInterval result = new FloatInterval();
		
		float leftPoint = lowest>floatInterval.getLowest()?lowest:floatInterval.getLowest();
		float rightPoint = higher<floatInterval.getHigher()?higher:floatInterval.getHigher();
		
		if (leftPoint>rightPoint){
			leftPoint = Float.NaN;
			rightPoint = Float.NaN;
		}
		
		result.setHigher(rightPoint);
		result.setLowest(leftPoint);
		
		return result;
	}
	
	public boolean isEmpty(){
		return new Float(lowest).equals(Float.NaN) || new Float(higher).equals(Float.NaN);
	}

	public float getLowest() {
		return lowest;
	}

	public void setLowest(float lowest) {
		this.lowest = lowest;
	}

	public float getHigher() {
		return higher;
	}

	public void setHigher(float higher) {
		this.higher = higher;
	}

	@Override
	protected FloatInterval clone() {
		try {
			return (FloatInterval) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Excpetion in FloatInterval..");
		}
	}

}
