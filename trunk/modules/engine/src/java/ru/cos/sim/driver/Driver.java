package ru.cos.sim.driver;

import ru.cos.cs.lengthy.Router;
import ru.cos.sim.utils.Hand;
import ru.cos.sim.utils.Pair;
import ru.cos.sim.vehicle.RegularVehicle;

/**
 * 
 * @author zroslaw
 */
public interface Driver extends Router {
	
	public enum DriverType{
		/**
		 * Dumb driver.
		 */
		Dumb,
		/**
		 * Composite driver.
		 */
		Composite, 
		/**
		 * Intelligent driver.
		 */
		IntelligentDriver
	}

	public Pair<Float, Hand> drive(float dt);

	public RegularVehicle getVehicle();
	
	public void setVehicle(RegularVehicle vehicle);
	
	public DriverType getDriverType();
}
