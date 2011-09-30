/**
 * 
 */
package ru.cos.sim.meters.framework;

/**
 * 
 * @author zroslaw
 */
public class TimePeriod implements Cloneable {
	private float timeFrom;
	private float timeTo;
	
	public TimePeriod() {
		super();
	}
	
	public TimePeriod(float timeFrom, float timeTo) {
		super();
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
	}
	
	public float getTimeFrom() {
		return timeFrom;
	}
	public void setTimeFrom(float timeFrom) {
		this.timeFrom = timeFrom;
	}
	public float getTimeTo() {
		return timeTo;
	}
	public void setTimeTo(float timeTo) {
		this.timeTo = timeTo;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TimePeriod clone() {
		TimePeriod result = null;
		try {
			result = (TimePeriod) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
}
