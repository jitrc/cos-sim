package ru.cos.scene.impl;

import ru.cos.trace.item.base.TrafficLight.Color;

public interface ITrafficLight extends IPlaceable{
	public void switchLight(Color color);
	public Color getLightColor();
	public Float getPosition();
}
