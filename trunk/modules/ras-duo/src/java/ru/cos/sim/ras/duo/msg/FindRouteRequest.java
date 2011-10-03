package ru.cos.sim.ras.duo.msg;

public class FindRouteRequest {

	public FindRouteRequest(int sourceLinkId, int destinationNodeId) {
		this.destinationNodeId = destinationNodeId;
		this.sourceLinkId = sourceLinkId;
	}
	
	private final int sourceLinkId;
	public int getSourceLinkId() {
		return sourceLinkId;
	}

	private final int destinationNodeId;
	public int getDestinationNodeId() {
		return destinationNodeId;
	}
}
