/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

import ru.cos.sim.driver.composite.CompositeDriver;

/**
 * 
 * @author zroslaw
 */
public abstract class AbstractBehaviorCase extends BehaviorCase {

	protected CompositeDriver driver;
	
	public AbstractBehaviorCase(CompositeDriver driver){
		this.driver = driver;
	}

}
