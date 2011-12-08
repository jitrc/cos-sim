/**
 * 
 */
package ru.cos.sim.road.objects;

import ru.cos.cs.lengthy.impl.objects.PointImpl;
import ru.cos.sim.road.RoadTrajectory;
import ru.cos.sim.utils.Hand;

/**
 * Abstract road object {@link RoadObject}
 * @author zroslaw
 */
public abstract class AbstractRoadObject extends PointImpl implements RoadObject, Cloneable{
	
	protected float shift = 0;
	
	protected float speed = 0;

	@Override
	public float getShift() {
		return shift;
	}
	
	@Override
	public void setShift(float shift){
		this.shift = shift;
	}

	@Override
	public boolean isOnLink() {
		return ((RoadTrajectory)lengthy).getRoadTrajectoryType()==RoadTrajectory.RoadTrajectoryType.Lane;
	}

	@Override
	public boolean isOnNode() {
		return ((RoadTrajectory)lengthy).getRoadTrajectoryType()==RoadTrajectory.RoadTrajectoryType.TransitionRule;
	}

	@Override
	public abstract RoadObjectType getRoadObjectType();

	@Override
	public RoadTrajectory getLengthy() {
		return (RoadTrajectory) super.getLengthy();
	}

	@Override
	public float getSpeed() {
		return speed;
	}

	@Override
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public Hand getShiftHand() {
		return shift==0?null:shift>0?Hand.Right:Hand.Left;
	}
	
}
