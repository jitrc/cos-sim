package ru.cos.trace.item.base;

public class Entity {

	protected int uid;

	public Entity(int uid) {
		super();
		this.uid = uid;
	}
	
	public int id()
	{
		return uid;
	}
}
