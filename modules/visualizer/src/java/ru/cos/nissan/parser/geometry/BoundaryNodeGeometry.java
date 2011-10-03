package ru.cos.nissan.parser.geometry;

import org.jdom.Element;

import ru.cos.nissan.parser.Parser;
import ru.cos.nissan.parser.utils.ItemParser;

public class BoundaryNodeGeometry {
	public static String Name = "CircleGeometry";
	
	private static enum Fields {
		Center,
		radius
	}
	public Point center;
	public float radius;
	
	public BoundaryNodeGeometry(Element e)
	{
		this.center = new Point(e.getChild(Fields.Center.name(),Parser.getCurrentNamespace()));
		this.radius = ItemParser.getFloat(e, Fields.radius.name());
	}
}
