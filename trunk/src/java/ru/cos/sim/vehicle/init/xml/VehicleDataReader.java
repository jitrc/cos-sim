/**
 * 
 */
package ru.cos.sim.vehicle.init.xml;


import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.road.init.data.LocationData;
import ru.cos.sim.road.init.xml.LocationDataReader;
import ru.cos.sim.road.init.xml.exceptions.XMLReaderException;
import ru.cos.sim.vehicle.Vehicle.VehicleType;
import ru.cos.sim.vehicle.init.data.VehicleData;


/**
 * 
 * @author zroslaw
 */
public class VehicleDataReader {

	public static final String VEHICLE_ID = "vehicleId";
	public static final String LINK_LOCATION = "LinkLocation";
	public static final String NODE_LOCATION = "NodeLocation";
	public static final String SPEED = "speed";
	public static final String TYPE = "type";
	
	public static final Namespace NS = MDFReader.MDF_NAMESPACE;
	
	public static VehicleData read(Element vehicleElement) {
		VehicleData vehicleData;

		VehicleType type = VehicleType.valueOf(vehicleElement.getName());

		switch (type){
			case RegularVehicle:
				vehicleData = RegularVehicleDataReader.read(vehicleElement);
				break;
			default: throw new XMLReaderException("Unexpected vehicle type "+type);
		}
		
		Element idElement = vehicleElement.getChild(VEHICLE_ID, NS);
		vehicleData.setVehicleId(idElement.getText());
		
		LocationData locationData;
		Element linkLocationElement = vehicleElement.getChild(LINK_LOCATION, NS);
		Element nodeLocationElement = vehicleElement.getChild(LINK_LOCATION, NS);
		if (linkLocationElement!=null)
			locationData = LocationDataReader.read(linkLocationElement);
		else
			locationData = LocationDataReader.read(nodeLocationElement);
		vehicleData.setLocationData(locationData);
		
		Element speedElement = vehicleElement.getChild(SPEED, NS);
		vehicleData.setSpeed(Float.parseFloat(speedElement.getText()));
		
		return vehicleData;
	}

}
