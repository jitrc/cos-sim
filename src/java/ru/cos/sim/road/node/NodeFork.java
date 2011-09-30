/**
 * 
 */
package ru.cos.sim.road.node;

import ru.cos.cs.lengthy.impl.ForkImpl;
import ru.cos.sim.road.RoadTrajectory;

/**
 * Fork of the road trajectories when path comes into the node. 
 * @author zroslaw
 */
public class NodeFork extends ForkImpl implements RoadTrajectory {
	
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

	protected NodeForkPoint nodeForkPoint;
	
	public NodeFork(){
		this.nodeForkPoint = new NodeForkPoint(this);
		super.putPoint(nodeForkPoint, 0);
	}

	@Override
	public float getWidth() {
		return 0;
	}

	@Override
	public final RoadTrajectoryType getRoadTrajectoryType() {
		return RoadTrajectoryType.NodeFork;
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
