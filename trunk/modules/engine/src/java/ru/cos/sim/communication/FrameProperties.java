package ru.cos.sim.communication;

import java.util.Map;
import java.util.Set;

/**
 * Properties of visualizer window frame.<br>
 * This properties is used by Traffic Simulation Engine to gather data for visualization.<br>
 * More concrete, frame properties it is information about road segments and nodes that now are visible in the visualizer window.
 * In the FrameProperties this information is presented as a map of list of integers Map<Integer,List<Integer>>.
 * Keys of the map are ids of links, while values are sets of ids of segments.
 * <p>
 * Traffic Simulation Engine will look up for any dynamic objects that placed on specified 
 * segments and nodes and transfer data to the client thread.
 * @author zroslaw
 */
public class FrameProperties {
	
	/**
	 * Map of link ids and links segments that are visible in the frame.
	 */
	private Map<Integer,Set<Integer>> linkSegments;
	
	/**
	 * List of ids of node that are visible in the frame.
	 */
	private Set<Integer> nodeIds;
	
	/**
	 * Create instance of frame properties.
	 * @param linkSegments {@link FrameProperties#linkSegments}
	 * @param nodeIds {@link FrameProperties#nodeIds}
	 */
	public FrameProperties(Map<Integer, Set<Integer>> linkSegments, Set<Integer> nodeIds) {
		this.linkSegments = linkSegments;
		this.nodeIds = nodeIds;
	}

	/**
	 * Get map of links and their visible segments.
	 * @return map of links and their visible segments
	 */
	public Map<Integer, Set<Integer>> getLinkSegments() {
		return linkSegments;
	}

	/**
	 * Get list of visible nodes.
	 * @return list of visible nodes
	 */
	public Set<Integer> getNodeIds() {
		return nodeIds;
	}
	
}
