/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

/**
 * Range of control command values
 * @author zroslaw
 */
public interface CCRange {
	
	/**
	 * Intersection of the current range of control commands 
	 * with another one.
	 * <p>
	 * Priority of current range must be greater than priority of another range,
	 * otherwise principles of control command evaluation may be violated.
	 * Method will throw an exception in this case.
	 * <p>
	 * Priority of the intersection will be equal to lowest priority.
	 * @param ccRange range of control command to intersect with
	 * @return new instance of CCRange that represents intersection of this and another ccRange.
	 * Priority of resulted ccRange will be equal to lowest one of priorities.
	 */
	public CCRange intersect(CCRange ccRange);
	
	/**
	 * Evaluate final control command on the base of the range.
	 * Method will choose one command among the range.
	 * @return
	 */
	public ControlCommand controlCommand();
	
	/**
	 * Get priority of the range.
	 * @return range priority
	 */
	public Priority getPriority();

}
