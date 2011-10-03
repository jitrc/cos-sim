package ru.cos.sim.driver.route;

import ru.cos.sim.driver.RoadRoute;

public class RouteProviderFactory {

	public static final float UPDATE_PERIOD = 10; 
	
	public static RouteProvider createRouteProvider(RoadRoute staticRoute) {
		if (staticRoute != null) {
			return new StaticRouteProvider(staticRoute); 
		} else {
			return new UpdatingRouteProvider(UPDATE_PERIOD);
		}
	}
}
