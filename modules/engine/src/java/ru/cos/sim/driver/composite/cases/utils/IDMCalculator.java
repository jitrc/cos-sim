/**
 * 
 */
package ru.cos.sim.driver.composite.cases.utils;

import ru.cos.sim.driver.composite.Perception;
import ru.cos.sim.driver.data.IDMDriverParameters;
import ru.cos.sim.road.objects.BlockRoadObject;
import ru.cos.sim.road.objects.RoadObject;
import ru.cos.sim.vehicle.RegularVehicle;

/**
 * Calculation of acceleration according to IDM model.
 * {@link http://www.vwi.tu-dresden.de/~treiber/MicroApplet/IDM.html}
 * @author zroslaw
 */
public class IDMCalculator {
	private float maxAcceleration = 2f; // 4 m/s^2
	private float maxSpeed = 15; //
	private float minDistance = 2.f; // 2m
	private float desiredTimeHeadway = 2.f; // 2 seconds
	private float comfortDeceleration = 2.f; // 2 m/s^2
	private float abruptness = 4.f; // 4
	
	private float speed = 0;
	private float frontVehicleSpeed = 0;
	private float distance = 5.f; // 5m
	
	// precompiled values
	private float denominator = (float) (2*Math.sqrt(maxAcceleration*comfortDeceleration));

	public void init(IDMDriverParameters driver) {
		maxAcceleration = driver.getIdmMaxAcceleration();
		maxSpeed = driver.getIdmMaxSpeed();
		minDistance = driver.getIdmMinimalGap();
		desiredTimeHeadway = driver.getIdmDesiredHeadwayTime();
		comfortDeceleration = driver.getIdmMaxAcceleration();
		abruptness = driver.getIdmAbruptness();
	}

	public float calculate(RegularVehicle vehicle, Perception frontVehicle) {
		if (frontVehicle==null) frontVehicle = new Perception(Float.MAX_VALUE, new BlockRoadObject());
		setSpeed(vehicle.getSpeed());
		RoadObject roadObject = frontVehicle.getRoadObject();
		setFrontVehicleSpeed(roadObject.getSpeed());
		setDistance(frontVehicle.getDistance());
		return calculate();
	}
	
	
	/**
	 * Calculate acceleration
	 * @return
	 */
	public float calculate(){
		float result=0;
		
		float safetyDistance = calculateSafeDistance();
		
		result =  desiredTimeHeadway*
				 (1 - (float)Math.pow(speed/maxSpeed, abruptness)
				    - (float)Math.pow(safetyDistance/distance, 2));
		return result;
	}
	
	protected void init(){
		this.denominator = (float) (2*Math.sqrt(maxAcceleration*comfortDeceleration));
	}
	
	public float calculateSafeDistance(){
		float dynamicPart = speed*desiredTimeHeadway+
		   speed*(speed-frontVehicleSpeed)/denominator;
		return minDistance+dynamicPart;
	}

	/**
	 * @return the maxAcceleration
	 */
	public float getMaxAcceleration() {
		return maxAcceleration;
	}

	/**
	 * @return the maxSpeed
	 */
	public float getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * @return the minDistance
	 */
	public float getMinDistance() {
		return minDistance;
	}

	/**
	 * @return the desiredTimeHeadway
	 */
	public float getDesiredTimeHeadway() {
		return desiredTimeHeadway;
	}

	/**
	 * @return the comfortDeceleration
	 */
	public float getComfortDeceleration() {
		return comfortDeceleration;
	}

	/**
	 * @return the abruptness
	 */
	public float getAbruptness() {
		return abruptness;
	}

	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @return the frontVehicleSpeed
	 */
	public float getFrontVehicleSpeed() {
		return frontVehicleSpeed;
	}

	/**
	 * @return the distance
	 */
	public float getDistance() {
		return distance;
	}

	/**
	 * @return the denominator
	 */
	public float getDenominator() {
		return denominator;
	}

	/**
	 * @param maxAcceleration the maxAcceleration to set
	 */
	public void setMaxAcceleration(float maxAcceleration) {
		this.maxAcceleration = maxAcceleration;
		init();
	}

	/**
	 * @param maxSpeed the maxSpeed to set
	 */
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * @param minDistance the minDistance to set
	 */
	public void setMinDistance(float minDistance) {
		this.minDistance = minDistance;
	}

	/**
	 * @param desiredTimeHeadway the desiredTimeHeadway to set
	 */
	public void setDesiredTimeHeadway(float desiredTimeHeadway) {
		this.desiredTimeHeadway = desiredTimeHeadway;
	}

	/**
	 * @param comfortDeceleration the comfortDeceleration to set
	 */
	public void setComfortDeceleration(float comfortDeceleration) {
		this.comfortDeceleration = comfortDeceleration;
		init();
	}

	/**
	 * @param abruptness the abruptness to set
	 */
	public void setAbruptness(float abruptness) {
		this.abruptness = abruptness;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/**
	 * @param frontVehicleSpeed the frontVehicleSpeed to set
	 */
	public void setFrontVehicleSpeed(float frontVehicleSpeed) {
		this.frontVehicleSpeed = frontVehicleSpeed;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(float distance) {
		this.distance = distance;
	}

	/**
	 * @param denominator the denominator to set
	 */
	public void setDenominator(float denominator) {
		this.denominator = denominator;
	}
	
}
