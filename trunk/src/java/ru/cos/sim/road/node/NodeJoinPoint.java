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
public class NodeJoinPoint extends PointImpl implements RoadObject{

	protected NodeJoin nodeJoin;
	
	public NodeJoinPoint(NodeJoin nodeJoin) {
		this.nodeJoin = nodeJoin;
	}

	public NodeJoin getNodeJoin() {
		return nodeJoin;
	}

	@Override
	public float getShift() {
		return 0;
	}

	@Override
	public void setShift(float shift) {
		// do nothing
	}

	@Override
	public RoadObjectType getRoadObjectType() {
		return RoadObjectType.NodeJoin;
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
