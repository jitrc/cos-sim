package ru.cos.sim.ras.duo.algo.pathfinding;

import java.util.concurrent.ConcurrentLinkedQueue;

import ru.cos.sim.ras.duo.PathFinder;
import ru.cos.sim.ras.duo.WeightProvider;
import ru.cos.sim.ras.duo.digraph.Digraph;
import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.digraph.Vertex;
import ru.cos.sim.ras.duo.dijkstra.DijkstraPersistenceProvider;
import ru.cos.sim.ras.duo.dijkstra.DijkstraProcessor;
import ru.cos.sim.ras.duo.dijkstra.DijkstraProcessor.PathSelector;

public class PlainDijkstraPathFinder implements PathFinder {

	public PlainDijkstraPathFinder(Digraph graph, WeightProvider weightProvider, PathSelector.Factory pathSelector) {
		this.graph = graph;
		this.weightProvider = weightProvider;
		this.pathSelector = pathSelector;
	}

	private Digraph graph;
	private WeightProvider weightProvider;
	private PathSelector.Factory pathSelector;
	
	private ConcurrentLinkedQueue<DijkstraPersistenceProvider> layers = new ConcurrentLinkedQueue<DijkstraPersistenceProvider>();
	private DijkstraPersistenceProvider getLayer() {
		// Get free layer
		DijkstraPersistenceProvider layer = layers.poll();
		if (layer == null) {
			// Create new one if all layers are busy
			layer = new DijkstraPersistenceProvider(graph);
		}
		return layer;
	}
	private void freeLayer(DijkstraPersistenceProvider layer) {
		layers.add(layer);
	}
	
	@Override
	public Iterable<Edge> findPath(Edge start, Vertex end) {
		DijkstraPersistenceProvider layer = getLayer();
		DijkstraProcessor processor = new DijkstraProcessor(layer, weightProvider);
		processor.reset();
		processor.run(start, end);
		Iterable<Edge> path = DijkstraProcessor.adaptPath(processor.collectPath(start.getOutgoingVertex(), end, pathSelector));
		freeLayer(layer);
		return path;
	}
}