package ru.cos.nissan.parser.trace.staff;

import org.jdom.Element;

import ru.cos.nissan.parser.trace.base.Staff;
import ru.cos.nissan.parser.trace.location.LaneLocation;
import ru.cos.nissan.parser.utils.ItemParser;

public class LaneClosure extends Staff {

	public static String Name = "LaneClosure";
	public static String ChapterName = "LaneClosures";
	
	public static enum Fields {
		UID,
		StartTime,
		Duration,
		Length,
		DesiredLength
	}
	
	protected Float length;
	protected Float startTime;
	protected Float duration;
	protected Float desiredLength;
	
	public LaneClosure(Element e) {
		super(e);
		
		this.setType(StaffType.LaneClosure);
		this.location = new LaneLocation(e.getChild("Location"));
		this.length = ItemParser.getFloat(e, Fields.Length.name());
		this.startTime = ItemParser.getFloat(e, Fields.StartTime.name());
		this.duration = ItemParser.getFloat(e, Fields.Duration.name());
		this.desiredLength = ItemParser.getFloat(e, Fields.DesiredLength.name());
	}

	public Float getLength() {
		return length;
	}

	public Float getStartTime() {
		return startTime;
	}

	public Float getDuration() {
		return duration;
	}

	public Float getDesiredLength() {
		return desiredLength;
	}
}
