/**
 * 
 */
package ru.cos.sim.road.init;

import java.util.Map;
import java.util.TreeMap;

import ru.cos.sim.road.RoadNetwork;
import ru.cos.sim.road.init.data.AbstractNodeData;
import ru.cos.sim.road.init.data.LinkData;
import ru.cos.sim.road.init.data.RoadNetworkData;
import ru.cos.sim.road.init.factories.LinksFactory;
import ru.cos.sim.road.init.factories.NodeFactory;
import ru.cos.sim.road.init.initializers.LinkInitializer;
import ru.cos.sim.road.init.initializers.NodeInitializer;
import ru.cos.sim.road.link.Link;
import ru.cos.sim.road.node.Node;

/**
 * 
 * @author zroslaw
 */
public class RoadNetworkBuilder {

	public static RoadNetwork build(RoadNetworkData roadNetworkData){
		RoadNetwork roadNetwork = new RoadNetwork();
		// 2 phase of building road network
		// 1st - create all road network objects
		// 2nd - init relationships between them
		
		// 1st phase
		Map<Integer,Link> links = new TreeMap<Integer,Link>();
		for (LinkData linkData:roadNetworkData.getLinks().values()){
			Link link = LinksFactory.createLink(linkData);
			links.put(link.getId(), link);
		}
		roadNetwork.setLinks(links);
		
		Map<Integer,Node> nodes = new TreeMap<Integer, Node>();
		for (AbstractNodeData abstractNodeData:roadNetworkData.getNodes().values()){
			Node node = NodeFactory.createNode(abstractNodeData);
			nodes.put(node.getId(), node);
		}
		roadNetwork.setNodes(nodes);
		
		// 2nd phase
		for (Link link:links.values()){
			LinkInitializer.initLink(link,roadNetwork,roadNetworkData);
		}
		
		for (Node node:nodes.values()){
			NodeInitializer.initNode(node, roadNetwork, roadNetworkData);
		}
		
		return roadNetwork;
	}
	
}
