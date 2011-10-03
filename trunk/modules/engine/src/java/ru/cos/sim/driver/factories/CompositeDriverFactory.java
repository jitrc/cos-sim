/**
 * 
 */
package ru.cos.sim.driver.factories;

import java.util.List;

import ru.cos.cs.agents.framework.Universe;
import ru.cos.sim.driver.RoadRoute;
import ru.cos.sim.driver.composite.CompositeDriver;
import ru.cos.sim.driver.data.CompositeDriverData;
import ru.cos.sim.driver.route.RouteProviderFactory;

/**
 * 
 * @author zroslaw
 */
public class CompositeDriverFactory {

	public static CompositeDriver createDriver(CompositeDriverData driverData, Universe universe) {
		CompositeDriver compositeDriver = new CompositeDriver();

		compositeDriver.setDestinationNodeId(driverData.getDestinationNodeId());
		compositeDriver.setRouteProvider(RouteProviderFactory.createRouteProvider(getRoute(driverData)));
		compositeDriver.setParameters(driverData.getParameters());

		return compositeDriver;
	}

	private static RoadRoute getRoute(CompositeDriverData driverData) {
		List<Integer> links = driverData.getRoute();
		if (links!=null){
			RoadRoute route = new RoadRoute();
			route.setLinks(links);
			return route;
		} else 
			return null;
	}
}
