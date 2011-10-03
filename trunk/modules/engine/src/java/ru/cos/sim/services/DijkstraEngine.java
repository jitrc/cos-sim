/**
 * 
 */
package ru.cos.sim.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ru.cos.sim.road.RoadNetwork;
import ru.cos.sim.road.link.*;
import ru.cos.sim.road.node.*;
import ru.cos.sim.road.node.Node.NodeType;

/**
 * Search for the shortest path in the road network using Dijkstra's algorithm.
 * @author zroslaw
 */
public class DijkstraEngine {
	public class ObservedNodesList{
		/**
		 * Observed node but shortest distances to them are not known
		 * Keys are costs for each node';
		 */
		protected TreeMap<Float,List<DijkstraNode>> nodesByCosts;
		/**
		 * Keys are ids of nodes;
		 */
		protected TreeMap<Integer,DijkstraNode> nodesByIds;
		public ObservedNodesList() {
			nodesByCosts = new TreeMap<Float, List<DijkstraNode>>();
			nodesByIds = new TreeMap<Integer, DijkstraNode>();
		}
		protected void put(DijkstraNode dNode){
			List<DijkstraNode> costList = nodesByCosts.get(dNode.getCost());
			if (costList==null){
				costList = new ArrayList<DijkstraNode>();
				costList.add(dNode);
				nodesByCosts.put(dNode.getCost(), costList);
			}else{
				costList.add(dNode);
			}
			nodesByIds.put(dNode.getId(), dNode);
		}
		protected void remove(DijkstraNode dNode){
			List<DijkstraNode> costList = nodesByCosts.get(dNode.getCost());
			if (costList==null) return;
			costList.remove(dNode);
			if(costList.size()==0)
				nodesByCosts.remove(dNode.getCost());
			nodesByIds.remove(dNode.getId());
		}
		protected DijkstraNode getById(int id){
			return nodesByIds.get(id);
		}
		protected DijkstraNode getLowest(){
			Map.Entry<Float,List<DijkstraNode>> entry = nodesByCosts.firstEntry();
			if (entry==null) return null;
			List<DijkstraNode> costList = entry.getValue();
			return costList.get(0);
		}
		protected void clear() {
			nodesByIds.clear();
			nodesByCosts.clear();
		}
	}
	public class DijkstraNode{
		Link link;
		float cost;
		DijkstraNode prevNode;
		public DijkstraNode(Link link) {
			this.link = link;
		}
		public float getCost() {
			return cost;
		}
		public void setCost(float cost) {
			this.cost = cost;
		}
		public int getId() {
			return link.getId();
		}
		public DijkstraNode getPrevNode() {
			return prevNode;
		}
		public void setPrevNode(DijkstraNode prevNode) {
			this.prevNode = prevNode;
		}
		public Link getLink() {
			return link;
		}
	}	
	
	protected RoadNetwork roadNetwork;
	
	/**
	 * Nodes that are visited and shortest distances to them are known 
	 */
	protected TreeMap<Integer,DijkstraNode> visitedNodes = new TreeMap<Integer, DijkstraNode>();
	
	/**
	 * Observed node but shortest distances to them are not known
	 */
	protected ObservedNodesList observedNodes = new ObservedNodesList();
	/**
	 * Remember current link
	 */
	protected Link currentLink;
	protected int destNodeId;
	protected DijkstraNode destDijkstraNode;
	
	public DijkstraEngine(RoadNetwork roadNetwork) {
		this.roadNetwork = roadNetwork;
	}

	/**
	 * Поиск путей с текущего линка на нод с данным айди
	 * @param currentLink
	 */
	public void searchShortestPaths(int fromLinkId, int destNodeId){
		Link currentLink = roadNetwork.getLink(fromLinkId);
		this.visitedNodes.clear();
		this.observedNodes.clear();
		this.destDijkstraNode = null;
		this.destNodeId = destNodeId;
		this.currentLink = currentLink;
		DijkstraNode dNode = new DijkstraNode(currentLink);
		dNode.setCost(0);
		observedNodes.put(dNode);
		for (;dNode!=null;){
			vizitNode(dNode);
			dNode = observedNodes.getLowest();
		}
	}
	
	/**
	 * Lets visit a node!
	 * @param pair
	 */
	private void vizitNode(DijkstraNode visitedNode){
		Link link = visitedNode.getLink();
		Node destinationNode = link.getDestinationNode();
		if (destinationNode.getId()==destNodeId)
			destDijkstraNode = visitedNode;
		/*
		 * Iterate through all possible transitions to connected links
		 */
		if (destinationNode.getNodeType()==NodeType.RegularNode){
			RegularNode regularNode = (RegularNode)destinationNode;
			Set<Integer> accessibleLinkIds = regularNode.getAccessibleLinks(link.getId());
			for (Integer connectedLinkId:accessibleLinkIds){
				
				Link connectedLink = roadNetwork.getLink(connectedLinkId);
				// For each connected link check that it isn't in the list of already visited dijkstra nodes.
				if (visitedNodes.containsKey(connectedLink.getId())) continue;
				
				// Get Dijkstra node corresponded to that node from the set of observed nodes
				DijkstraNode obsdNode = observedNodes.getById(connectedLink.getId());
				if (obsdNode==null){
					// Hurrah! We have found new node! Welcome, newcomer!
					DijkstraNode dn = new DijkstraNode(connectedLink);
					dn.setCost(connectedLink.getLength()+visitedNode.getCost());
					dn.setPrevNode(visitedNode);
					observedNodes.put(dn);
				}else{
					// Node was already observed once, let's check if our new point of 
					// view have lowest cost
					float newCost = connectedLink.getLength()+visitedNode.getCost();
					if(newCost<obsdNode.getCost()){
						// Hurrah! New lower cost!
						// Now we should remove node from the observed node, change it costs and put it back
						observedNodes.remove(obsdNode);
						obsdNode.setCost(newCost);
						obsdNode.setPrevNode(visitedNode);
						observedNodes.put(obsdNode);
					}
				}
			}
		}
		visitedNodes.put(visitedNode.getId(), visitedNode);
		observedNodes.remove(visitedNode);
	}

	/**
	 * Generating route as sequence of links.
	 * First link is the link from which search was started.
	 * @param destId
	 * @return route as sequence of links
	 */
	public List<Integer> getShortestPath() {
		DijkstraNode dNode = destDijkstraNode;
		if (dNode==null) 
			return null;
		
		/**
		 * Generate path in the reverse order
		 */
		LinkedList<Integer> result = new LinkedList<Integer>();
		for(DijkstraNode dn=dNode;dn!=null;){
			result.addFirst(dn.getLink().getId());
			dn = dn.getPrevNode();
		}
		return result;
		
	}
}
