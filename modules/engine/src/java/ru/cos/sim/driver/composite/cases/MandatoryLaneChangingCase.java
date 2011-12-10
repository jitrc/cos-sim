/**
 * 
 */
package ru.cos.sim.driver.composite.cases;

import java.util.Set;

import ru.cos.sim.driver.CompositeDriver;
import ru.cos.sim.driver.composite.Perception;
import ru.cos.sim.driver.composite.Percepts;
import ru.cos.sim.driver.composite.TrajectoryPercepts;
import ru.cos.sim.driver.composite.framework.AbstractBehaviorCase;
import ru.cos.sim.driver.composite.framework.CCRange;
import ru.cos.sim.driver.composite.framework.HandRange;
import ru.cos.sim.driver.composite.framework.Priority;
import ru.cos.sim.driver.composite.framework.RectangleCCRange;
import ru.cos.sim.exceptions.TrafficSimulationException;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.link.Link;
import ru.cos.sim.road.node.Node;
import ru.cos.sim.road.node.Node.NodeType;
import ru.cos.sim.road.node.NodeFork;
import ru.cos.sim.road.node.NodeForkPoint;
import ru.cos.sim.road.node.RegularNode;
import ru.cos.sim.utils.Hand;
import ru.cos.sim.vehicle.RegularVehicle;

/**
 * @author zroslaw
 *
 */
public class MandatoryLaneChangingCase extends AbstractBehaviorCase {

	private Lane desiredLane = null;
	
	/**
	 * @param driver
	 */
	public MandatoryLaneChangingCase(CompositeDriver driver) {
		super(driver);
	}

	@Override
	public CCRange behave(float dt) {
		/**
		 * 1. Choose appropriate lane to enter forthcoming node
		 * 2. If we are not on the appropriate lane
		 * 3 Turn on the lane
		 * 4. If we are on an appropriate lane - restrict any turns 
		 * that will lead us to inappropriate lanes.
		 */
		desiredLane = null;
		Percepts percepts = driver.getPercepts();
		TrajectoryPercepts currentPercepts = percepts.getCurrentPercepts();
		Perception frontNofrForkPoint = currentPercepts.getFrontFork();
		if (frontNofrForkPoint==null) return null;
		
		NodeForkPoint forkPoint = (NodeForkPoint) frontNofrForkPoint.getRoadObject();
		NodeFork fork = forkPoint.getNodeFork();
		Node node = fork.getNode();
		if (node.getNodeType()!=NodeType.RegularNode) return null;
		RegularVehicle vehicle = driver.getVehicle();
		Lane lane = (Lane) fork.getPrev();
		RegularNode regularNode = (RegularNode) node;
		Link incomingLink = ((Lane)fork.getPrev()).getLink();
		int incomingLinkId = incomingLink.getId();
		int ougtoingLinkId = driver.getNavigator().getNextLinkId(incomingLinkId);
		
		/** 1. Choose appropriate lane */
		Set<Lane> appropriateLanes = regularNode.getAppropriateLanes(incomingLinkId, ougtoingLinkId);
		desiredLane = findNearestLane(appropriateLanes,lane);
		
		/** 3. If we are on the appropriate lane */
		if (lane == desiredLane){
			RectangleCCRange ccRange = new RectangleCCRange();
			HandRange turnRange = new HandRange();
			// check for left lane appropriance
			if (!lane.isLeftmost()){
				Lane leftLane = lane.getLeftLane();
				if (!appropriateLanes.contains(leftLane)&&vehicle.getShift()<=0)
					turnRange.remove(Hand.Left);
			}
			// check for left lane appropriance
			if (!lane.isRightmost()){
				Lane rightLane = lane.getRightLane();
				if (!appropriateLanes.contains(rightLane)&&vehicle.getShift()>=0)
					turnRange.remove(Hand.Right);
			}
			ccRange.setTurnRange(turnRange);
			ccRange.setPriority(Priority.ForthcomingNode);
			return ccRange;
		}
		
		/** 2. We are not on the appropriate lane :( */
		RectangleCCRange ccRange = new RectangleCCRange();
		Hand turnHand = null;
		if (desiredLane.getIndex()>lane.getIndex())
			turnHand = Hand.Right;
		if (desiredLane.getIndex()<lane.getIndex())
			turnHand = Hand.Left;
		
		// try to change lane in desired hand,
		ccRange.getTurnRange().setOneHand(turnHand);

		ccRange.setPriority(Priority.MandatoryLaneChanging);
		
		return ccRange;
	}

	/**
	 * Find nearest lane from set of appropriate ones
	 * @param appropriateLanes set of appropriate lane
	 * @param lane lane from to which nearest lane must be  found
	 * @return nearest lane instance
	 */
	private Lane findNearestLane(Set<Lane> appropriateLanes, Lane lane){
		// find nearest index of appropriate lane incoming in the observed node
		int index=-1;
		int difference = Integer.MAX_VALUE;
		Lane result = null;
		for (Lane appLane:appropriateLanes){
			if (appLane==lane){ // if current lane in the set of appropriate lanes
				return lane;
			}
			if (index>-1){ 
				// at least one appropriate lane already has been found
				int newDifference = Math.abs(appLane.getIndex()-lane.getIndex());
				if (newDifference<difference){
					index = appLane.getIndex();
					result = appLane;
					difference = newDifference;
				}
			}else{
				// first lane among appropriate lanes set
				index = appLane.getIndex();
				difference = Math.abs(appLane.getIndex()-lane.getIndex());
				result = appLane;
			}
		}
		if (result==null)
			throw new TrafficSimulationException("Unexptected error, unable to find nearest lane");
		return result;
	}

	public Lane getDesiredLane() {
		return desiredLane;
	}
}
