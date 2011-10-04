package ru.cos.sim.visualizer.traffic.parser.trace.staff;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.trace.base.SideableStaff;
import ru.cos.sim.visualizer.traffic.parser.trace.location.LaneLocation;

public class Beacon extends SideableStaff {
	
	public static String Name = "Beacon";
	public static String ChapterName = "Beacons";
	
	public Beacon(Element e)
	{
		super(e);
		this.setType(StaffType.Beacon);
		this.location = new LaneLocation(e.getChild("Location"));
	}

}
