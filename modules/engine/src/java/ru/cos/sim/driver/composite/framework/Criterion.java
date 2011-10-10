/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

/**
 * Criterion of control command range.
 * @author zroslaw
 */
public interface Criterion extends Comparable<Criterion>, Cloneable{

	public enum CriterionType{
		Priority,
		Acceleration
	}
	
	public CriterionType getCriterionType();

	public Criterion clone();
	
}
