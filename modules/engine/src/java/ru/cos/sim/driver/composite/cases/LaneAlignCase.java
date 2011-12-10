/**
 * 
 */
package ru.cos.sim.driver.composite.cases;

import java.util.HashSet;
import java.util.Set;

import ru.cos.sim.driver.CompositeDriver;
import ru.cos.sim.driver.composite.framework.AbstractBehaviorCase;
import ru.cos.sim.driver.composite.framework.CCRange;
import ru.cos.sim.driver.composite.framework.HandRange;
import ru.cos.sim.driver.composite.framework.Priority;
import ru.cos.sim.driver.composite.framework.RectangleCCRange;
import ru.cos.sim.utils.Hand;


/**
 * 
 * @author zroslaw
 */
public class LaneAlignCase extends AbstractBehaviorCase {

	
	public LaneAlignCase(CompositeDriver driver) {
		super(driver);
	}

	@Override
	public CCRange behave(float dt) {
		RectangleCCRange ccRange = new RectangleCCRange();
		Set<Hand> handSet = new HashSet<Hand>();
		
		float shift = driver.getVehicle().getShift();
		
		if (shift<0)
			handSet.add(Hand.Right);
		else if (shift==0)
			handSet.add(null);
		else if (shift>0)
			handSet.add(Hand.Left);

		HandRange turnRange = new HandRange();
		turnRange.setTurnRange(handSet);
		ccRange.setTurnRange(turnRange);
		ccRange.setPriority(Priority.LaneAlign);
		return ccRange;
	}

}
