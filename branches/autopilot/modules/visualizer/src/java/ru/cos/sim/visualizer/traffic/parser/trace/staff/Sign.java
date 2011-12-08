package ru.cos.sim.visualizer.traffic.parser.trace.staff;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.trace.base.SideableStaff;
import ru.cos.sim.visualizer.traffic.parser.trace.location.Location;
import ru.cos.sim.visualizer.traffic.parser.utils.ItemParser;

public class Sign extends SideableStaff {

	public static String ChapterName = "RoadSigns";

	
	protected static enum Fields {
		position,
		SignType,
		speedLimit
	}
	
	public static enum Type {
		SpeedLimitSign,
		NoSpeedLimitSign
	}
	
	protected Type signType;
	protected Float speedLimit;
	
	public Sign(Element e, Location loc, Type signType){
		super(e);
		
		this.location = loc;
		this.setType(StaffType.Sign);
		this.location.setPosition(ItemParser.getFloat(e, Fields.position.name()));
		this.signType = signType;
		if (signType == Type.SpeedLimitSign) {
			this.speedLimit = ItemParser.getFloat(e, Fields.speedLimit.name());
		}
	}

	public Type getSignType() {
		return signType;
	}

	public Float getSpeedLimit() {
		return speedLimit;
	}
}
