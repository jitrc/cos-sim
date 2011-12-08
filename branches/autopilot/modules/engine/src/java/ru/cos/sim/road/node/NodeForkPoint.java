/**
 * 
 */
package ru.cos.sim.road.node;

import ru.cos.cs.lengthy.impl.objects.PointImpl;
import ru.cos.sim.road.objects.RoadObject;
import ru.cos.sim.utils.Hand;

/**
 * 
 * @author zroslaw
 */
public class NodeForkPoint extends PointImpl implements RoadObject {
	
	protected NodeFork nodeFork;
	
	/**
	 * @param nodeFork
	 */
	public NodeForkPoint(NodeFork nodeFork) {
		this.nodeFork = nodeFork;
	}

	@Override
	public final float getShift() {
		return 0;
	}

	@Override
	public RoadObject.RoadObjectType getRoadObjectType() {
		return RoadObject.RoadObjectType.NodeFork;
	}

	public NodeFork getNodeFork() {
		return nodeFork;
	}

	@Override
	public boolean isOnLink() {
		return false;
	}

	@Override
	public boolean isOnNode() {
		return false;
	}

	@Override
	public void setShift(float shift) {
		// do nothing
	}

	@Override
	public float getSpeed() {
		return 0;
	}

	@Override
	public void setSpeed(float speed) {
		// do nothing
	}

	@Override
	public Hand getShiftHand() {
		return null;
	}
	
}
