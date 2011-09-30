/**
 * 
 */
package ru.cos.sim.road.node;

import ru.cos.sim.road.link.Link;

/**
 * Origin node. Point in the road network start their trips.
 * @author zroslaw
 */
public class OriginNode extends Node {
	
	protected Link outgoingLink;

	public OriginNode(int id) {
		super(id);
	}

	@Override
	public final NodeType getNodeType() {
		return NodeType.OriginNode;
	}

	public Link getOutgoingLink() {
		return outgoingLink;
	}

	public void setOutgoingLink(Link outgoingLink) {
		this.outgoingLink = outgoingLink;
	}

}
