package ru.cos.nissan.parser.trace;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import ru.cos.nissan.parser.Parser;
import ru.cos.nissan.parser.geometry.Waypoint;
import ru.cos.nissan.parser.trace.base.TransitionRule;

public class PolylineTransitionRule extends TransitionRule {

	private static enum Fields {
		Geometry,
		Waypoints,
		Waypoint
	}
	private ArrayList<Waypoint> waypoints;
	private ArrayList<TransitionSegment> segments;
	
	public PolylineTransitionRule(Element e,int nodeId) {
		super(e,nodeId);
		
		this.waypoints = new ArrayList<Waypoint>();
		this.segments = new ArrayList<TransitionSegment>();
		
	}
	
	private void readWaypoints(Element e)
	{
		List<Element> wps = e.getChild(Fields.Geometry.name(),Parser.getCurrentNamespace()).
		getChild(Fields.Waypoints.name(),Parser.getCurrentNamespace()).
		getChildren(Fields.Waypoint.name(),Parser.getCurrentNamespace());
		for (Element wp : wps)
		{
			this.waypoints.add(new Waypoint(wp));
		}
	}
	
	private void readSegments(Element e)
	{
		List<Element> segs = e.getChild(Fields.Geometry.name(),Parser.getCurrentNamespace()).
		getChild(TransitionSegment.ChapterName,Parser.getCurrentNamespace())
		.getChildren(TransitionSegment.Name,Parser.getCurrentNamespace());
		for (Element segment : segs)
		{
			this.segments.add(new TransitionSegment(segment));
		}
	}
	
}
