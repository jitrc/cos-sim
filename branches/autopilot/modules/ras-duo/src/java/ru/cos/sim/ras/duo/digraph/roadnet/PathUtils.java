package ru.cos.sim.ras.duo.digraph.roadnet;

import java.util.Iterator;

import ru.cos.sim.ras.duo.WeightProvider;
import ru.cos.sim.ras.duo.digraph.Edge;

public class PathUtils {
	public PathUtils(Iterable<Edge> path) {
		this.edges = path;
	}
	
	private final Iterable<Edge> edges;
	
	public LinkEdge getCurrentLink() { 
		return getEdgeAt(0, LinkEdge.class);
	}
	
	public LinkEdge getNextLink() {
		return getEdgeAt(2, LinkEdge.class);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getEdgeAt(int position, Class<T> type) {
		Iterator<Edge> iter = edges.iterator();
		Edge edge = null; 
		while (position-- >= 0 && iter.hasNext())
			edge = iter.next();
		if (position == -1 && edge != null && type.isAssignableFrom(edge.getClass()))
			return (T)edge;
		else
			throw new RuntimeException("Internal RoadNetDigraph inconsistency");
	}
	
	public float getLength() {
		float length = 0;
		for (Edge edge : edges) 
			if (edge instanceof LinkEdge)
				length += ((LinkEdge)edge).getLinkData().getLength();
		return length;
	}
	
	public float getTravelTime(WeightProvider travelTimeWeightProvider) {
		return getTravelTime(travelTimeWeightProvider, false);
	}

	public float getTravelTime(WeightProvider travelTimeWeightProvider, boolean linksOnly) {
		float travelTime = 0;
		for (Edge edge : edges)
			if (!linksOnly || edge instanceof LinkEdge)
				travelTime += travelTimeWeightProvider.getWeight(edge, null);
		return travelTime;
	}
}
