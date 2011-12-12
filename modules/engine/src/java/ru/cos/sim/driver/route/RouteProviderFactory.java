package ru.cos.sim.driver.route;

import ru.cos.sim.driver.RoadRoute;

public class RouteProviderFactory {

	public static final float UPDATE_PERIOD = 10; 
	
	public static RouteProvider createStaticProvider(int destLinkId) {
		return new StaticRouteProvider(destLinkId); 
	}
	public static RouteProvider createDynamicProvider(int destLinkId) {
		return new UpdatingRouteProvider(UPDATE_PERIOD, destLinkId);
	}
}
