package ru.cos.nissan.parser.trace;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import ru.cos.nissan.parser.Parser;
import ru.cos.nissan.parser.geometry.Point;

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
