package ru.cos.sim.driver;

import ru.cos.cs.lengthy.Fork;
import ru.cos.cs.lengthy.Join;
import ru.cos.cs.lengthy.Lengthy;
import ru.cos.sim.vehicle.Vehicle;

public class DumbDriverRouter implements DriverRouter {

	@Override
	public Lengthy getPrevLengthy(Join join) {
		return null;
	}

	@Override
	public Lengthy getNextLengthy(Fork fork) {
		return null;
	}

	@Override
	public void act(float dt) {
	}

	@Override
	public void init(Vehicle vehicle) {
	}

	@Override
	public int getNextLinkId(int prevLinkId) {
		return 0;
	}

}
