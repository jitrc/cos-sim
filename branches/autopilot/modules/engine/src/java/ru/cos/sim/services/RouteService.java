package ru.cos.sim.services;

import ru.cos.sim.driver.RoadRoute;

public interface RouteService {

	public RoadRoute findRoute(int sourceLinkId, int destinationNodeId);
	
}
