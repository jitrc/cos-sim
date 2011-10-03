package ru.cos.sim.ras.duo.digraph.roadnet;

import java.util.ArrayList;
import java.util.List;

import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.digraph.Vertex;

public class NodePartVertex extends Vertex {

	public NodePartVertex(int nodeId) {
		this.nodeId = nodeId;
	}
	
	private int nodeId;
	public int getNodeId() {
		return nodeId;
	}
	
	private List<Edge> incomingEdges = new ArrayList<Edge>();
	private List<Edge> outgoingEdges = new ArrayList<Edge>();
	
	protected void addIncomingEdge(Edge edge) {
		incomingEdges.add(edge);
	}
	
	protected void addOutgoingEdge(Edge edge) {
		outgoingEdges.add(edge);
	}
	
	@Override
	public Iterable<Edge> getIncomingEdges() {
		return incomingEdges;
	}

	@Override
	public Iterable<Edge> getOutgoingEdges() {
		return outgoingEdges;
	}

	@Override
	public String toString() {
		return Integer.toString(nodeId);
	}
}
