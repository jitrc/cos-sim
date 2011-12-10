package ru.cos.sim.driver;

import ru.cos.cs.lengthy.Router;
import ru.cos.sim.utils.Hand;
import ru.cos.sim.utils.Pair;
import ru.cos.sim.vehicle.RegularVehicle;
import ru.cos.sim.vehicle.Vehicle;

/**
 * 
 * @author zroslaw
 */
public interface Driver{
	
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

	/**
	 * Initialize driver cases
	 * @return
	 */
	public Pair<Float, Hand> drive(float dt);

	public Vehicle getVehicle();
	
	public void setVehicle(RegularVehicle vehicle);
	
	public DriverType getDriverType();
	public DriverRouter getDriverRouter();
}
