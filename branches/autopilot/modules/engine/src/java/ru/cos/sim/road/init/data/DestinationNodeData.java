/**
 * 
 */
package ru.cos.sim.road.init.data;

import ru.cos.sim.road.node.Node.NodeType;

/**
 * 
 * @author zroslaw
 */
public class DestinationNodeData extends AbstractNodeData {

	protected int incomingLinkId;
	
	@Override
	public final NodeType getType() {
		return NodeType.DestinationNode;
	}

	public int getIncomingLinkId() {
		return incomingLinkId;
	}

	public void setIncomingLinkId(int incomingLinkId) {
		this.incomingLinkId = incomingLinkId;
	}

}
