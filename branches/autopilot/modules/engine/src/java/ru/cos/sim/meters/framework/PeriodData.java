/**
 * 
 */
package ru.cos.sim.meters.framework;

/**
 * 
 * @author zroslaw
 */
public class PeriodData<T extends MeasuredData<T>> implements MeasuredData<PeriodData<T>>{
	private TimePeriod timePeriod;
	private T measuredData;
	private float actualOn;
	
	public PeriodData(TimePeriod timePeriod) {
		super();
		this.timePeriod = timePeriod;
	}

	public float actualOn() {
		return actualOn;
	}

	public void setActualOn(float actualOn) {
		this.actualOn = actualOn;
	}

	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	public T getMeasuredData() {
		return measuredData;
	}
	
	public void setMeasuredData(T measuredData) {
		this.measuredData = measuredData;
	}

	public boolean isReady(){
		return actualOn>=timePeriod.getTimeTo();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PeriodData<T> clone(){
		PeriodData<T> result = null;
		try {
			result = (PeriodData<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		result.timePeriod = this.timePeriod.clone();
		if (this.measuredData!=null)
			result.measuredData = this.measuredData.clone();
		return result;
	}

	@Override
	public void normalize(PeriodData<T> norma) {
		throw new UnsupportedOperationException();
	}
	
}