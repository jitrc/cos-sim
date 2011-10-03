package ru.cos.nissan.parser.trace;

import org.jdom.Element;

import ru.cos.nissan.parser.Parser;
import ru.cos.nissan.parser.geometry.BoundaryNodeGeometry;
import ru.cos.nissan.parser.trace.base.Node;
import ru.cos.nissan.parser.trace.location.LinkLocation;
import ru.cos.nissan.parser.utils.ItemParser;

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
