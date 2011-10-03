package ru.cos.nissan.parser.geometry;

import org.jdom.Element;

import ru.cos.nissan.parser.utils.ItemParser;

public class Point {

	protected static enum Fields {
		x,
		y
	}
	
	public float x;
	public float y;
	
	public Point(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Point(Element e)
	{
		this.x = ItemParser.getFloat(e, Fields.x.name());
		this.y = ItemParser.getFloat(e, Fields.y.name());
	}
	
	
}
