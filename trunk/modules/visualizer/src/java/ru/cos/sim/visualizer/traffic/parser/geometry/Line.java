package ru.cos.sim.visualizer.traffic.parser.geometry;

import org.jdom.Element;

import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.traffic.parser.Parser;
import ru.cos.sim.visualizer.traffic.parser.utils.ItemParser;

public class Line extends GeometryPrimitive {
	public static String name = "Line";
	
	private static enum Fields {
		End,
		x,
		y
	}
	
	public Vector2f beginPoint;
	public Vector2f endPoint;
	
	public Line(Element e , Vector2f beginPoint)
	{
		super(Geometrytype.Line);
		this.beginPoint = beginPoint;
		
		Element end = e.getChild(Fields.End.name(),Parser.getCurrentNamespace());
		this.endPoint = new Vector2f();
		this.endPoint.x = ItemParser.getFloat(end, Fields.x.name());
		this.endPoint.y = ItemParser.getFloat(end, Fields.y.name());
	}
}
