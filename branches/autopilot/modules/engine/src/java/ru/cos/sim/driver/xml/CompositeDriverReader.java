/**
 * 
 */
package ru.cos.sim.driver.xml;

import java.util.List;


import org.jdom.Element;

import ru.cos.sim.driver.composite.CompositeDriverParameters;
import ru.cos.sim.driver.data.CompositeDriverData;
import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.road.init.xml.RouteReader;


/**
 * 
 * @author zroslaw
 */
public class CompositeDriverReader {

	public static final String DESTINATION_NODE_ID = "destinationNodeId";
	public static final String ROUTE = "Route";
	public static final String PARAMETERS = "Parameters";

	public static CompositeDriverData read(Element driverElement) {
		CompositeDriverData compositeDriverData = new CompositeDriverData();

		Element dstNodeIdElement = driverElement.getChild(DESTINATION_NODE_ID, MDFReader.MDF_NAMESPACE);
		compositeDriverData.setDestinationNodeId(Integer.parseInt(dstNodeIdElement.getText()));
		
		Element routeElement = driverElement.getChild(ROUTE,MDFReader.MDF_NAMESPACE);
		if (routeElement!=null){
			List<Integer> route = RouteReader.read(routeElement);
			compositeDriverData.setRoute(route);
		}
		
		Element parametersElement = driverElement.getChild(PARAMETERS,MDFReader.MDF_NAMESPACE);
		CompositeDriverParameters parameters = CompositeDriverParametersReader.read(parametersElement);
		compositeDriverData.setParameters(parameters);
		
		return compositeDriverData;
	}

}
