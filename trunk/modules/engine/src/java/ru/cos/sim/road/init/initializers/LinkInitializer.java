package ru.cos.sim.road.init.initializers;

import java.util.Map;

import ru.cos.sim.road.RoadNetwork;
import ru.cos.sim.road.init.data.LinkData;
import ru.cos.sim.road.init.data.RoadNetworkData;
import ru.cos.sim.road.link.Link;
import ru.cos.sim.road.link.Segment;
import ru.cos.sim.road.node.Node;

public class LinkInitializer {

	/**
	 * Initialize all link relations
	 * @param link
	 * @param roadNetwork
	 * @param roadNetworkData
	 */
	public static void initLink(Link link, RoadNetwork roadNetwork, RoadNetworkData roadNetworkData) {
		Map<Integer,LinkData> linksData = roadNetworkData.getLinks();
		Map<Integer,Node> nodes = roadNetwork.getNodes();
		
		LinkData linkData = linksData.get(link.getId());

		Node sourceNode = nodes.get(linkData.getSourceNodeId());
		Node destinationNode = nodes.get(linkData.getDestinationNodeId());
		link.setDestinationNode(destinationNode);
		link.setSourceNode(sourceNode);
		
		for (Segment segment:link.getSegments().values()){
			SegmentInitializers.initSegment(segment,roadNetwork,roadNetworkData);
		}
		
	}

}
