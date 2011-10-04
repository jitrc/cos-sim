package ru.cos.sim.visualizer.traffic.parser.trace;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.geometry.Geometry;
import ru.cos.sim.visualizer.traffic.parser.geometry.Waypoint;
import ru.cos.sim.visualizer.traffic.parser.utils.ItemParser;

public class TransitionSegment {
	private static enum Fields {
		StartWaypointId,
		EndWaypointId,
		BendAngle,
		Geometry
	}
	public static String Name = "Segments";
	public static String ChapterName = "Segment";
	
	protected Waypoint beginWaypoint;
	protected Waypoint endWaypoint;
	protected Geometry geometry;
	
	public TransitionSegment(Element segment)
	{
//		this.beginWaypoint = new Waypoint(ItemParser.getInteger(segment, Fields.StartWaypointId.name()));
//		this.endWaypoint = new Waypoint(ItemParser.getInteger(segment, Fields.EndWaypointId.name()));
		this.geometry = new Geometry(segment);
	}

	public Waypoint getBeginWaypoint() {
		return beginWaypoint;
	}

	public Waypoint getEndWaypoint() {
		return endWaypoint;
	}

	public Geometry getGeometry() {
		return geometry;
	}
}