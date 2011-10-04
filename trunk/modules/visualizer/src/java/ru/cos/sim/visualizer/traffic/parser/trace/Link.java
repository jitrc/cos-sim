package ru.cos.sim.visualizer.traffic.parser.trace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.Parser;
import ru.cos.sim.visualizer.traffic.parser.base.Entity;
import ru.cos.sim.visualizer.traffic.parser.geometry.Waypoint;
import ru.cos.sim.visualizer.traffic.parser.trace.base.StaffCollector;
import ru.cos.sim.visualizer.traffic.parser.trace.location.LinkLocation;
import ru.cos.sim.visualizer.traffic.parser.trace.staff.Gradient;
import ru.cos.sim.visualizer.traffic.parser.trace.staff.SolidLineOnRight;

public class Link extends StaffCollector {
	
	public static String Name = "Link";
	public static String ChapterName = "Links";
	
	public static enum Field {
		id,
		name,
		sourceNodeId,
		destinationNodeId,
		length,
		Waypoints,
		Waypoint,
		Segments,
		TrapeziumSegment
	}
	
	public static enum StaffField {
		Gradient,
		SolidLineOnRight
	}
	
	protected Entity sourceNode;
	protected Entity destanationNode;
	protected HashMap<Integer, Waypoint> waypoints;
	
	public Link()
	{
		super();
	}
	
	public Link(Element link) {
		this();
		
		this.uid = Integer.parseInt(link.getChildText(Field.id.name(),Parser.getCurrentNamespace()));
		initWaypoints(link);
		initNetwork(link);
		readStaff(link);
	}
	
	protected void initNetwork(Element link)
	{
		this.sourceNode = new Entity(Integer.parseInt(
				link.getChildText(Field.sourceNodeId.name(),Parser.getCurrentNamespace())));
		this.destanationNode = new Entity(Integer.parseInt(
				link.getChildText(Field.destinationNodeId.name(),Parser.getCurrentNamespace())));
		List<Element> segments = link.getChild(Field.Segments.name(),Parser.getCurrentNamespace()
				).getChildren(Field.TrapeziumSegment.name(),Parser.getCurrentNamespace());
		
		for (Element e : segments) {
			Parser.getParserCollector().segments.add(new Segment(e,this));
		}
	}
	
	protected void initWaypoints(Element link) {
		List<Element> waypoints = link.getChild(Field.Waypoints.name(),Parser.getCurrentNamespace()
				).getChildren(Field.Waypoint.name(),Parser.getCurrentNamespace());
		this.waypoints = new HashMap<Integer, Waypoint>();
		for (Element w : waypoints) {
			Waypoint wp = new Waypoint(w);
			this.waypoints.put(wp.id(), wp);
		}
	}
	
	protected void readStaff(Element link)
	{
		List<Element> gradients = link.getChildren(Gradient.Name,Parser.getCurrentNamespace());
		for (Element gradient : gradients)
		{
			Parser.getParserCollector().staff.add(new Gradient(gradient, new LinkLocation(id())));
		}
		
		List<Element> solids = link.getChildren(SolidLineOnRight.Name,Parser.getCurrentNamespace());
		for (Element solid : solids)
		{
			Parser.getParserCollector().staff.add(new SolidLineOnRight(solid, id() ));
		}
	}
	
	public Waypoint getWP(int id) {
		return this.waypoints.get(id);
	}
	
}
