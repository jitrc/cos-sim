package ru.cos.sim.visualizer.traffic.parser.trace;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.Parser;
import ru.cos.sim.visualizer.traffic.parser.trace.base.StaffCollector;
import ru.cos.sim.visualizer.traffic.parser.trace.location.LaneLocation;
import ru.cos.sim.visualizer.traffic.parser.trace.location.SegmentLocation;
import ru.cos.sim.visualizer.traffic.parser.trace.staff.LineDirection;
import ru.cos.sim.visualizer.traffic.parser.utils.ItemParser;

public class Lane extends StaffCollector {

	public static String Name = "Lane";
	public static String ChapterName = "Lanes";
	
	protected static enum Fields {
		index,
		length,
		nextLaneIndex,
		prevLaneIndex,
		width
	}
	
	protected float length;
	protected float width;
	protected int next;
	protected int previous;
	protected SegmentLocation location;
	
	public Lane(int id)
	{
		super(id);
	}
	
	public Lane(Element lane, int segId, int linkId) {
		super(lane);
		this.uid = ItemParser.getInteger(lane, Fields.index.name());
		this.location = new SegmentLocation(segId, linkId);
		this.length = ItemParser.getFloat(lane, Fields.length.name());
		this.width = ItemParser.getFloat(lane, Fields.width.name());
		this.next = ItemParser.getInteger(lane, Fields.nextLaneIndex.name());
		this.previous = ItemParser.getInteger(lane, Fields.prevLaneIndex.name());
		
		readStaff(lane);
	}
	
	private void readStaff(Element e)
	{
		Element laneDirection = e.getChild(LineDirection.name,Parser.getCurrentNamespace());
		if ( laneDirection != null) {
			LaneLocation loc = new LaneLocation(this.location.getLinkId(),
					this.location.getSegmentId(), this.id());
			LineDirection ld = new LineDirection(laneDirection,loc);
			Parser.getParserCollector().staff.add(ld);
		}
	}

	public float getLength() {
		return length;
	}

	public float getWidth() {
		return width;
	}

	public int getNext() {
		return next;
	}

	public int getPrevious() {
		return previous;
	}

	public SegmentLocation getLocation() {
		return location;
	}
	
}
