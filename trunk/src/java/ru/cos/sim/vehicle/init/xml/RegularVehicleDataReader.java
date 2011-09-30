/**
 * 
 */
package ru.cos.sim.vehicle.init.xml;


import org.jdom.Element;

import ru.cos.sim.driver.data.DriverData;
import ru.cos.sim.driver.xml.CompositeDriverReader;
import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.vehicle.Vehicle.VehicleClass;
import ru.cos.sim.vehicle.init.data.RegularVehicleData;


/**
 * 
 * @author zroslaw
 */
public class RegularVehicleDataReader {

	public static final String CLASS = "class";
	public static final String LENGTH = "length";
	public static final String WIDTH = "width";
	public static final String COMPOSITE_DRIVER = "CompositeDriver";

	public static RegularVehicleData read(Element vehicleElement) {
		RegularVehicleData regularVehicleData = new RegularVehicleData();

		Element classElement = vehicleElement.getChild(CLASS,MDFReader.MDF_NAMESPACE);
		regularVehicleData.setVehicleClass(VehicleClass.valueOf(classElement.getText()));

		Element lengthElement = vehicleElement.getChild(LENGTH,MDFReader.MDF_NAMESPACE);
		regularVehicleData.setLength(Float.parseFloat(lengthElement.getText()));

		Element widthElement = vehicleElement.getChild(WIDTH,MDFReader.MDF_NAMESPACE);
		regularVehicleData.setWidth(Float.parseFloat(widthElement.getText()));
		
		Element driverElement = vehicleElement.getChild(COMPOSITE_DRIVER,MDFReader.MDF_NAMESPACE);
		DriverData driverData = CompositeDriverReader.read(driverElement);
		regularVehicleData.setDriverData(driverData);
		
		return regularVehicleData;
	}

}
