/**
 * 
 */
package ru.cos.sim.driver;

import ru.cos.sim.driver.composite.CompositeDriverParameters;
import ru.cos.sim.driver.composite.Percepts;
import ru.cos.sim.driver.composite.framework.BehaviorCase;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.utils.Hand;

/**
 * Composite Driver.
 * @author zroslaw
 */
public interface CompositeDriver extends Driver {
	void init();
	void addBehaviorCase(BehaviorCase behaviorCase);
	
	Percepts getPercepts();
	CompositeDriverParameters getParameters();
	DriverRouter getRouter();

	float getFrontVisibleRange();

	//public boolean isTurnSafe(Hand turnHand);

	/**
	 * Retrieve lane from which driver wants to enter the node.
	 * @return desired incoming lane
	 */
	//public Lane getNodesDesiredIncomingLane();

}
