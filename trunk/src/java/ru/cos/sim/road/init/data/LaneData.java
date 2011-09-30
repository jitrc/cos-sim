/**
 * 
 */
package ru.cos.sim.road.init.data;

/**
 * 
 * @author zroslaw
 */
public class LaneData {
	
	protected int index;

	protected float length;
	
	protected float width;
	
	protected int prevLaneIndex;
	
	protected int nextLaneIndex;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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

	public int getPrevLaneIndex() {
		return prevLaneIndex;
	}

	public void setPrevLaneIndex(int prevLaneIndex) {
		this.prevLaneIndex = prevLaneIndex;
	}

	public int getNextLaneIndex() {
		return nextLaneIndex;
	}

	public void setNextLaneIndex(int nextLaneIndex) {
		this.nextLaneIndex = nextLaneIndex;
	}
	
}
