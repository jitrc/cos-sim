/**
 * 
 */
package ru.cos.sim.road.init.data;

import ru.cos.sim.road.node.Node.NodeType;

/**
 * 
 * @author zroslaw
 */
public class OriginNodeData extends AbstractNodeData {

	protected int outgoingLinkId;

	/* (non-Javadoc)
	 * @see road.init.data.AbstractNodeData#getType()
	 */
	@Override
	public final NodeType getType() {
		return NodeType.OriginNode;
	}

	public int getOutgoingLinkId() {
		return outgoingLinkId;
	}

	public void setOutgoingLinkId(int outgoingLinkId) {
		this.outgoingLinkId = outgoingLinkId;
	}

}
