package ru.cos.sim.ras.duo.utils;

import java.util.Iterator;
import java.util.Map;

import ru.cos.sim.ras.duo.digraph.Digraph;
import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.digraph.Vertex;

public class Dump {

	public static Dump REPORTING = new Dump(true);

	public static Dump INFO = new Dump(false);
	public static Dump ERROR = new Dump(true);
	
	public static Dump PATHFINDING = new Dump(false);
	public static Dump WEIGHTING = new Dump(false);
	
	public Dump(boolean enabled) {
		this.enabled = enabled;
	}
	
	private boolean enabled = false;
	
	private void _println(Object message) {
		if (enabled)
			System.out.println(message);
	}
	
	private void _print(Object message) {
		if (enabled)
			System.out.print(message);
	}
	
	private void _println() {
		if (enabled)
			System.out.println();
	}
	
	public void print(String message) {
		_println(message);
	}

	public void print(Iterable<Edge> path) {
		print(path, true);
	}
	
	public void print(Iterable<Edge> path, boolean showEdges) {
		Iterator<Edge> edges = path.iterator();
		while (edges.hasNext()) {
			Edge e = edges.next();
			if (showEdges) {
				_print(e);
				if (edges.hasNext())
					_print(", ");
			} else {
				_print(e.getIncomingVertex() + " -> ");
				if (!edges.hasNext())
					_print(e.getOutgoingVertex());
			}
		}
		_println();
	}
	
	public void print(String message, Iterable<?> items) {
		_print(message);
		for (Object item : items)
			_print(" " + item);
		_println();
	}
	
	public void print(Digraph graph) {
		_println();
		_println("Edge Extensions:");
		for (Edge e : graph.getEdges()) {
			_println("* " + e + " : ");
			print(e.getExtensions());
		}
			
		_println();
		_println("Vertex Extensions:");
		for (Vertex v : graph.getVertexes()) {
			_println("* " + v);
			print(v.getExtensions());
		}
	}
	
	public void print(ExtensionCollection extensions) {
		for (Map.Entry<Object, Object> extension : extensions.getAll()) {
			_println(extension.getKey() + " - " + extension.getValue());
		}
	}
}
