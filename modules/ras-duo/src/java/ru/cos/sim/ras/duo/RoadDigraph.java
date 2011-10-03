package ru.cos.sim.ras.duo;

import ru.cos.sim.driver.RoadRoute;
import ru.cos.sim.ras.duo.digraph.Digraph;
import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.digraph.Vertex;

public interface RoadDigraph extends Digraph {

	public RoadRoute convertToRoute(int previousLinkId, Iterable<Edge> path);
	
	public Edge getLinkEdge(int linkId);
	public Vertex getNodeVertex(int nodeId);
}
