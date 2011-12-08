/**
 * 
 */
package ru.cos.sim.meters.framework;

import java.util.List;
import java.util.Vector;

/**
 * 
 * @author zroslaw
 */
public class MeasurementHistory<T extends MeasuredData<T>> implements MeasuredData<MeasurementHistory<T>>{

	/**
	 * Sorted by time list of measurements.
	 */
	protected List<Measurement<T>> history = new Vector<Measurement<T>>();

	public void addMeasurement(Measurement<T> measurement){
		history.add(measurement);
	}
	
	public List<Measurement<T>> getHistory(){
		return history;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public MeasurementHistory<T> clone() {
		MeasurementHistory<T> result = null;
		List<Measurement<T>> history = new Vector<Measurement<T>>();
		try {
			result = (MeasurementHistory<T>) super.clone();
			for (Measurement<T> m:this.history){
					history.add((Measurement<T>) m.clone());
			}
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		result.history = history;
		return result;
	}

	@Override
	public void normalize(MeasurementHistory<T> norma) {
		throw new UnsupportedOperationException();
	}
	
}
