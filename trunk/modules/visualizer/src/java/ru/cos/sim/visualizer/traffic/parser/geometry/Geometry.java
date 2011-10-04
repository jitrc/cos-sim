package ru.cos.sim.visualizer.traffic.parser.geometry;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.Parser;

public class Geometry {
	public static String Name = "TrapeziumGeometry";
	
	protected ArrayList<Side> sides;
	
	public Geometry(Element e)
	{
		this.sides = new ArrayList<Side>();
		
		this.sides.add(new Side(e.getChild(Side.Names.LeftSide.name(),Parser.getCurrentNamespace())));
		this.sides.add(new Side(e.getChild(Side.Names.RightSide.name(),Parser.getCurrentNamespace())));
	}

	public ArrayList<Side> getSides() {
		return sides;
	}
	
}
