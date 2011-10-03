package ru.cos.sim.ras.duo.dijkstra;

import java.util.LinkedList;

import ru.cos.sim.ras.duo.PathExtensions;
import ru.cos.sim.ras.duo.WeightProvider;
import ru.cos.sim.ras.duo.digraph.Direction;
import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.digraph.Vertex;
import ru.cos.sim.ras.duo.dijkstra.DijkstraVertexInfo.Backtrack;
import ru.cos.sim.ras.duo.utils.ExtensionCollection;
import ru.cos.sim.ras.duo.utils.KeyValuePair;
import ru.cos.sim.ras.duo.utils.SortedList;
import ru.cos.sim.utils.AdaptIterator;
import ru.cos.sim.utils.Adapter;

public class DijkstraProcessor {
	public DijkstraProcessor(DijkstraPersistenceProvider persistenceProvider, WeightProvider weightProvider) {
		this(persistenceProvider, weightProvider, Direction.Forward);
	}
	
	public DijkstraProcessor(DijkstraPersistenceProvider persistenceProvider, WeightProvider weightProvider, Direction direction) {
		this.persistenceProvider = persistenceProvider;
		this.weightProvider = weightProvider;
		this.direction = direction;
	}
	
	private DijkstraPersistenceProvider persistenceProvider;
	private WeightProvider weightProvider;
	private Direction direction;
	
	public void run(Edge start) {
		run(start, (Vertex)null);
	}
	
	public void run(Edge start, Vertex end) {
		SortedList<KeyValuePair<Float, PathEstablishingFollower>> queue = new SortedList<KeyValuePair<Float,PathEstablishingFollower>>();
		run(new PathEstablishingFollower(start, direction.follow(start)), queue);
		processQueue(end, queue);
	}
	
	private void run(PathEstablishingFollower previousPath, SortedList<KeyValuePair<Float, PathEstablishingFollower>> queue) {
		Vertex start = previousPath.getLastVertex();
		DijkstraVertexInfo startInfo = persistenceProvider.getInfo(start);
		Backtrack minimumBackpath = startInfo.getBacktrack().peekMinimum();  
		float totalWeight = minimumBackpath != null ? minimumBackpath.getTotalWeight() : 0;
		for (Edge edge : direction.follow(start)) {
			Vertex nextVertex = direction.follow(edge);
			DijkstraVertexInfo nextVertexInfo = persistenceProvider.getInfo(nextVertex);
			PathEstablishingFollower currentPath = new PathEstablishingFollower(previousPath, edge, nextVertex);
			float newTotalWeight = totalWeight + weightProvider.getWeight(edge, currentPath);
			// Add backtrack even to visited nodes
			nextVertexInfo.getBacktrack().add(new Backtrack(edge, newTotalWeight));
			if (!nextVertexInfo.isVisited()) {
				queue.add(new KeyValuePair<Float, PathEstablishingFollower>(newTotalWeight, currentPath));
			}
		}
		startInfo.setIsVisited(true);
	}
	
	// Now queue is processed until ALL the vertexes are visited, not until the destination is visited
	private void processQueue(Vertex end, SortedList<KeyValuePair<Float, PathEstablishingFollower>> queue) {
		KeyValuePair<Float, PathEstablishingFollower> nextVertex;
		while ((nextVertex = queue.popMinimum()) != null) {
			DijkstraVertexInfo nextVertexInfo = persistenceProvider.getInfo(nextVertex.getValue().getLastVertex()); 
			if (!nextVertexInfo.isVisited())
				run(nextVertex.getValue(), queue);
		} 
	}

	public LinkedList<Backtrack> collectPath(Vertex start, Vertex end, PathSelector.Factory pathSelectorFactory) {
		return collectPath(start, end, pathSelectorFactory.createPathSelector(this, start, end));
	}
	
	private LinkedList<Backtrack> collectPath(Vertex start, Vertex end, PathSelector pathSelector) {
		LinkedList<Backtrack> path = new LinkedList<Backtrack>();
		Vertex currentVertex = end;
		Backtrack pathItem;
		while ((pathItem = pathSelector.select(persistenceProvider.getInfo(currentVertex).getBacktrack())) != null && currentVertex != start) {
			path.addFirst(pathItem);
			currentVertex = direction.getInverted().follow(pathItem.getEdge());
		}
		return path;
	}
	
	public static Iterable<Edge> adaptPath(Iterable<Backtrack> path) {
		return new AdaptIterator<Backtrack, Edge>(path, new Adapter<Backtrack, Edge>() {
			@Override
			public Edge adapt(Backtrack source) {
				return source.getEdge();
			}
		}).asIterable();
	}
	
	public void reset() {
		for (DijkstraVertexInfo info : persistenceProvider.getAllVertexInfo()) {
			info.setIsVisited(false);
			info.getBacktrack().clear();
		}
	}
	
	public static interface PathSelector {
		public Backtrack select(SortedList<Backtrack> variants);
		
		public static interface Factory {
			public PathSelector createPathSelector(DijkstraProcessor processor, Vertex start, Vertex end);
		}
	}
	
	public static class PathEstablishingFollower implements PathExtensions {
		private PathEstablishingFollower(Edge lastEdge, Vertex lastVertex) {
			this.lastEdge = lastEdge;
			this.lastVertex = lastVertex;
		}
		
		private PathEstablishingFollower(PathEstablishingFollower previous, Edge lastEdge, Vertex lastVertex) {
			this(lastEdge, lastVertex);
			this.previous = previous;
		}
		
		private PathEstablishingFollower previous;
		private Edge lastEdge;
		private Vertex lastVertex;
		
		protected Vertex getLastVertex() {
			return lastVertex;
		}
		
		@Override
		public Edge getEdge() {
			return lastEdge;
		}
		
		@Override
		public PathEstablishingFollower getPreceeding() {
			return previous;
		}

		private ExtensionCollection extensions = new ExtensionCollection();
		
		@Override
		public ExtensionCollection getExtensions() {
			return extensions;
		}
	}
}
