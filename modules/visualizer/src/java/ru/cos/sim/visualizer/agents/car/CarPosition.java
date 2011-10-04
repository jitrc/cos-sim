package ru.cos.sim.visualizer.agents.car;

import ru.cos.sim.visualizer.math.Vector2f;

public class CarPosition {
	public Vector2f position;
	public Vector2f direction;
	
	public CarPosition(Vector2f position, Vector2f direction) {
		super();
		this.position = position;
		this.direction = direction;
	}
	
}
