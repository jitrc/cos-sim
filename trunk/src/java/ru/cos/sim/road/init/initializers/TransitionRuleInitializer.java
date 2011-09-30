/**
 * 
 */
package ru.cos.sim.road.init.initializers;

import java.util.Map;

import ru.cos.sim.road.RoadNetwork;
import ru.cos.sim.road.init.data.AbstractNodeData;
import ru.cos.sim.road.init.data.RegularNodeData;
import ru.cos.sim.road.init.data.RoadNetworkData;
import ru.cos.sim.road.init.data.TransitionRuleData;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.link.Link;
import ru.cos.sim.road.node.NodeFork;
import ru.cos.sim.road.node.NodeJoin;
import ru.cos.sim.road.node.TransitionRule;

/**
 * 
 * @author zroslaw
 */
public class TransitionRuleInitializer {

	public static void initTR(TransitionRule tr, RoadNetwork roadNetwork, RoadNetworkData roadNetworkData) {
		Map<Integer,AbstractNodeData> nodesData = roadNetworkData.getNodes();
		Map<Integer,Link> links =roadNetwork.getLinks();
		RegularNodeData nodeData = (RegularNodeData) nodesData.get(tr.getNode().getId());
		TransitionRuleData trData = nodeData.getTransitionRules().get(tr.getId());
		
		// find source and destination lanes
		Link srcLink = links.get(trData.getSourceLinkId());
		Link dstLink = links.get(trData.getDestinationLinkId());
		Lane srcLane = srcLink.getLastSegment().getLanes()[trData.getSourceLaneIndex()];
		Lane dstLane = dstLink.getFirstSegment().getLanes()[trData.getDestinationLaneIndex()];
		
		// retrieve or create nodeFork for source lane and fork tr from it
		NodeFork nodeFork = (NodeFork) srcLane.getNext();
		if (nodeFork==null){
			nodeFork = new NodeFork();
			nodeFork.setNode(tr.getNode());
			srcLane.setNext(nodeFork);
			nodeFork.setPrev(srcLane);
		}
		nodeFork.forkTo(tr);
		tr.setPrev(nodeFork);
		
		// retrieve or create nodeJoin for destination lane and join tr to it
		NodeJoin nodeJoin = (NodeJoin) dstLane.getPrev();
		if (nodeJoin==null){
			nodeJoin = new NodeJoin();
			nodeJoin.setNode(tr.getNode());
			nodeJoin.setNext(dstLane);
			dstLane.setPrev(nodeJoin);
		}
		nodeJoin.join(tr);
		tr.setNext(nodeJoin);
		
	}

}
