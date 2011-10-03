package ru.cos.trace.item.base;

import ru.cos.trace.item.TransitionRule;

public final class TrafficLight extends Entity {
	public static enum Color {
		Red,
		Yellow,
		Green,
		None
	}
	
	public TransitionRule rule;
	public float position;
	public Color color;
	
	public TrafficLight(int uid, TransitionRule rule, float position, Color color) {
		super(uid);
		this.rule = rule;
		this.position = position;
		this.color = color;
	}
	
	
}
