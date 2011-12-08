package ru.cos.sim.visualizer.traffic.parser.trace;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.Parser;
import ru.cos.sim.visualizer.traffic.parser.geometry.BoundaryNodeGeometry;
import ru.cos.sim.visualizer.traffic.parser.trace.base.Node;
import ru.cos.sim.visualizer.traffic.parser.trace.location.LinkLocation;
import ru.cos.sim.visualizer.traffic.parser.utils.ItemParser;

public class SourceNode extends Node {

	public static String Name = "OriginNode";
	private String outgoingLinkId = "outgoingLinkId";
	
	public BoundaryNodeGeometry geometry;
	public LinkLocation outgoingLink;
	
	public SourceNode(Element e) {
		super(e,Type.SourceNode);
		
		this.geometry = new BoundaryNodeGeometry(
				e.getChild(BoundaryNodeGeometry.Name,Parser.getCurrentNamespace()));
		this.outgoingLink = new LinkLocation(ItemParser.getInteger(e, outgoingLinkId));
	}

}
