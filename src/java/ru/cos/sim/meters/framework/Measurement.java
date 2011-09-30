/**
 * 
 */
package ru.cos.sim.meters.framework;


/**
 * @author zroslaw
 *
 */
public class Measurement<T extends MeasuredData<T>> implements Cloneable{
	protected T measuredData;
	protected float measurementTime;
	
	public Measurement(T measuredValue, float measurementTime) {
		this.measuredData = measuredValue;
		this.measurementTime = measurementTime;
	}

	public T getMeasuredData() {
		return measuredData;
	}

	public float getMeasurementTime() {
		return measurementTime;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Measurement<T> clone() throws CloneNotSupportedException {
		Measurement<T> result = (Measurement<T>) super.clone();
		result.measuredData = this.measuredData.clone();
		return result;
	}	
	
}
