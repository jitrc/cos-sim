package ru.cos.nissan.parser.geometry;

import java.util.ArrayList;

import org.jdom.Element;

import ru.cos.math.Vector2f;
import ru.cos.nissan.parser.Parser;
import ru.cos.nissan.parser.utils.ItemParser;

public class Side {
	public static enum Names { 
		LeftSide,
		RightSide
	}
	
	protected static enum Fields {
		Start,
		End,
		x,
		y,
		Override
	}
	public float startX;
	public float startY;
	public float endX;
	public float endY;
	
	public ArrayList<GeometryPrimitive> override;
	public boolean overrided = false;
	
	public Side(Element e)
	{
		Element start = e.getChild(Fields.Start.name(),Parser.getCurrentNamespace());
		startX = ItemParser.getFloat(start, Fields.x.name());
		startY = ItemParser.getFloat(start, Fields.y.name());
		
		Element end = e.getChild(Fields.End.name(),Parser.getCurrentNamespace());
		endX = ItemParser.getFloat(end, Fields.x.name());
		endY = ItemParser.getFloat(end, Fields.y.name());
		
		Element override = e.getChild(Fields.Override.name(),Parser.getCurrentNamespace());
		
		if (override != null) {
			this.overrided = true;
			this.override = new ArrayList<GeometryPrimitive>();
			Vector2f begin = new Vector2f();
			Element starte = override.getChild(Fields.Start.name(),Parser.getCurrentNamespace());
			begin.x = ItemParser.getFloat(starte, Fields.x.name());
			begin.y = ItemParser.getFloat(starte, Fields.y.name());
			this.startX = begin.x;
			this.startY = begin.y;
			for (Object o : override.getChildren()) {
				Element oe = (Element) o;
				if (oe.getName().equals(Arc.name)) {
					Arc arc = new Arc(oe, begin);
					this.override.add(arc);
					begin = arc.getEndPoint();
					this.endX = begin.x;
					this.endY = begin.y;
					continue;
				}
				if (oe.getName().equals(Line.name)) {
					Line line  = new Line(oe, begin);
					this.override.add(line);
					begin = line.endPoint;
					this.endX = begin.x;
					this.endY = begin.y;
					continue;
				}
			}
		}
	}
	
	public Side(float startX, float startY, float endX, float endY) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
	
	public Vector2f getVector()
	{
		return new Vector2f(endX - startX, endY - startY);
	}
	
	
}
