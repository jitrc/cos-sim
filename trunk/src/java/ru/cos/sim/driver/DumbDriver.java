/**
 * 
 */
package ru.cos.sim.driver;

import ru.cos.cs.lengthy.Fork;
import ru.cos.cs.lengthy.Join;
import ru.cos.cs.lengthy.Lengthy;
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
	public Lengthy chooseNextLengthy(Join join) {
		return join.getJoinedLengthies().iterator().next();
	}

	@Override
	public Lengthy chooseNextLengthy(Fork fork) {
		return fork.getForkedLengthies().iterator().next();
	}

	@Override
	public final DriverType getDriverType() {
		return DriverType.Dumb;
	}

}
