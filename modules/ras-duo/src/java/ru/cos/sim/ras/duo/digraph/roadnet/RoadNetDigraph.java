package ru.cos.sim.ras.duo.digraph.roadnet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ru.cos.sim.agents.TrafficAgentData;
import ru.cos.sim.agents.tlns.data.LogicalTrafficLightData;
import ru.cos.sim.agents.tlns.data.TrafficLightNetworkData;
import ru.cos.sim.driver.RoadRoute;
import ru.cos.sim.engine.TrafficModelDefinition;
import ru.cos.sim.ras.duo.RoadDigraph;
import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.digraph.Vertex;
import ru.cos.sim.ras.duo.utils.CachingMap;
import ru.cos.sim.ras.duo.utils.ConcatIterator;
import ru.cos.sim.road.init.data.AbstractNodeData;
import ru.cos.sim.road.init.data.DestinationNodeData;
import ru.cos.sim.road.init.data.LinkData;
import ru.cos.sim.road.init.data.RegularNodeData;
import ru.cos.sim.road.init.data.RoadNetworkData;
import ru.cos.sim.road.init.data.TransitionRuleData;
import ru.cos.sim.road.init.data.TurnTRGroupData;
import ru.cos.sim.utils.Pair;

public class RoadNetDigraph implements RoadDigraph {

	public RoadNetDigraph(TrafficModelDefinition modelDefinition) {
		this(modelDefinition.getRoadNetworkData(), getTrafficLightsData(modelDefinition));
	}
	
	private static Map<Integer, TrafficLightNetworkData> getTrafficLightsData(TrafficModelDefinition modelDefinition) {
		Map<Integer, TrafficLightNetworkData> trafficLights = new HashMap<Integer, TrafficLightNetworkData>();
		for (TrafficAgentData agentData : modelDefinition.getAgentsData())
			if (agentData instanceof TrafficLightNetworkData) {
				TrafficLightNetworkData lights = (TrafficLightNetworkData)agentData;
				trafficLights.put(lights.getRegularNodeId(), lights);
			}
		return trafficLights;
	}
	
	public RoadNetDigraph(RoadNetworkData network) {
		this(network, new TreeMap<Integer, TrafficLightNetworkData>());
	}
	
	public RoadNetDigraph(RoadNetworkData network, Map<Integer, TrafficLightNetworkData> trafficLights) {
		for (LinkData link : network.getLinks().values()) {
			NodePartVertex start = addVertex(link.getSourceNodeId());
			NodePartVertex end = addVertex(link.getDestinationNodeId());
			
			// Check if end vertex is Destination node
			if (network.getNodes().get(link.getDestinationNodeId()) instanceof DestinationNodeData)
				destinationVertexes.put(link.getDestinationNodeId(), end);
			
			linkEdges.put(link.getId(), new LinkEdge(link, start, end));
		}

		for (AbstractNodeData node : network.getNodes().values()) {
			if (node instanceof RegularNodeData) {
				RegularNodeData regularNode = (RegularNodeData)node;
				CachingMap<Integer, LinkEdge> linksInvolved = new CachingMap<Integer, LinkEdge>(linkEdges, new TreeMap<Integer, LinkEdge>());
				TrafficLightNetworkData nodeLightsNetwork = trafficLights != null ? trafficLights.get(node.getId()) : null;
				Set<LogicalTrafficLightData> nodeLights = nodeLightsNetwork != null ? nodeLightsNetwork.getTrafficLightsData() : new HashSet<LogicalTrafficLightData>(); 
				for (TransitionRuleData transition : regularNode.getTransitionRules().values()) {
					int turnGroupId = -1;
					for (TurnTRGroupData turnGroup : regularNode.getTurnTRGroups().values())
						if (turnGroup.getTransitionRuleIds().contains(transition.getId())) {
							turnGroupId = turnGroup.getId(); break;
						}
					
					LinkEdge srcLinkEdge = linksInvolved.get(transition.getSourceLinkId());
					LinkEdge dstLinkEdge = linksInvolved.get(transition.getDestinationLinkId());
					
					int transitionLightsId = -1;
					for (LogicalTrafficLightData lights : nodeLights) {
						if (lights.getPlacement().getTrurnTRGroups().contains(turnGroupId)) {
							transitionLightsId = lights.getId();
						}
						for (Pair<Integer, Float> lightsAssociation : lights.getPlacement().getTransitionRules())
							if (lightsAssociation.getFirst() == transition.getId() && lightsAssociation.getSecond() == 0) {
								transitionLightsId = lights.getId(); break;
							}
						if (transitionLightsId != -1) break;
					}
					
					transitionEdges.add(new TransitionEdge(node.getId(), transition, transitionLightsId != -1 ? nodeLightsNetwork : null, transitionLightsId, srcLinkEdge.getOutgoingVertex(), dstLinkEdge.getIncomingVertex()));
				}
			}
		}
	}

	private NodePartVertex addVertex(int nodeId) {
		NodePartVertex vertex = new NodePartVertex(nodeId);
		allVertexes.add(vertex);
		return vertex;
	}
	
	@Override
	public LinkEdge getLinkEdge(int linkId) {
		return linkEdges.get(linkId);
	}
	
	@Override
	public Vertex getNodeVertex(int nodeId) {
		return destinationVertexes.get(nodeId);
	}

	private Map<Integer, LinkEdge> linkEdges = new TreeMap<Integer, LinkEdge>();
	private List<TransitionEdge> transitionEdges = new LinkedList<TransitionEdge>();
	
	private List<NodePartVertex> allVertexes = new LinkedList<NodePartVertex>();
	private Map<Integer, NodePartVertex> destinationVertexes = new TreeMap<Integer, NodePartVertex>();
	
	@Override
	public Iterable<? extends Edge> getEdges() {
		return new ConcatIterator<Edge>()
			.add(linkEdges.values())
			.add(transitionEdges)
			.asIterable();
	}

	@Override
	public Iterable<? extends Vertex> getVertexes() {
		return allVertexes;
	}
	
	@Override
	public RoadRoute convertToRoute(int previousLinkId, Iterable<Edge> path) {
		List<Integer> linkIds = new LinkedList<Integer>();
		linkIds.add(previousLinkId);
		for (Edge e : path)
			if (e instanceof LinkEdge)
				linkIds.add(((LinkEdge)e).getLinkId());
		
		RoadRoute route = new RoadRoute();
		route.setLinks(linkIds);
		return route;
	}
}
