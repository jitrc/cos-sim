/**
 * 
 */
package ru.cos.sim.driver.composite.cases;

import java.util.List;

import ru.cos.sim.driver.CompositeDriver;
import ru.cos.sim.driver.composite.Perception;
import ru.cos.sim.driver.composite.Percepts;
import ru.cos.sim.driver.composite.TrajectoryPercepts;
import ru.cos.sim.driver.composite.cases.utils.IDMCalculator;
import ru.cos.sim.driver.composite.framework.AbstractBehaviorCase;
import ru.cos.sim.driver.composite.framework.CCRange;
import ru.cos.sim.driver.composite.framework.Priority;
import ru.cos.sim.driver.composite.framework.RectangleCCRange;
import ru.cos.sim.driver.data.IDMDriverParameters;
import ru.cos.sim.road.objects.BlockRoadObject;
import ru.cos.sim.road.objects.Sign;
import ru.cos.sim.road.objects.Sign.SignType;
import ru.cos.sim.road.objects.SpeedLimitSign;

/**
 * 
 * @author zroslaw
 */
public class SpeedLimitCase extends AbstractBehaviorCase {
	
	/**
	 * Speed limit on current vehicle position
	 */
	private float currentSpeedLimit = Float.MAX_VALUE;
	
	/**
	 * Speed limit sign that was observed previously in front but was not yet reached
	 */
	private Perception prevFrontSpeedLimit = null;
	
	/**
	 * Sign that was observed previously but was not yet reached.<br>
	 * Sign states that there is no speed limit on the road ahead.
	 */
	private Perception prevNoSpeedLimitSign = null;
	
	private IDMCalculator idmCalculator;

	public SpeedLimitCase(CompositeDriver driver) {
		super(driver);
		this.idmCalculator = new IDMCalculator();
	}
	
	public void init(IDMDriverParameters parameters){
		this.idmCalculator.init(parameters);
	}

	@Override
	public CCRange behave(float dt) {
		// First, update currentSpeedLimit and frontSpeedLimit variables
		updatePerceptions();
		
		// Second, obey current speed limit
		RectangleCCRange ccRange = new RectangleCCRange();
		idmCalculator.setMaxSpeed(currentSpeedLimit);
		float acceleration = idmCalculator.calculate(driver.getVehicle(), new Perception(Float.MAX_VALUE, new BlockRoadObject()));
		
		// Adjust speed for further speed limit sign, if any.
		// We decrease speed continuously
		if (prevFrontSpeedLimit!=null){
			SpeedLimitSign speedLimitSign = (SpeedLimitSign)prevFrontSpeedLimit.getRoadObject();
			float frontSpeedLimit = speedLimitSign.getSpeedLimit();
			if (driver.getVehicle().getMovement().getSpeed()>frontSpeedLimit){
				idmCalculator.setDistance(prevFrontSpeedLimit.getActualDistance()+driver.getParameters().getMinDistance());
				idmCalculator.setFrontVehicleSpeed(0);
				idmCalculator.setMaxSpeed(currentSpeedLimit-frontSpeedLimit);
				idmCalculator.setSpeed(driver.getVehicle().getMovement().getSpeed()-frontSpeedLimit);
				float frontSignAcceleration = idmCalculator.calculate();
				if (frontSignAcceleration<acceleration) acceleration = frontSignAcceleration;
			}
		}
		
		ccRange.getAccelerationRange().setHigher(acceleration);
		
		ccRange.setPriority(Priority.SpeedLimit);
		return ccRange;
	}

	private void updatePerceptions() {
		Percepts percepts = driver.getPercepts();
		TrajectoryPercepts trajectoryPercepts = percepts.getCurrentPercepts();
		List<Perception> roadSigns = trajectoryPercepts.getRoadSigns();
		
		// find first speed limit sign
		Perception frontSpeedLimit = null;
		Perception noSpeedLimitSign = null;
		if (roadSigns!=null)
			for (Perception perception:roadSigns){
				Sign sign = (Sign)perception.getRoadObject();
				if (frontSpeedLimit==null && sign.getSignType()==SignType.SpeedLimitSign){
					frontSpeedLimit = perception;
				}else if (noSpeedLimitSign==null && sign.getSignType()==SignType.NoSpeedLimitSign){
					noSpeedLimitSign = perception;
				}
			}
		
		// Now we must update currentSpeedLimit value
		// Value must be updated in case we just pass speed limit sign or NoSpeedLimitSign
		
		// check if we just pass speed limit sign and if we do, then update current speed limit
		if (frontSpeedLimit!=null){
			// we observe front speed limit sign
			if (this.prevFrontSpeedLimit!=null){
				// on the previous step front speed limit sign was observed too
				// let's check that it is the same as observed on this step
				if (frontSpeedLimit.getDistance()-this.prevFrontSpeedLimit.getDistance()>5.f)
					// it is another sign, update current speed limit
					this.currentSpeedLimit = ((SpeedLimitSign)this.prevFrontSpeedLimit.getRoadObject()).getSpeedLimit();
			}
		}else{
			// we do not observe front speed limit sign
			if (this.prevFrontSpeedLimit!=null){
				// on the previous step there was observed speed limit sign
				// so, we must remember previous speed as current max speed
				this.currentSpeedLimit = ((SpeedLimitSign)this.prevFrontSpeedLimit.getRoadObject()).getSpeedLimit();
			}
		}
		
		// check if we just pass NoSpeedLimitSign, and if we do, then restore max speed limit value
		if (noSpeedLimitSign==null && this.prevNoSpeedLimitSign!=null){
			this.currentSpeedLimit = driver.getParameters().getMaxSpeed();
		}
		
		this.prevNoSpeedLimitSign = noSpeedLimitSign;
		this.prevFrontSpeedLimit = frontSpeedLimit;
		
	}

}
