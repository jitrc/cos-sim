package ru.cos.sim.visualizer.traffic.parser.trace;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.Parser;
import ru.cos.sim.visualizer.traffic.parser.geometry.Geometry;
import ru.cos.sim.visualizer.traffic.parser.geometry.SegmentGeometry;
import ru.cos.sim.visualizer.traffic.parser.geometry.Waypoint;
import ru.cos.sim.visualizer.traffic.parser.trace.base.StaffCollector;
import ru.cos.sim.visualizer.traffic.parser.trace.location.LinkLocation;
import ru.cos.sim.visualizer.traffic.parser.trace.location.SegmentLocation;
import ru.cos.sim.visualizer.traffic.parser.trace.staff.Sign;
import ru.cos.sim.visualizer.traffic.parser.utils.ItemParser;

public class Segment extends StaffCollector {
	
	public static String Name = "TrapeziumSegment";
	public static String ChapterName = "Segments";
	
	private static enum Fields {
		nextSegmentId,
		prevSegmentId,
		TrapeziumShift,
		StartWaypointId,
		EndWaypointId,
		BendAngle,
		TrapeziumGeometry,
		Profile
	}
	
	public static enum SegmentType {
		Simple,
		Narrow
	}
	
	protected int next;
	protected int previous;
	protected Waypoint beginWaypoint;
	protected Waypoint endWaypoint;
	protected SegmentGeometry geometry;
	protected LinkLocation location;
	protected SegmentType segmentType;

	public Segment(int uid)
	{
		super(uid);
	}
	
	public Segment(Element segment,Link link) {
		super(segment);
		this.location = new LinkLocation(link.id());
		this.next = ItemParser.getInteger(segment, Fields.nextSegmentId.name());
		this.previous = ItemParser.getInteger(segment, Fields.prevSegmentId.name());
//		int startWPID = ItemParser.getInteger(segment, Fields.StartWaypointId.name());
//		int endWpId = ItemParser.getInteger(segment, Fields.EndWaypointId.name());
//		this.beginWaypoint = link.getWP(startWPID); 
//		this.endWaypoint = link.getWP(endWpId);
		this.geometry = new SegmentGeometry(
				segment.getChild(
						Fields.TrapeziumGeometry.name(),Parser.getCurrentNamespace()));
		readLanes(segment);
		readStaffs(segment);
		
		this.segmentType = SegmentType.Simple;
	}
	
	private void readLanes(Element e)
	{
		List<Element> lanes = e.getChild(Lane.ChapterName,Parser.getCurrentNamespace()).
				getChildren(Lane.Name,Parser.getCurrentNamespace());
		for (Element lane: lanes)
		{
			Parser.getParserCollector().lanes.add(new Lane(lane,this.id(),location.getLinkId()));
		}
	}
	
	private void readStaffs(Element e)
	{
		if (e.getChild(Sign.ChapterName,Parser.getCurrentNamespace()) == null) return;
		List<Element> signs = e.getChild(Sign.ChapterName,Parser.getCurrentNamespace()).
				getChildren(Sign.Type.SpeedLimitSign.name(),Parser.getCurrentNamespace());
		for (Element sign : signs)
		{
			Parser.getParserCollector().staff.add(new Sign(sign, new SegmentLocation(id(), 
					this.location.getLinkId()), Sign.Type.SpeedLimitSign));
		}
		
		signs = e.getChild(Sign.ChapterName,Parser.getCurrentNamespace()).
				getChildren(Sign.Type.NoSpeedLimitSign.name(),Parser.getCurrentNamespace());
		for (Element sign : signs)
		{
			Parser.getParserCollector().staff.add(new Sign(sign, new SegmentLocation(id(), 
					this.location.getLinkId()), Sign.Type.NoSpeedLimitSign));
		}
	}

	public int getNext() {
		return next;
	}

	public int getPrevious() {
		return previous;
	}

	public Waypoint getBeginWaypoint() {
		return beginWaypoint;
	}

	public Waypoint getEndWaypoint() {
		return endWaypoint;
	}

	public SegmentGeometry getGeometry() {
		return geometry;
	}

	public LinkLocation getLocation() {
		return location;
	}

	public SegmentType getSegmentType() {
		return segmentType;
	}
}
