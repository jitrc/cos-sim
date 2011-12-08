package ru.cos.sim.visualizer.traffic.parser.trace.location;


public class NodeLocation extends Location {

	protected int nodeId;
	
	protected NodeLocation() {
		super(Type.Node);
	}
	
	public NodeLocation(int nodeId) {
		super(Type.Node);
		this.nodeId = nodeId;
	}
	
	protected NodeLocation(Type type,int nodeId){
		super(type);
		this.nodeId = nodeId;
	}

	public int getNodeId() {
		return nodeId;
	}
	
	
}
