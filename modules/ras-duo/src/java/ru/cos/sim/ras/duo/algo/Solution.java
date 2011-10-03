package ru.cos.sim.ras.duo.algo;

import ru.cos.sim.ras.duo.MessageProcessor;
import ru.cos.sim.ras.duo.Parameters;
import ru.cos.sim.ras.duo.DataSources;
import ru.cos.sim.ras.duo.PathFinder;
import ru.cos.sim.ras.duo.RoadDigraph;
import ru.cos.sim.ras.duo.RouteRequest;
import ru.cos.sim.ras.duo.WeightProvider;
import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.msg.FindRouteRequest;
import ru.cos.sim.ras.duo.msg.FindRouteResponse;
import ru.cos.sim.ras.duo.msg.ReportPositionRequest;
import ru.cos.sim.ras.duo.msg.ReportPositionResponse;

public abstract class Solution implements MessageProcessor {
	
	public Solution(RoadDigraph graph, Parameters parameters) {
		this.graph = graph;
		this.parameters = parameters;
	}
	
	private RoadDigraph graph;
	public RoadDigraph getGraph() {
		return graph;
	}

	private Parameters parameters;
	public Parameters getCommonParameters() {
		return parameters;
	}
	
	protected abstract PathFinder getPathFinder();
	protected abstract WeightProvider getWeightProvider();
	
	protected abstract void collectData(DataSources sources);

	@Override
	public ReportPositionResponse reportPosition(float timestamp, ReportPositionRequest request) {
		collectData(new DataSources(timestamp, 
				request.getVehicleId(), 
				graph.getLinkEdge(request.getLinkId()),
				request.getLinkId(),
				request.getPosition(),
				request.getSpeed(),
				request.getVehicleData()));

		return new ReportPositionResponse();
	}
	
	@Override
	public FindRouteResponse findRoute(float timestamp, FindRouteRequest request) {
		return findRoute(timestamp, request, graph.getLinkEdge(request.getSourceLinkId()));
	}
	
	private FindRouteResponse findRoute(float timestamp, FindRouteRequest request, Edge startEdge) {
		RouteRequest routeRequest = new RouteRequest(request.getSourceLinkId(), request.getDestinationNodeId());
		Iterable<Edge> path = findPath(startEdge, routeRequest);
		return new FindRouteResponse(graph.convertToRoute(routeRequest.getStartLinkId(), path));
	}
	
	protected Iterable<Edge> findPath(Edge startEdge, RouteRequest routeRequest) {
		return getPathFinder().findPath(startEdge, graph.getNodeVertex(routeRequest.getEndNodeId()));
	}

	public static interface Factory {
		public Solution createSolution(RoadDigraph graph, Parameters parameters);
	}
}
