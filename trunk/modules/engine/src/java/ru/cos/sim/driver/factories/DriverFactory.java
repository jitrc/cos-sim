/**
 * 
 */
package ru.cos.sim.driver.factories;

import ru.cos.cs.agents.framework.Universe;
import ru.cos.sim.driver.Driver;
import ru.cos.sim.driver.DriverException;
import ru.cos.sim.driver.data.CompositeDriverData;
import ru.cos.sim.driver.data.DriverData;

/**
 * 
 * @author zroslaw
 */
public class DriverFactory {

	public static Driver createDriver(DriverData driverData, Universe universe) {
		Driver driver;
		
		switch (driverData.getDriverType()){
		case Composite:
//			driver = new IntelligentDriver((CompositeDriverData) driverData);
			driver = CompositeDriverFactory.createDriver((CompositeDriverData) driverData, universe);
			break;
		default: throw new DriverException("Unable to create driver instance, unexpected driver type"+driverData.getDriverType());
		}
		
		return driver;
	}

}
