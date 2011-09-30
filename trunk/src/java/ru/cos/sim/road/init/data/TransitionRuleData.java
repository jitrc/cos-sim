/**
 * 
 */
package ru.cos.sim.road.init.data;

/**
 * 
 * @author zroslaw
 */
public class TransitionRuleData {

	protected int id;
	
	protected float length;
	
	protected float width;
	
	protected int sourceLinkId;
	
	protected int sourceLaneIndex;
	
	protected int destinationLinkId;
	
	protected int destinationLaneIndex;

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

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public int getSourceLinkId() {
		return sourceLinkId;
	}

	public void setSourceLinkId(int sourceLinkId) {
		this.sourceLinkId = sourceLinkId;
	}

	public int getSourceLaneIndex() {
		return sourceLaneIndex;
	}

	public void setSourceLaneIndex(int sourceLaneIndex) {
		this.sourceLaneIndex = sourceLaneIndex;
	}

	public int getDestinationLinkId() {
		return destinationLinkId;
	}

	public void setDestinationLinkId(int destinationLinkId) {
		this.destinationLinkId = destinationLinkId;
	}

	public int getDestinationLaneIndex() {
		return destinationLaneIndex;
	}

	public void setDestinationLaneIndex(int destinationLaneIndex) {
		this.destinationLaneIndex = destinationLaneIndex;
	}
	
}
