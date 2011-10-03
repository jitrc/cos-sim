package ru.cos.sim.ras.duo.digraph;

import ru.cos.sim.ras.duo.utils.Extendable;
import ru.cos.sim.ras.duo.utils.ExtensionCollection;

public abstract class Edge implements Extendable {

	public abstract Vertex getOutgoingVertex();
	public abstract Vertex getIncomingVertex();
	
	private ExtensionCollection extensions = new ExtensionCollection();
	@Override
	public ExtensionCollection getExtensions() {
		return extensions;
	}
}
