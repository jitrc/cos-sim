/**
 * 
 */
package ru.cos.sim.vehicle;

import ru.cos.sim.driver.CompositeDriver;
import ru.cos.sim.utils.Hand;
import ru.cos.sim.utils.Pair;

/**
 * 
 * @author zroslaw
 */
public class RegularVehicle extends AbstractVehicle {
	
	protected CompositeDriver driver;

	@Override
	protected Pair<Float, Hand> drive(float dt) {
		Pair<Float, Hand> result = driver.drive(dt);
		return result;
	}

	public CompositeDriver getDriver() {
		return driver;
	}

	public void setDriver(CompositeDriver driver) {
		this.driver = driver;
	}

	@Override
	public VehicleType getVehicleType() {
		return VehicleType.RegularVehicle;
	}
}
