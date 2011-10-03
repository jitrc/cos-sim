/**
 * 
 */
package ru.cos.sim.driver.data;

/**
 * 
 * @author zroslaw
 */
public interface IDMDriverParameters {

	public float getIdmMaxAcceleration();

	public float getIdmMaxSpeed();

	public float getIdmMinimalGap();

	public float getIdmDesiredHeadwayTime();

	public float getIdmAbruptness();

	public void setIdmMaxAcceleration(float idmAbruptness);

	public void setIdmMaxSpeed(float idmMaxSpeed);

	public void setIdmMinimalGap(float idmMinimalGap);

	public void setIdmDesiredHeadwayTime(float idmDesiredHeadwayTime);

	public void setIdmAbruptness(float idmAbruptness);
	
}
