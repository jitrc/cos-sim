package ru.cos.sim.ras.duo;

public class RouteRequest {
	public RouteRequest(int startLinkId, int endNodeId) {
		this.startLinkId = startLinkId;
		this.endNodeId = endNodeId;
	}
	
	private int startLinkId, endNodeId;
	
	public int getStartLinkId() {
		return startLinkId;
	}

	public int getEndNodeId() {
		return endNodeId;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof RouteRequest)) return false;
		RouteRequest otherRequest = (RouteRequest)other;
		return (this.startLinkId == otherRequest.startLinkId &&
				this.endNodeId == otherRequest.endNodeId);
	}
	
	@Override
	public int hashCode() {
		return startLinkId ^ endNodeId;
	}
}
