package ru.cos.sim.services;

import ru.cos.sim.driver.RoadRoute;

public interface InitialRouteService {

	public RoadRoute findRoute(int sourceLinkId, int destinationNodeId);
	
}
