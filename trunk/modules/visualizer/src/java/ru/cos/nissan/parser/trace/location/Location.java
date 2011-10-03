package ru.cos.nissan.parser.trace.location;

public abstract class Location {

	public static enum Type {
		Global,
		Link,
		Segment,
		Lane,
		Rule,
		Node
	}
	
	protected boolean hasPosition = false;
	protected float position = 0;
	protected Type type;
	
	public Location(Type type)
	{
		this.type = type;
	}
	
	public Location(Type type, float position)
	{
		this.type = type;
		this.setPosition(position);
		this.hasPosition = true;
	}
	
	public boolean isHasPosition() {
		return hasPosition;
	}
	
	public Type getType()
	{
		return type;
	}

	public float getPosition() {
		return position;
	}

	public void setPosition(float position) {
		this.position = position;
		hasPosition = true;
	}
	
}
