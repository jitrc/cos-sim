/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

import ru.cos.sim.driver.data.IDMDriverParameters;


/**
 * 
 * @author zroslaw
 */
public interface BehaviorCase {

	CCRange behave(float dt);
	void init(IDMDriverParameters parameters);
	
}
