/**
 * 
 */
package ru.cos.sim.driver.xml;

import org.jdom.Element;

import ru.cos.sim.driver.Driver;
import ru.cos.sim.driver.Driver.DriverType;
import ru.cos.sim.driver.data.DriverData;
import ru.cos.sim.road.init.xml.exceptions.XMLReaderException;


/**
 * 
 * @author zroslaw
 */
public class DriverDataReader {

	public static final String COMPOSITE_DRIVER = "CompositeDriver";
	
	public static DriverData read(Element driverElement) {
		DriverData driverData;
		
		DriverType driverType = Driver.DriverType.valueOf(driverElement.getName());
		
		switch (driverType){
		case Composite:
			driverData = CompositeDriverReader.read(driverElement);
			break;
		default: throw new XMLReaderException("Unexpected driver type "+driverType);
		}
		
		return driverData;
	}

}
