/**
 * 
 */
package ru.cos.sim.driver.composite.cases;

import ru.cos.sim.driver.composite.CompositeDriver;
import ru.cos.sim.driver.composite.CompositeDriverParameters;
import ru.cos.sim.driver.composite.Perception;
import ru.cos.sim.driver.composite.Percepts;
import ru.cos.sim.driver.composite.TrajectoryPercepts;
import ru.cos.sim.driver.composite.cases.utils.IDMCalculator;
import ru.cos.sim.driver.composite.framework.AbstractBehaviorCase;
import ru.cos.sim.driver.composite.framework.CCRange;
import ru.cos.sim.driver.composite.framework.Priority;
import ru.cos.sim.driver.composite.framework.RectangleCCRange;

/**
 * @author zroslaw
 *
 */
public class RespectNodeCase extends AbstractBehaviorCase {
	
	private IDMCalculator idmCalculator;

	public RespectNodeCase(CompositeDriver driver) {
		super(driver);
		idmCalculator = new IDMCalculator();
	}

	public void init(CompositeDriverParameters parameters){
		this.idmCalculator.init(parameters);
	}
	
	@Override
	public CCRange behave(float dt) {
		Percepts percepts = driver.getPercepts();
		TrajectoryPercepts currentPercepts = percepts.getCurrentPercepts();
		
		Perception frontForkPerception = currentPercepts.getFrontFork();
		Perception frontJoinPerception = currentPercepts.getFrontJoin();
		Perception frontObstaclePerception = currentPercepts.getFrontObstacle();
		if (frontForkPerception==null ||
			frontJoinPerception==null ||
			frontObstaclePerception==null) return null;
		
		if (frontObstaclePerception.getActualDistance()<frontJoinPerception.getActualDistance() &&
			frontObstaclePerception.getActualDistance()>frontForkPerception.getActualDistance());
		else return null;
		/*
		 * Formal criterion for respecting node place is:
		 * Expected time when front obstacle goes out the node is more than 5 seconds.
		 */
		float distanceToEndOfNode = frontJoinPerception.getActualDistance()-frontObstaclePerception.getActualDistance();
		float timeOfFrontObstacle = distanceToEndOfNode/frontObstaclePerception.getRoadObject().getSpeed();
		if (timeOfFrontObstacle<5.f) return null;
		
		RectangleCCRange ccRange = new RectangleCCRange();
		float acceleration = idmCalculator.calculate(driver.getVehicle(), frontForkPerception);
		ccRange.getAccelerationRange().setHigher(acceleration);
		ccRange.setPriority(Priority.RespectNodeCase);
		
		return ccRange;
	}

}
