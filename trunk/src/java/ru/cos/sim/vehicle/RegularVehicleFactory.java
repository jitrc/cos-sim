/**
 * 
 */
package ru.cos.sim.vehicle;

import ru.cos.sim.driver.Driver;
import ru.cos.sim.driver.factories.DriverFactory;
import ru.cos.sim.engine.RoadNetworkUniverse;
import ru.cos.sim.vehicle.init.data.RegularVehicleData;

/**
 * 
 * @author zroslaw
 */
public class RegularVehicleFactory{

	public static RegularVehicle createRegularVehicle(RegularVehicleData regularVehicleData, RoadNetworkUniverse universe) {
		RegularVehicle regularVehicle = new RegularVehicle();
		
		regularVehicle.setVehicleId(regularVehicleData.getVehicleId());
		regularVehicle.setLength(regularVehicleData.getLength());
		regularVehicle.setWidth(regularVehicleData.getWidth());
		regularVehicle.setVehicleClass(regularVehicleData.getVehicleClass());
		
		Driver driver = DriverFactory.createDriver(regularVehicleData.getDriverData(), universe);
		regularVehicle.setDriver(driver);
		driver.setVehicle(regularVehicle);
		
		return regularVehicle;
	}

}
