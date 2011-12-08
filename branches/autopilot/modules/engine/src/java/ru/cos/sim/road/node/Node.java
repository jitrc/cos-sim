/**
 * 
 */
package ru.cos.sim.road.node;

/**
 * Abstract node of the road network.
 * @author zroslaw
 */
public abstract class Node{
	
	/**
	 * Road node type
	 * @author zroslaw
	 */
	public enum NodeType{
		/**
		 * Regular road node that connects incoming and 
		 * outgoing links by means of transition rules.
		 */
		RegularNode,
		/**
		 * Origin road node. It is the point in which 
		 * vehicles appears in the road network.
		 */
		OriginNode,
		/**
		 * Destination node.  It is the point where vehicles 
		 * disappears, when they reach destination point.
		 */
		DestinationNode
	}
	
	/**
	 * Node agentId.
	 */
	protected int id;
	
	/**
	 * Get node type
	 * @return node type
	 */
	public abstract NodeType getNodeType();
	
	/**
	 * Node constructor
	 * @param agentId unique node agentId
	 */
	public Node(int id){
		this.id = id;
	}

	/**
	 * Get node agentId
	 * @return node agentId
	 */
	public int getId() {
		return id;
	}
	
}
