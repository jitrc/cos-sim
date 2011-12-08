package ru.cos.sim.visualizer.traffic.parser.trace;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.Parser;
import ru.cos.sim.visualizer.traffic.parser.geometry.Point;

public class NodeGeometry {
	public static String Name = "PolygonGeometry";
	private String Point = "Point";
	
	public ArrayList<Point> points;

	public NodeGeometry(Element e) {
		this.points = new ArrayList<Point>();
		
		List<Element> points = e.getChildren(Point,Parser.getCurrentNamespace());
		for (Element point : points)
		{
			this.points.add(new Point(point));
		}
	}
	
	
}
