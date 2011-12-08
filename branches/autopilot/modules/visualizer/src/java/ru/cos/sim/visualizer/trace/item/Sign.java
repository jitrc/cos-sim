package ru.cos.sim.visualizer.trace.item;

import ru.cos.sim.visualizer.trace.item.base.LocatableItem;
import ru.cos.sim.visualizer.traffic.parser.trace.staff.Sign.Type;

public class Sign extends LocatableItem {

	protected Type signType;
	protected float speedLimit;
	
	public Sign(ru.cos.sim.visualizer.traffic.parser.trace.staff.Sign sign) {
		super(sign.id(), sign.getLocation(), sign.getSide());
		
		this.signType = sign.getSignType();
		if (signType == Type.SpeedLimitSign){
			this.speedLimit = sign.getSpeedLimit();
		}
	}
	
	public Type getSignType()
	{
		return this.signType;
	}

	public float getSpeedLimit() {
		return speedLimit;
	}
}
