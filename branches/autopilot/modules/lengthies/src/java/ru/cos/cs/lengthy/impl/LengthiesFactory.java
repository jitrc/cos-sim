/**
 * 
 */
package ru.cos.cs.lengthy.impl;

import ru.cos.cs.lengthy.*;
import ru.cos.cs.lengthy.objects.*;
import ru.cos.cs.lengthy.objects.continuous.*;

/**
 * 
 * @author zroslaw
 */
public interface LengthiesFactory {
	
	/**
	 * Creates lengthy instance.
	 * @param length length of the lengthy to be created
	 * @return lengthy instance
	 */
	public RegularLengthy createLengthy(float length);

	/**
	 * Create simple observable.
	 * @return
	 */
	public Point createObservable();

	public Continuous createContinuous(float length);

	public Join createJoin();

	public Fork createFork();
	
}
