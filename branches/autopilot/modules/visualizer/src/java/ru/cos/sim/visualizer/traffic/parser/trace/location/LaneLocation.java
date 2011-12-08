package ru.cos.sim.visualizer.traffic.parser.trace.location;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.trace.Lane;
import ru.cos.sim.visualizer.traffic.parser.utils.ItemParser;

public class LaneLocation extends SegmentLocation {
	
	protected int laneId;
	protected Lane lane;
	
	public LaneLocation(Element e)
	{
		super(Type.Lane);
		read(e);
	}
	
	protected LaneLocation(Type type)
	{
		super(type);
	}
	
	public LaneLocation(int linkId, int segId, int laneId) {
		super(Type.Lane,segId,linkId);
		this.laneId = laneId;
	}
	
	public LaneLocation(int linkId, int segId, int laneId,float position) {
		super(Type.Lane,segId,linkId,position);
		this.laneId = laneId;
	}

	public int getLaneId() {
		return laneId;
	}

	public Lane getLane() {
		return lane;
	}

	protected void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}
	
	public void read(Element e)
	{
		this.laneId = ItemParser.getInteger(e, Fields.LaneId.name());
		super.read(e);
	}
	
}
