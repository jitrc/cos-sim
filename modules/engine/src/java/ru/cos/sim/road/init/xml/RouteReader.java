/**
 * 
 */
package ru.cos.sim.road.init.xml;

import java.util.List;
import java.util.Vector;

import org.jdom.Element;

/**
 * Route reader.
 * @author zroslaw
 */
public class RouteReader {

	public static final String DELIMITER = ",";

	public static List<Integer> read(Element routeElement) {
		List<Integer> route = new Vector<Integer>();
		
		String[] linkIds = routeElement.getText().split(DELIMITER);
		for (String linkId:linkIds){
			route.add(Integer.parseInt(linkId));
		}
		
		return route;
	}

}
