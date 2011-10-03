/**
 * 
 */
package ru.cos.sim.driver.composite.cases.utils;

import ru.cos.sim.driver.data.IDMDriverParameters;

/**
 * 
 * @author zroslaw
 */
public interface MOBILDriverParameters extends IDMDriverParameters{

	public float getPoliteness();
	
	public void setPoliteness(float politeness);

	public float getAccThreshold();
	
	public void setAccThreshold(float accThreshold);

}
