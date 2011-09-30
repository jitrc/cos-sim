/**
 * 
 */
package ru.cos.sim.road.init.initializers;

import ru.cos.sim.road.RoadNetwork;
import ru.cos.sim.road.init.data.OriginNodeData;
import ru.cos.sim.road.init.data.RoadNetworkData;
import ru.cos.sim.road.link.Link;
import ru.cos.sim.road.node.DestinationNode;
import ru.cos.sim.road.node.Node;
import ru.cos.sim.road.node.OriginNode;
import ru.cos.sim.road.node.RegularNode;
import ru.cos.sim.road.node.TransitionRule;

/**
 * 
 * @author zroslaw
 */
public class NodeInitializer {

	public static void initNode(Node node, RoadNetwork roadNetwork,	RoadNetworkData roadNetworkData) {
		
		switch (node.getNodeType()){
			case RegularNode:{
				RegularNode regularNode = (RegularNode) node;
				for (TransitionRule tr:regularNode.getTRules().values()){
					TransitionRuleInitializer.initTR(tr, roadNetwork, roadNetworkData);
				}
				regularNode.init();
				break;
			}
			case OriginNode:{
				OriginNode originNode = (OriginNode) node;
				OriginNodeData originNodeData = (OriginNodeData) roadNetworkData.getNodes().get(node.getId());
				Link outgoingLink = roadNetwork.getLinks().get(originNodeData.getOutgoingLinkId());
				originNode.setOutgoingLink(outgoingLink);
				break;
			}
			case DestinationNode:{
				DestinationNodeInitializer.init((DestinationNode) node, roadNetwork, roadNetworkData);
				break;
			}
		}
		
	}
	
}
