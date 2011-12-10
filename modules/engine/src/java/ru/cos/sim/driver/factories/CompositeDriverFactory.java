/**
 * 
 */
package ru.cos.sim.driver.factories;

import java.util.List;

import ru.cos.cs.agents.framework.Universe;
import ru.cos.sim.driver.CompositeDriver;
import ru.cos.sim.driver.RoadRoute;
import ru.cos.sim.driver.composite.cases.CarFollowingCase;
import ru.cos.sim.driver.composite.cases.DesiredLaneChangingCase;
import ru.cos.sim.driver.composite.cases.ForthcomingNode;
import ru.cos.sim.driver.composite.cases.LaneAlignCase;
import ru.cos.sim.driver.composite.cases.MandatoryLaneChangingCase;
import ru.cos.sim.driver.composite.cases.RespectNodeCase;
import ru.cos.sim.driver.composite.cases.RespectQueueCase;
import ru.cos.sim.driver.composite.cases.RouterCase;
import ru.cos.sim.driver.composite.cases.SafetyCase;
import ru.cos.sim.driver.composite.cases.SpeedLimitCase;
import ru.cos.sim.driver.composite.cases.TrafficLightCase;
import ru.cos.sim.driver.composite.cases.WayJoinCase;
import ru.cos.sim.driver.composite.framework.CompositeDriverImpl;
import ru.cos.sim.driver.data.CompositeDriverData;
import ru.cos.sim.driver.route.RouteProvider;
import ru.cos.sim.driver.route.RouteProviderFactory;

/**
 * 
 * @author zroslaw
 */
public class CompositeDriverFactory {

	public static CompositeDriver createDriver(CompositeDriverData driverData, Universe universe) {
		CompositeDriverImpl compositeDriver = new CompositeDriverImpl();

		RouteProvider rp = RouteProviderFactory.createRouteProvider(getRoute(driverData));
		
		compositeDriver.addBehaviorCase(new RouterCase(compositeDriver, rp));
		compositeDriver.addBehaviorCase(new CarFollowingCase(compositeDriver));
		compositeDriver.addBehaviorCase(new LaneAlignCase(compositeDriver));
		compositeDriver.addBehaviorCase(new DesiredLaneChangingCase(compositeDriver));
		compositeDriver.addBehaviorCase(new SafetyCase(compositeDriver));
		compositeDriver.addBehaviorCase(new TrafficLightCase(compositeDriver));
		compositeDriver.addBehaviorCase(new ForthcomingNode(compositeDriver));
		compositeDriver.addBehaviorCase(new RespectQueueCase(compositeDriver));
		compositeDriver.addBehaviorCase(new SpeedLimitCase(compositeDriver));
		compositeDriver.addBehaviorCase(new MandatoryLaneChangingCase(compositeDriver));
		compositeDriver.addBehaviorCase(new RespectNodeCase(compositeDriver));
		compositeDriver.addBehaviorCase(new WayJoinCase(compositeDriver));
		
		compositeDriver.setDestinationNodeId(driverData.getDestinationNodeId());
		//compositeDriver.setRouteProvider(RouteProviderFactory.createRouteProvider(getRoute(driverData)));
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
