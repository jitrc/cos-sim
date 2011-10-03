/**
 * 
 */
package ru.cos.sim.road.init.initializers;

import java.util.HashMap;
import java.util.Map;

import ru.cos.sim.road.RoadNetwork;
import ru.cos.sim.road.init.data.DestinationNodeData;
import ru.cos.sim.road.init.data.RoadNetworkData;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.link.Link;
import ru.cos.sim.road.node.DestinationNode;
import ru.cos.sim.road.node.NodeFork;
import ru.cos.sim.road.node.TransitionRule;

/**
 * 
 * @author zroslaw
 */
public class DestinationNodeInitializer {

	public static void init(DestinationNode node, RoadNetwork roadNetwork, RoadNetworkData roadNetworkData) {
		DestinationNode destinationNode = (DestinationNode) node;
		DestinationNodeData destinationNodeData = (DestinationNodeData) roadNetworkData.getNodes().get(node.getId());
		Link incomingLink = roadNetwork.getLinks().get(destinationNodeData.getIncomingLinkId());
		destinationNode.setIncomingLink(incomingLink);
		
		// for each incoming lane create appropriate infinite transition rule and node fork
		Map<Integer, TransitionRule> tRules = new HashMap<Integer, TransitionRule>();
		for (Lane lane:incomingLink.getLastSegment().getLanes()){
			NodeFork nodeFork = new NodeFork();
			TransitionRule tr = new TransitionRule(lane.getIndex(), Float.POSITIVE_INFINITY);
			lane.setNext(nodeFork);
			nodeFork.forkTo(tr);
			nodeFork.setPrev(lane);
			nodeFork.setNode(destinationNode);
			tr.setPrev(nodeFork);
			tr.setNode(destinationNode);
			tRules.put(tr.getId(), tr);
		}
		destinationNode.setTRules(tRules);
	}

}
