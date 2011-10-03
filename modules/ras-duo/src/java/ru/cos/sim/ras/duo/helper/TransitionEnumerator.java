package ru.cos.sim.ras.duo.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.digraph.roadnet.LinkEdge;
import ru.cos.sim.ras.duo.digraph.roadnet.TransitionEdge;
import ru.cos.sim.utils.AdaptIterator;
import ru.cos.sim.utils.Adapter;

public class TransitionEnumerator {

	public static List<TransitionEdge> getOutgoingTransitions(LinkEdge link) {
		List<TransitionEdge> edges = new ArrayList<TransitionEdge>();
	
		for (Edge e : link.getOutgoingVertex().getOutgoingEdges())
			if (e instanceof TransitionEdge) 
				edges.add((TransitionEdge)e);
		
		return edges;
	}
	
	public static Iterable<Integer> getTrafficLightsIds(Iterable<TransitionEdge> transitions) {
		return new AdaptIterator<TransitionEdge, Integer>(transitions, new Adapter<TransitionEdge, Integer>() {
			@Override
			public Integer adapt(TransitionEdge transition) {
				return transition.getLightsId();
			}
		}).asIterable();
	}
	
	public static class TransitionDestination {
		public TransitionDestination(TransitionEdge transition, int linkId, int laneId) {
			this.transition = transition;
			this.linkId = linkId;
			this.laneId = laneId;
		}
		
		private TransitionEdge transition;
		private int linkId;
		private int laneId;
		
		public LinkEdge getLink() {
			for (Edge edge : transition.getOutgoingVertex().getOutgoingEdges())
				if (edge instanceof LinkEdge && ((LinkEdge)edge).getLinkId() == linkId)
					return (LinkEdge)edge;
			return null;
		}

		public int getLaneNumber() {
			return laneId;
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof TransitionDestination)) return false;
			
			TransitionDestination other = (TransitionDestination)o;

			return this.linkId == other.linkId && this.laneId == other.laneId;
		}
		
		@Override
		public int hashCode() {
			return linkId;
		}
	}
	
	public static Set<TransitionDestination> getUniqueDestinations(Iterable<TransitionEdge> transitions) {
		Set<TransitionDestination> destinations = new HashSet<TransitionDestination>();
		
		for (TransitionEdge transition : transitions) {
			TransitionDestination destination = new TransitionDestination(transition, transition.getTransitionData().getDestinationLinkId(), transition.getTransitionData().getDestinationLaneIndex());
			if (!destinations.contains(destination))
				destinations.add(destination);
		}
		
		return destinations;
	}
}
