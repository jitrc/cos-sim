package ru.cos.sim.ras.duo.dijkstra;

import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.utils.SortedList;

public class DijkstraVertexInfo {

	public DijkstraVertexInfo() {
	}
	
	private boolean isVisited = false;
	public boolean isVisited() {
		return this.isVisited;
	}
	public void setIsVisited(boolean isVisited) {
		this.isVisited = isVisited; 
	}
	
	public static class Backtrack implements Comparable<Backtrack> {
		public Backtrack(Edge edge, float totalWeight) {
			this.edge = edge;
			this.totalWeight = totalWeight;
		}
		
		private final Edge edge;
		public Edge getEdge() {
			return edge;
		}
		
		private final float totalWeight;
		public float getTotalWeight() {
			return totalWeight;
		}
		
		@Override
		public int compareTo(Backtrack other) {
			return Float.compare(getTotalWeight(), other.getTotalWeight());
		}
		
		@Override
		public String toString() {
			return "(" + getEdge() + " " + getTotalWeight() + ")";
		}
	}
	
	private SortedList<Backtrack> backtrack = new SortedList<Backtrack>();
	public SortedList<Backtrack> getBacktrack() {
		return backtrack;
	}
	
	@Override
	public String toString() {
		String ret = "[" + (isVisited() ? "*" : " ") + "]";
		for (Backtrack bt : getBacktrack())
			ret += ", " + bt.toString();
		return ret;
	}
}
