/**
 * 
 */
package ru.cos.sim.road.init.data;

import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author zroslaw
 */
public class RoadNetworkData {
	
	protected Map<Integer,LinkData> links = new TreeMap<Integer, LinkData>();
	
	protected Map<Integer,AbstractNodeData> nodes = new TreeMap<Integer, AbstractNodeData>();

	public Map<Integer, LinkData> getLinks() {
		return links;
	}

	public void setLinks(Map<Integer, LinkData> links) {
		this.links = links;
	}

	public Map<Integer, AbstractNodeData> getNodes() {
		return nodes;
	}

	public void setNodes(Map<Integer, AbstractNodeData> nodes) {
		this.nodes = nodes;
	}
	
}
