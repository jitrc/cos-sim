package ru.cos.nissan.parser.trace.staff;

import org.jdom.Element;

import ru.cos.nissan.parser.Parser;
import ru.cos.nissan.parser.trace.base.Staff;
import ru.cos.nissan.parser.trace.location.Location;
import ru.cos.nissan.parser.utils.ItemParser;
import ru.cos.nissan.parser.utils.LocationCreator;

public class Meter extends Staff {
	
	public static String Name = "Meter";
	public static String ChapterName = "Meters";
	
	protected Location location;
	protected String name;
	protected String type;
	
	private static enum Fields {
		id,
		name,
		type,
		Position
	}
	
	public Meter(Element e)
	{
		super(e);
		this.uid = ItemParser.getInteger(e, Fields.id.name());
		this.name = e.getChildText(Fields.name.name(),Parser.getCurrentNamespace());
		this.type = e.getName();
		this.location = LocationCreator.createLocation(e);
		this.location = LocationCreator.setPosition(e, this.location);
		this.staffType = StaffType.Meter;
	}

	public Location getLocation() {
		return location;
	}

	public String getMeterName() {
		return name;
	}

	public String getType() {
		return type;
	}
	
}
