/**
 * 
 */
package ru.cos.sim.road.init.data;

import ru.cos.sim.road.link.Lane;

/**
 * Link location data
 * @author zroslaw
 */
public class LinkLocationData extends LocationData {

	protected int linkId;
	
	protected int segmentId;
	
	protected int laneIndex;
	
	public LinkLocationData(Lane lane) {
		this.laneIndex = lane.getIndex();
		this.segmentId = lane.getSegment().getId();
		this.linkId = lane.getLink().getId();
	}

	public LinkLocationData() {
	}

	@Override
	public final LocationType getLocationType() {
		return LocationType.LinkLocation;
	}

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

	public int getLaneIndex() {
		return laneIndex;
	}

	public void setLaneIndex(int laneIndex) {
		this.laneIndex = laneIndex;
	}

}
