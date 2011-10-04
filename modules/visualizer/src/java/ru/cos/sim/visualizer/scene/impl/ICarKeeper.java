package ru.cos.sim.visualizer.scene.impl;

import ru.cos.sim.visualizer.agents.car.CarPosition;

public interface ICarKeeper {

	public CarPosition getPosition(float position);
	public CarPosition getPosition(float position, CarPosition pos);
	
}
