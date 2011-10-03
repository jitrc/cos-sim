package ru.cos.nissan.parser.utils;

import org.jdom.Element;

import ru.cos.nissan.parser.Parser;
import ru.cos.nissan.parser.trace.location.GlobalLocation;
import ru.cos.nissan.parser.trace.location.LaneLocation;
import ru.cos.nissan.parser.trace.location.LinkLocation;
import ru.cos.nissan.parser.trace.location.Location;
import ru.cos.nissan.parser.trace.location.SegmentLocation;

public class LocationCreator {
	private static enum Fields {
		position,
		linkId,
		segmentId,
		laneId
	}
	public static Location createLocation(Element e)
	{
		Location location;

		if (e.getChild(Fields.linkId.name(),Parser.getCurrentNamespace()) == null) {
			location = new GlobalLocation();
			return location;
		}
		
		Integer link = ItemParser.getInteger(e, Fields.linkId.name());
		if (link == null){
			location = new GlobalLocation();
			return location;
		}
		
		Integer segment = ItemParser.getInteger(e, Fields.segmentId.name());
		if (segment == null) {
			location = new LinkLocation(link);
			return location;
		}
		
		Integer lane = ItemParser.getInteger(e, Fields.laneId.name());
		if (lane == null) {
			location = new SegmentLocation(segment, link);
			return location;
		}
		
		location = new LaneLocation(link, segment, lane);
		
		
		return location;
	}
	
	public static Location setPosition(Element e , Location location) {
		if (e.getChild(Fields.position.name() , Parser.getCurrentNamespace()) == null) return location;
		Float pos = ItemParser.getFloat(e, Fields.position.name());
		if (pos == null) {
			location.setPosition(0);
		} else {
			location.setPosition(pos.floatValue());
		}
		
		return location;
	}
}
