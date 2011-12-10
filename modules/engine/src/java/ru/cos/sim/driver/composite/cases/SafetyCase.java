/**
 * 
 */
package ru.cos.sim.driver.composite.cases;

import ru.cos.sim.driver.CompositeDriver;
import ru.cos.sim.driver.composite.Perception;
import ru.cos.sim.driver.composite.Percepts;
import ru.cos.sim.driver.composite.TrajectoryPercepts;
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
public class SafetyCase extends AbstractBehaviorCase{
	
	public static float MIN_SAFE_DISTANCE = 0.5f;

	private HandRange safeTurns = new HandRange();
	
	public SafetyCase(CompositeDriver driver) {
		super(driver);
	}

	@Override
	public CCRange behave(float dt) {
		RectangleCCRange range = new RectangleCCRange();
		Percepts percepts = driver.getPercepts();
		
		HandRange turnRange = range.getTurnRange();
		TrajectoryPercepts leftPercepts = percepts.getLeftPercepts();
		float shift = driver.getVehicle().getShift();
		if (shift<=0 && (leftPercepts==null || !checkForSafety(leftPercepts)))
			turnRange.remove(Hand.Left); // restrict left turn
		TrajectoryPercepts rightPercepts = percepts.getRightPercepts();
		if (shift>=0 && (rightPercepts==null || !checkForSafety(rightPercepts)))
			turnRange.remove(Hand.Right);
		
		// remember safe turns
		safeTurns = turnRange.clone();
		
		// return result
		range.setPriority(Priority.SafetyCase);
		return range;
	}
	
	/**
	 * 
	 * @param trajectoryPercepts
	 * @return true if it is safe
	 */
	private boolean checkForSafety(TrajectoryPercepts trajectoryPercepts){
		return checkForSafety(trajectoryPercepts.getBackObstacle(), trajectoryPercepts.getFrontObstacle());
	}

	/**
	 * 
	 * @param back
	 * @param front
	 * @return true if it is safe
	 */
	public boolean checkForSafety(Perception back, Perception front){
		if ((back==null || back.getDistance()+MIN_SAFE_DISTANCE<0) && 
			(front==null || front.getDistance()-MIN_SAFE_DISTANCE>0))
			return true;
		else
			return false;
	}

	public HandRange getSafeTurns() {
		return safeTurns;
	}
	
}
