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
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.node.NodeFork;
import ru.cos.sim.road.node.NodeForkPoint;

/**
 * If vehicle is not on the appropriate lane, then restrict maximum acceleration considering forthcoming node
 * as an obstacle.
 * @author zroslaw
 */
public class ForthcomingNode extends AbstractBehaviorCase {
	
	private IDMCalculator idmCalculator;

	public ForthcomingNode(CompositeDriver driver) {
		super(driver);
		this.idmCalculator = new IDMCalculator();
	}
	
	/**
	 * Initialize case parameters.
	 * @param parameters driver parameters
	 */
	public void init(CompositeDriverParameters parameters){
		idmCalculator.init(parameters);
	}

	@Override
	public CCRange behave(float dt) {
		Lane desiredLane = driver.getNodesDesiredIncomingLane();
		if (desiredLane==null) return null;
		
		Percepts percepts = driver.getPercepts();
		TrajectoryPercepts currentPercepts = percepts.getCurrentPercepts();
		Perception frontNofrForkPoint = currentPercepts.getFrontFork();
		if (frontNofrForkPoint==null) return null;
		NodeForkPoint forkPoint = (NodeForkPoint) frontNofrForkPoint.getRoadObject();
		NodeFork fork = forkPoint.getNodeFork();
		
		Lane currentLane = (Lane)fork.getPrev();
		if (currentLane==desiredLane) return null;
		// otherwise restrict acceleration
		// set smaller max speed when approaching closer to the intersection
		float maxSpeed = driver.getParameters().getMaxSpeed();
		float visibleRange = driver.getFrontVisibleRange();
		float distanceToNode = frontNofrForkPoint.getDistance();
		// simple linear relation
		float maxCurrentSpeed = maxSpeed*distanceToNode/visibleRange;

		RectangleCCRange ccRange = new RectangleCCRange();
		idmCalculator.setMaxSpeed(maxCurrentSpeed);
		float acceleration = idmCalculator.calculate(driver.getVehicle(), frontNofrForkPoint);
		ccRange.getAccelerationRange().setHigher(acceleration);
		
		ccRange.setPriority(Priority.ForthcomingNode);
		
		return ccRange;
	}

}
