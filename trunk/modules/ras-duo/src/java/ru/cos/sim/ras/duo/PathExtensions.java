package ru.cos.sim.ras.duo;

import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.utils.Extendable;

public interface PathExtensions extends Extendable {
	
	public Edge getEdge();
	
	public PathExtensions getPreceeding();
	
}
