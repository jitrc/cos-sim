package ru.cos.sim.ras.duo.msg;

import ru.cos.sim.driver.RoadRoute;

public class FindRouteResponse {

	public FindRouteResponse(RoadRoute route) {
		this.route = route;
	}
	
	private final RoadRoute route;
	public RoadRoute getRoute() {
		return route;
	}
}
