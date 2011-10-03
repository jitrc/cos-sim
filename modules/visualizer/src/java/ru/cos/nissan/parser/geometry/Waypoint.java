package ru.cos.nissan.parser.geometry;

import org.jdom.Element;

import ru.cos.math.Vector2f;
import ru.cos.nissan.parser.base.Entity;
import ru.cos.nissan.parser.utils.ItemParser;

public class Waypoint extends Entity {

	protected static enum Fields {
		x,
		y
	}
	
	public float x;
	public float y;
	
	public Waypoint(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Waypoint() {
		super();
	}

	public Waypoint(Element e) {
		this.x = ItemParser.getFloat(e, Fields.x.name());
		this.y = ItemParser.getFloat(e, Fields.y.name());
	}
	
	public Vector2f getVector(Waypoint end)
	{
		Vector2f vector = new Vector2f(end.x -x , end.y - y);
		return vector;
	}
	
}
