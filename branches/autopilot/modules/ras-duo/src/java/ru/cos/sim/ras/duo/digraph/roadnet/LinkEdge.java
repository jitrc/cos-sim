package ru.cos.sim.ras.duo.digraph.roadnet;

import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.road.init.data.LinkData;

public class LinkEdge extends Edge {

	public LinkEdge(LinkData link, NodePartVertex start, NodePartVertex end) {
		this.start = start;
		this.end = end;
		
		this.link = link;
		
		start.addOutgoingEdge(this);
		end.addIncomingEdge(this);
	}
	
	private NodePartVertex start, end;
	
	public int getLinkId() {
		return link.getId();
	}
	
	private LinkData link;
	public LinkData getLinkData() {
		return link;
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
		return getIncomingVertex() + "=(" + getLinkId() + ")=>" + getOutgoingVertex();
	}
}
