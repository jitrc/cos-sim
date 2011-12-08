/**
 * 
 */
package ru.cos.sim.road;

import java.util.Map;

import ru.cos.sim.road.link.*;
import ru.cos.sim.road.node.*;

/**
 * 
 * @author zroslaw
 */
public class RoadNetwork {

	protected Map<Integer, Link> links;
	
	protected Map<Integer, Node> nodes;

	public Map<Integer, Link> getLinks() {
		return links;
	}

	public void setLinks(Map<Integer, Link> links) {
		this.links = links;
	}

	public Map<Integer, Node> getNodes() {
		return nodes;
	}

	public void setNodes(Map<Integer, Node> nodes) {
		this.nodes = nodes;
	}

	public Node getNode(int nodeId) {
		return nodes.get(nodeId);
	}

	/**
	 * Get link of the road network by its id.
	 * @param linkId link id
	 * @return instance of the link with specified id or null
	 */
	public Link getLink(Integer linkId) {
		return links.get(linkId);
	}
	
}
