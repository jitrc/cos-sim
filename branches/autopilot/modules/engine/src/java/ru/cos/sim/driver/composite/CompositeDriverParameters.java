/**
 * 
 */
package ru.cos.sim.driver.composite;

import ru.cos.sim.driver.composite.cases.utils.MOBILDriverParameters;
import ru.cos.sim.driver.data.IDMDriverParameters;

/**
 * 
 * @author zroslaw
 */
public class CompositeDriverParameters implements IDMDriverParameters, MOBILDriverParameters {

	private float maxSpeed = 16;
	private float maxAcceleration = 2;
	private float comfortDeceleration = 4;
	private float minDistance = 2;
	private float desiredHeadwayTime = 2;
	private float abruptness = 4;
	private float politeness = 0.5f;
	private float accThreshold = 0.2f;
	private float intersectionGapTime = 3;
	
	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public float getMaxAcceleration() {
		return maxAcceleration;
	}

	public void setMaxAcceleration(float maxAcceleration) {
		this.maxAcceleration = maxAcceleration;
	}

	public float getComfortDeceleration() {
		return comfortDeceleration;
	}

	public void setComfortDeceleration(float comfortDeceleration) {
		this.comfortDeceleration = comfortDeceleration;
	}

	public float getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(float minDistance) {
		this.minDistance = minDistance;
	}

	public float getDesiredHeadwayTime() {
		return desiredHeadwayTime;
	}

	public void setDesiredHeadwayTime(float desiredHeadwayTime) {
		this.desiredHeadwayTime = desiredHeadwayTime;
	}

	public float getAbruptness() {
		return abruptness;
	}

	public void setAbruptness(float abruptness) {
		this.abruptness = abruptness;
	}

	public float getIdmMaxSpeed() {
		return maxSpeed;
	}
	
	public void setIdmMaxSpeed(float idmMaxSpeed) {
		this.maxSpeed = idmMaxSpeed;
	}
	
	public float getIdmMaxAcceleration() {
		return maxAcceleration;
	}
	
	public void setIdmMaxAcceleration(float maxAcceleration) {
		this.maxAcceleration = maxAcceleration;
	}
	
	public float getIdmMaxDeceleration() {
		return comfortDeceleration;
	}
	
	public void setIdmMaxDeceleration(float idmMaxDeceleration) {
		this.comfortDeceleration = idmMaxDeceleration;
	}
	
	public float getIdmMinimalGap() {
		return minDistance;
	}
	
	public void setIdmMinimalGap(float idmMinimalGap) {
		this.minDistance = idmMinimalGap;
	}
	
	public float getIdmAbruptness() {
		return abruptness;
	}
	
	public void setIdmAbruptness(float idmAbsruptness) {
		this.abruptness = idmAbsruptness;
	}

	public void setIdmDesiredHeadwayTime(float desiredHeadwayTime) {
		this.desiredHeadwayTime = desiredHeadwayTime;
	}

	public float getIdmDesiredHeadwayTime() {
		return desiredHeadwayTime;
	}

	public float getPoliteness() {
		return politeness;
	}

	public void setPoliteness(float politeness) {
		this.politeness = politeness;
	}

	public float getAccThreshold() {
		return accThreshold;
	}

	public void setAccThreshold(float accThreshold) {
		this.accThreshold = accThreshold;
	}

	public float getIntersectionGapTime() {
		return intersectionGapTime;
	}

	public void setIntersectionGapTime(float intersectionGapTime) {
		this.intersectionGapTime = intersectionGapTime;
	}
	
}
