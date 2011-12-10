/**
 * 
 */
package ru.cos.sim.driver;

import ru.cos.sim.utils.Hand;
import ru.cos.sim.utils.Pair;

/**
 * 
 * @author zroslaw
 */
public class DumbDriver extends AbstractDriver {

	@Override
	public Pair<Float, Hand> drive(float dt) {
		return new Pair<Float, Hand>(1.f, null);
	}

	@Override
	public DriverRouter getDriverRouter() {
		return new DumbDriverRouter();
	}

	@Override
	public final DriverType getDriverType() {
		return DriverType.Dumb;
	}

}
