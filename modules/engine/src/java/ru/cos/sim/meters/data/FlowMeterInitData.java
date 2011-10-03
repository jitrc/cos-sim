/**
 * 
 */
package ru.cos.sim.meters.data;

/**
 *
 * @author zroslaw
 */
public class FlowMeterInitData extends MeterData {
	
	private int linkId;
	
	private int segmentId;
	
	private float position;
	
	private float measuringTime;
	
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
	
	public void setMeasuringTime(float measuringTime) {
		this.measuringTime = measuringTime;
	}
	
	public float getMeasuringTime() {
		return measuringTime;
	}
	
}
