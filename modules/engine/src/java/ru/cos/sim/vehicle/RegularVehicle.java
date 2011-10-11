/**
 * 
 */
package ru.cos.sim.vehicle;

import ru.cos.cs.lengthy.Fork;
import ru.cos.cs.lengthy.Join;
import ru.cos.cs.lengthy.Lengthy;
import ru.cos.sim.driver.Driver;
import ru.cos.sim.utils.Hand;
import ru.cos.sim.utils.Pair;

/**
 * 
 * @author zroslaw
 */
public class RegularVehicle extends AbstractVehicle {
	
	protected Driver driver;

	@Override
	public Lengthy chooseNextLengthy(Join join) {
		return driver.chooseNextLengthy(join);
	}

	@Override
	public Lengthy chooseNextLengthy(Fork fork) {
		return driver.chooseNextLengthy(fork);
	}

	@Override
	protected Pair<Float, Hand> drive(float dt) {
		Pair<Float, Hand> result = driver.drive(dt);
		return result;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	@Override
	public VehicleType getVehicleType() {
		return VehicleType.RegularVehicle;
	}

}
