package ru.cos.sim.visualizer.traffic.parser.trace.staff;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.trace.base.Staff;
import ru.cos.sim.visualizer.traffic.parser.trace.location.LaneLocation;

public class Detector extends Staff {

	public static String Name = "DetectorPost";
	public static String ChapterName = "DetectorPosts";
	
	public Detector(Element e)
	{
		super(e);
		this.location = new LaneLocation(e.getChild("Location"));
	}
}
