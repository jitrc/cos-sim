package ru.cos.sim.ras.duo.digraph;

public interface Digraph {

	public Iterable<? extends Vertex> getVertexes();
	public Iterable<? extends Edge> getEdges();
}
