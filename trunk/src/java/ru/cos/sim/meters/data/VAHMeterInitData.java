/**
 * 
 */
package ru.cos.sim.meters.data;


/**
 *
 * @author zroslaw
 */
public class VAHMeterInitData extends MeterData {
	
	private int linkId;
	
	private int segmentId;
	
	private float position;
	
	private float timeBin;
	
	public int getLinkId() {
		return linkId;
	}
	
	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}
	
	public int getSegmentId() {
		return segmentId;
	}
	
	public void setSegmentId(int segmentId) {
		this.segmentId = segmentId;
	}
	
	public float getPosition() {
		return position;
	}
	
	public void setPosition(float position) {
		this.position = position;
	}
	
	public void setTimeBin(float timeBin) {
		this.timeBin = timeBin;
	}
	
	public float getTimeBin() {
		return timeBin;
	}
	
}
