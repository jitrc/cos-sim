/**
 * 
 */
package ru.cos.sim.road.init.data;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author zroslaw
 *
 */
public class LinkData {

	protected int id;

	protected int sourceNodeId;
	
	protected int destinationNodeId;
	
	protected float length;
	
	protected Map<Integer, SegmentData> segments = new TreeMap<Integer, SegmentData>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public int getSourceNodeId() {
		return sourceNodeId;
	}

	public void setSourceNodeId(int sourceNodeId) {
		this.sourceNodeId = sourceNodeId;
	}

	public int getDestinationNodeId() {
		return destinationNodeId;
	}

	public void setDestinationNodeId(int destinationNodeId) {
		this.destinationNodeId = destinationNodeId;
	}

	public Map<Integer, SegmentData> getSegments() {
		return segments;
	}

	public void setSegments(Map<Integer, SegmentData> segments) {
		this.segments = segments;
	}
	
	
}
