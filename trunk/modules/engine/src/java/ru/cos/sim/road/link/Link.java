/**
 * 
 */
package ru.cos.sim.road.link;

import java.util.HashMap;
import java.util.Map;

import ru.cos.sim.road.exceptions.RoadNetworkException;
import ru.cos.sim.road.node.Node;

/**
 * Link of the road network.
 * @author zroslaw
 */
public class Link{

	/**
	 * Link agentId
	 */
	protected int id;

	protected Map<Integer,Segment> segments = new HashMap<Integer, Segment>();
	
	protected float length;
	
	protected Node sourceNode, destinationNode;
	
	protected Segment firstSegment, lastSegment;
	
	public Link(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public Node getSourceNode() {
		return sourceNode;
	}

	public void setSourceNode(Node sourceNode) {
		this.sourceNode = sourceNode;
	}

	public Node getDestinationNode() {
		return destinationNode;
	}

	public void setDestinationNode(Node destinationNode) {
		this.destinationNode = destinationNode;
	}

	public Map<Integer, Segment> getSegments() {
		return segments;
	}
	
	public void setSegments(Map<Integer,Segment> segments){
		this.segments = segments;
	}

	/**
	 * Getting first segment of the link
	 * @return first link's segment
	 */
	public Segment getFirstSegment() {
		// lazy initialization of the firstSegment variable
		if (firstSegment==null){
			for (Segment segment:segments.values()){
				if (segment.getPrevSegment()==null){
					firstSegment = segment;
					break;
				}
			}
			if (firstSegment==null)
				throw new RoadNetworkException("Unable to find first segment of the link. Road Network is incosistent.");
		}
		return firstSegment;
	}

	/**
	 * Getting last segment of the link
	 * @return last link's segment
	 */
	public Segment getLastSegment() {
		// lazy initialization of the lastSegment variable
		if (lastSegment==null){
			for (Segment segment:segments.values()){
				if (segment.getNextSegment()==null){
					lastSegment = segment;
					break;
				}
			}
			if (lastSegment==null)
				throw new RoadNetworkException("Unable to find last segment of the link. Road Network is incosistent.");
		}
		return lastSegment;
	}

	/**
	 * Get segment by id.
	 * @param segmentId segment id
	 * @return segment instance
	 */
	public Segment getSegment(int segmentId) {
		return segments.get(segmentId);
	}
	
}
