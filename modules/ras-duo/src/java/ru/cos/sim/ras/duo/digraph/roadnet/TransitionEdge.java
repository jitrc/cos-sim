package ru.cos.sim.ras.duo.digraph.roadnet;

import ru.cos.sim.agents.tlns.data.TrafficLightNetworkData;
import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.road.init.data.TransitionRuleData;

public class TransitionEdge extends Edge {
	public TransitionEdge(int nodeId, TransitionRuleData transition, TrafficLightNetworkData lightsNetwork, int lightsId, NodePartVertex start, NodePartVertex end) {
		this.nodeId = nodeId;
		this.transition = transition;
		this.lightsNetwork = lightsNetwork;
		this.lightsId = lightsId;
		this.start = start;
		this.end = end;
		
		start.addOutgoingEdge(this);
		end.addIncomingEdge(this);
	}
	
	private NodePartVertex start, end;
	
	private int nodeId;
	public int getNodeId() {
		return nodeId;
	}
	
	private TransitionRuleData transition;
	public TransitionRuleData getTransitionData() {
		return transition;
	}
	
	private TrafficLightNetworkData lightsNetwork;
	public TrafficLightNetworkData getLightsNetworkData() {
		return lightsNetwork;
	}
	
	private int lightsId;
	public int getLightsId() {
		return lightsId;
	}
	
	@Override
	public NodePartVertex getIncomingVertex() {
		return start;
	}

	@Override
	public NodePartVertex getOutgoingVertex() {
		return end;
	}

	@Override
	public String toString() {
		return getIncomingVertex() + "=(T)=>" + getOutgoingVertex();
	}
}
