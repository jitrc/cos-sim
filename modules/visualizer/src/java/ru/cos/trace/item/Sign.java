package ru.cos.trace.item;

import ru.cos.nissan.parser.trace.staff.Sign.Type;
import ru.cos.trace.item.base.LocatableItem;

public class Sign extends LocatableItem {

	protected Type signType;
	protected float speedLimit;
	
	public Sign(ru.cos.nissan.parser.trace.staff.Sign sign) {
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
