/**
 * 
 */
package ru.cos.sim.services;

import java.util.List;

import ru.cos.sim.driver.RoadRoute;
import ru.cos.sim.driver.composite.framework.RoadRouteImpl;
import ru.cos.sim.road.RoadNetwork;

/**
 * Dijkstra route service search for a shortest route in the road network
 * using Dijkstra's algorithm.
 * @author zroslaw
 */
public class DijkstraRouteService implements RouteService{
	
	private final DijkstraEngine dijkstraEngine;

	/**
	 * Init Dijkstra route service
	 * @param roadNetwork road network instance
	 */
	public DijkstraRouteService(RoadNetwork roadNetwork) {
		this.dijkstraEngine = new DijkstraEngine(roadNetwork);
	}

	@Override
	public RoadRoute findRoute(int sourceLinkId, int destinationNodeId) {
		dijkstraEngine.searchShortestPaths(sourceLinkId, destinationNodeId);
		List<Integer> routeLinkIds = dijkstraEngine.getShortestPath();
		RoadRoute route = new RoadRouteImpl(routeLinkIds);
		return route;
	}
}
