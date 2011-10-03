package ru.cos.nissan.parser.trace.staff;

import org.jdom.Element;

import ru.cos.nissan.parser.trace.base.SideableStaff;
import ru.cos.nissan.parser.trace.location.LaneLocation;

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
