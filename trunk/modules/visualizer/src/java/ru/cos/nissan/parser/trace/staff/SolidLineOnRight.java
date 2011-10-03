package ru.cos.nissan.parser.trace.staff;

import org.jdom.Element;

import ru.cos.nissan.parser.trace.base.Staff;
import ru.cos.nissan.parser.trace.location.LaneLocation;
import ru.cos.nissan.parser.utils.ItemParser;

public class SolidLineOnRight extends Staff {

	public static String Name = "SolidLineOnRight";
	
	public static enum Fields {
		LinkId,
		SegmentId,
		LaneId,
		Position,
		Length,
		DesiredLength
	}
	
	protected Float length;
	protected Float desiredLength;
	
	public SolidLineOnRight(Element e,int linkId)
	{
		super();
		this.setType(StaffType.SolidLine);
		this.location = new LaneLocation(linkId,
				ItemParser.getInteger(e, Fields.SegmentId.name()),
				ItemParser.getInteger(e, Fields.LaneId.name()));
		this.length = ItemParser.getFloat(e, Fields.Length.name());
		this.desiredLength = ItemParser.getFloat(e, Fields.DesiredLength.name());
	}
	

}
