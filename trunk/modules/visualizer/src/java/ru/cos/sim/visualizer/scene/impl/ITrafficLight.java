package ru.cos.sim.visualizer.scene.impl;

import ru.cos.sim.visualizer.trace.item.base.TrafficLight.Color;

public interface ITrafficLight extends IPlaceable{
	public void switchLight(Color color);
	public Color getLightColor();
	public Float getPosition();
}
