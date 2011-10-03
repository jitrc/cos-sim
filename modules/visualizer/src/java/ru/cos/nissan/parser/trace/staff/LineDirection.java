package ru.cos.nissan.parser.trace.staff;

import org.jdom.Element;

import ru.cos.nissan.parser.Parser;
import ru.cos.nissan.parser.trace.base.Staff;
import ru.cos.nissan.parser.trace.location.LaneLocation;

public class LineDirection extends Staff {
	public static String name = "LaneDirectionProperties";
	
	public static enum DirectionType {
		Right,
		Left,
		Middle,
		RightLeft,
		RightMiddle,
		LeftMiddle,
		RightMiddleLeft,
		None
	}
	
	private static enum Fields {
		Straight,
		Left,
		Right
	}
	
	public DirectionType directionType;
	
	public LineDirection(Element e, LaneLocation loc)
	{
		super();
		
		this.setType(StaffType.LineDirection);
		this.setLocation(loc);
		boolean right = e.getChild(Fields.Right.name() , Parser.getCurrentNamespace()) != null;
		boolean left = e.getChild(Fields.Left.name() , Parser.getCurrentNamespace()) != null;
		boolean middle = e.getChild(Fields.Straight.name(), Parser.getCurrentNamespace()) != null;
		
		generateDirectionType(right, middle, left);
	}
	
	private void generateDirectionType(boolean right, boolean middle, boolean left){
		if (right && left && middle) directionType = DirectionType.RightMiddleLeft;
		if (right && left && !middle) directionType = DirectionType.RightLeft;
		if (right && middle && !left) directionType = DirectionType.RightMiddle;
		if (left && middle && !right) directionType = DirectionType.LeftMiddle;
		if (right && !left && !middle) directionType = DirectionType.Right;
		if (left && !right && !middle) directionType = DirectionType.Left;
		if (middle  && !left && !right) directionType = DirectionType.Middle;
		if (!middle  && !left && !right) directionType = DirectionType.None;
	}
}
