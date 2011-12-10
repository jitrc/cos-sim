/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

import ru.cos.sim.driver.CompositeDriver;
import ru.cos.sim.driver.data.IDMDriverParameters;

/**
 * 
 * @author zroslaw
 */
public abstract class AbstractBehaviorCase implements BehaviorCase {

	protected CompositeDriver driver;
	
	public AbstractBehaviorCase(CompositeDriver driver){
		this.driver = driver;
	}

	@Override
	public void init(IDMDriverParameters parameters) {
	}
}
