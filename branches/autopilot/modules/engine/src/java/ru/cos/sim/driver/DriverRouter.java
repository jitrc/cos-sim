package ru.cos.sim.driver;

import ru.cos.cs.lengthy.Router;
import ru.cos.sim.vehicle.Vehicle;

public interface DriverRouter extends Router{
	void act(float dt);
	void init(Vehicle vehicle);
	int getNextLinkId(int prevLinkId);
}
