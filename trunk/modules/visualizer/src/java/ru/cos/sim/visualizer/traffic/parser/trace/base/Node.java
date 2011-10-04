package ru.cos.sim.visualizer.traffic.parser.trace.base;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.Parser;


public class Node extends StaffCollector {
	
	public static String ChapterName = "Nodes";
	public static enum Type {
		CrossRoad,
		DestinationNode,
		SourceNode
	}
	private static enum Fields {
		Name
	}
	protected String name;
	protected Type type;
	
	public Node(Element e,Type type) {
		super(e);
		
		this.type = type;
		this.name = e.getChildText(Fields.Name.name(),Parser.getCurrentNamespace());
	}
	
	public Type getType()
	{
		return type;
	}

	
}
