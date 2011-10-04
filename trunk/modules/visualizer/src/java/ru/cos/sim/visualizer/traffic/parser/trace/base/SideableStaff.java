package ru.cos.sim.visualizer.traffic.parser.trace.base;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.Parser;
import ru.cos.sim.visualizer.traffic.parser.utils.ItemParser;

public class SideableStaff extends Staff {

	private static enum Fields {
		side
	}
	
	public static enum Side {
		Left,
		Right
	}
	
	protected Side side;
	
	public SideableStaff() {
		super();
	}

	public SideableStaff(Element e) {
		super(e);
		side = Side.valueOf(e.getChildText(Fields.side.name(),Parser.getCurrentNamespace()));
	}

	public SideableStaff(int uid) {
		super(uid);

	}
	public Side getSide() {
		return side;
	}

}
