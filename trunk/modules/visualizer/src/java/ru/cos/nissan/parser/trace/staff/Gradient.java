package ru.cos.nissan.parser.trace.staff;

import org.jdom.Element;

import ru.cos.nissan.parser.trace.base.Staff;
import ru.cos.nissan.parser.trace.location.Location;
import ru.cos.nissan.parser.utils.ItemParser;

public class Gradient extends Staff {
	
	public static String Name = "Gradient";
	
	public static enum Fields {
		Value,
		StartPosition,
		EndPosition,
		DesiredLength
	}
	
	protected Float value;
	protected Float startPosition;
	protected Float endPosition;
	protected Float desiredLength;
	
	public Gradient(Element e, Location loc)
	{
		super();
		this.setType(StaffType.Gradient);
		this.staffType = StaffType.Gradient;
		this.location = loc;
		this.value = ItemParser.getFloat(e, Fields.Value.name());
		this.startPosition = ItemParser.getFloat(e, Fields.StartPosition.name());
		this.endPosition = ItemParser.getFloat(e, Fields.EndPosition.name());
		this.desiredLength = ItemParser.getFloat(e, Fields.DesiredLength.name());
	}

	public Float getValue() {
		return value;
	}

	public Float getStartPosition() {
		return startPosition;
	}

	public Float getEndPosition() {
		return endPosition;
	}

	public Float getDesiredLength() {
		return desiredLength;
	}

	
}
