package ru.cos.scene.impl;

import ru.cos.agents.car.CarPosition;

public interface ICarKeeper {

	public CarPosition getPosition(float position);
	public CarPosition getPosition(float position, CarPosition pos);
	
}
