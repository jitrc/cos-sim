/**
 * 
 */
package ru.cos.sim.driver.composite.cases;

import java.util.HashSet;
import java.util.Set;

import ru.cos.sim.driver.composite.CompositeDriver;
import ru.cos.sim.driver.composite.Percepts;
import ru.cos.sim.driver.composite.TrajectoryPercepts;
import ru.cos.sim.driver.composite.cases.utils.MOBILCalculator;
import ru.cos.sim.driver.composite.cases.utils.MOBILDriverParameters;
import ru.cos.sim.driver.composite.framework.AbstractBehaviorCase;
import ru.cos.sim.driver.composite.framework.CCRange;
import ru.cos.sim.driver.composite.framework.Priority;
import ru.cos.sim.driver.composite.framework.RectangleCCRange;
import ru.cos.sim.utils.Hand;
import ru.cos.sim.vehicle.RegularVehicle;

/**
 * Lane change case responsible for lane change action 
 * induced by driver's comfort motives.<br>
 * MOBIL lane change model is employed in this implementation.
 * @author zroslaw
 */
public class LaneChangeCase extends AbstractBehaviorCase {
	
	protected MOBILCalculator mobilCalculator;

	public LaneChangeCase(CompositeDriver driver) {
		super(driver);
		mobilCalculator = new MOBILCalculator();
	}

	public void init(MOBILDriverParameters parameters){
		mobilCalculator.init(parameters);
	}
	
	@Override
	public CCRange behave(float dt) {
		RectangleCCRange ccRange = new RectangleCCRange();
		
		Set<Hand> turnRange = new HashSet<Hand>();
		Hand mobilTurn = calculateTurn();
		if (mobilTurn==null) return null; // no turn decision from mobil
		turnRange.add(mobilTurn);
		ccRange.getTurnRange().setTurnRange(turnRange);
		ccRange.setPriority(Priority.LaneChanging);
		
		return ccRange;
	}

	public Hand calculateTurn(){
		RegularVehicle vehicle = driver.getVehicle();
		Percepts percepts = driver.getPercepts();
		TrajectoryPercepts leftPercepts = percepts.getLeftPercepts();
		TrajectoryPercepts currentPercepts = percepts.getCurrentPercepts();
		TrajectoryPercepts rightPercepts = percepts.getRightPercepts();
		
		float rightLaneProfit, leftLaneProfit;
		// calculate left lane profit
		if (leftPercepts==null)
			leftLaneProfit = -Float.MAX_VALUE;
		else{
			leftLaneProfit = 
				mobilCalculator.calculate(vehicle,
					currentPercepts.getBackObstacle(), currentPercepts.getFrontObstacle(),
					leftPercepts.getBackObstacle(), leftPercepts.getFrontObstacle());
		}
		// calculate right lane profit
		if (percepts.getRightPercepts()==null)
			rightLaneProfit = -Float.MAX_VALUE;
		else{
			rightLaneProfit
				= mobilCalculator.calculate(vehicle,
					currentPercepts.getBackObstacle(), currentPercepts.getFrontObstacle(),
					rightPercepts.getBackObstacle(), rightPercepts.getFrontObstacle());
		}
		
		if (leftLaneProfit>rightLaneProfit){
			if (leftLaneProfit<0) return null;
			else return Hand.Left;
		}else{
			if (rightLaneProfit<0) return null;
			else return Hand.Right;
		}
		
	}
	
}
