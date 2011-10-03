package ru.cos.sim.ras.duo.digraph;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class Direction {

	public abstract Vertex follow(Edge edge);
	public abstract Iterable<? extends Edge> follow(Vertex vertex);
	public abstract Direction getInverted();
	public abstract Iterable<Edge> normalize(LinkedList<Edge> path);
	
	public static final Direction Forward = new Direction() {
		@Override
		public Iterable<? extends Edge> follow(Vertex vertex) {
			return vertex.getOutgoingEdges();
		}
		
		@Override
		public Vertex follow(Edge edge) {
			return edge.getOutgoingVertex();
		}

		@Override
		public Direction getInverted() {
			return Direction.Backward;
		}
		
		@Override
		public Iterable<Edge> normalize(LinkedList<Edge> path) {
			return path;
		}
	};

	public static final Direction Backward = new Direction() {
		@Override
		public Iterable<? extends Edge> follow(Vertex vertex) {
			return vertex.getIncomingEdges();
		}
		
		@Override
		public Vertex follow(Edge edge) {
			return edge.getIncomingVertex();
		}

		@Override
		public Direction getInverted() {
			return Direction.Forward;
		}

		@Override
		public Iterable<Edge> normalize(final LinkedList<Edge> path) {
			return new Iterable<Edge>() {
				@Override
				public Iterator<Edge> iterator() {
					return path.descendingIterator();
				}
			};
		}
	};
}
