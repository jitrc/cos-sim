package ru.cos.nissan.parser.trace;

import org.jdom.Element;

import ru.cos.nissan.parser.Parser;
import ru.cos.nissan.parser.geometry.BoundaryNodeGeometry;
import ru.cos.nissan.parser.trace.base.Node;
import ru.cos.nissan.parser.trace.location.LinkLocation;
import ru.cos.nissan.parser.utils.ItemParser;

public class DestinationNode extends Node {
	public static String Name = "DestinationNode";
	private String incomingLinkId = "incomingLinkId";
	
	public BoundaryNodeGeometry geometry;
	public LinkLocation incomingLink;
	
	public DestinationNode(Element e) {
		super(e,Type.DestinationNode);
		
		this.geometry = new BoundaryNodeGeometry(
				e.getChild(BoundaryNodeGeometry.Name,Parser.getCurrentNamespace()));
		this.incomingLink = new LinkLocation(ItemParser.getInteger(e, incomingLinkId));
	}
}
