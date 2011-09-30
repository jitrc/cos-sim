/**
 * 
 */
package ru.cos.sim.road.node;

import ru.cos.cs.lengthy.impl.JoinImpl;
import ru.cos.sim.road.RoadTrajectory;

/**
 * 
 * @author zroslaw
 */
public class NodeJoin extends JoinImpl implements RoadTrajectory{

	protected Node node;
	
	/**
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * @param node the node to set
	 */
	public void setNode(Node node) {
		this.node = node;
	}

	protected NodeJoinPoint nodeJoinPoint;
	
	/**
	 * 
	 */
	public NodeJoin() {
		this.nodeJoinPoint = new NodeJoinPoint(this);
		putPoint(nodeJoinPoint, 0);
	}

	@Override
	public float getWidth() {
		return 0;
	}

	@Override
	public final RoadTrajectoryType getRoadTrajectoryType() {
		return RoadTrajectoryType.NodeJoin;
	}

	@Override
	public float setWidth(float width) {
		return 0;
	}

	@Override
	public boolean isLane() {
		return false;
	}

	@Override
	public boolean isTransitionRules() {
		return false;
	}
	
}
