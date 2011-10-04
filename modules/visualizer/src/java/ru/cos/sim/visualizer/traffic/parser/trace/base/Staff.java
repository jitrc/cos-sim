package ru.cos.sim.visualizer.traffic.parser.trace.base;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.base.Entity;
import ru.cos.sim.visualizer.traffic.parser.trace.location.Location;

public abstract class Staff extends Entity {

	public static enum StaffType  {
		Sign,
		Meter,
		Beacon,
		Detector,
		LaneClosure,
		Gradient,
		SolidLine,
		TrafficLightNetwork,
		Background,
		LineDirection
	}
	
	protected StaffType staffType;
	protected Location location;
	
	public Staff(Element e)
	{
		super(e);
	}
	
	public Staff()
	{
		super();
	}
	
	public Staff(int uid) {
		super(uid);
	}
	
	protected void setType(StaffType type)
	{
		this.staffType = type;
	}
	
	public StaffType getStaffType()
	{
		return staffType;
	}
	
	protected void setLocation(Location location) {
		this.location = location;
	}

	public Location getLocation()
	{
		return location;
	}
	
}
