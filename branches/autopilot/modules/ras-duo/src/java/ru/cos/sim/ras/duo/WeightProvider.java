package ru.cos.sim.ras.duo;

import ru.cos.sim.ras.duo.digraph.Edge;

public interface WeightProvider {

	public float getWeight(Edge edge, PathExtensions extensions);
	
}
