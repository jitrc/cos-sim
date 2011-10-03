/**
 * 
 */
package ru.cos.cs.lengthy.impl;

import ru.cos.cs.lengthy.Fork;
import ru.cos.cs.lengthy.Join;
import ru.cos.cs.lengthy.RegularLengthy;
import ru.cos.cs.lengthy.impl.objects.PointImpl;
import ru.cos.cs.lengthy.impl.objects.continuous.ContinuousImpl;
import ru.cos.cs.lengthy.objects.Point;
import ru.cos.cs.lengthy.objects.continuous.Continuous;

/**
 * 
 * @author zroslaw
 */
public class LengthiesFactoryImpl implements LengthiesFactory {

	/* (non-Javadoc)
	 * @see ru.cos.cs.lengthy.LengthiesFactory#createLengthy(float)
	 */
	@Override
	public RegularLengthy createLengthy(float length) {
		return new RegularLengthyImpl(length);
	}

	@Override
	public Point createObservable() {
		return new PointImpl();
	}

	@Override
	public Continuous createContinuous(float length) {
		return new ContinuousImpl(length);
	}

	@Override
	public Join createJoin() {
		return new JoinImpl();
	}

	@Override
	public Fork createFork() {
		return new ForkImpl();
	}

}
