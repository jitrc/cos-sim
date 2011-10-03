package ru.cos.sim.ras.duo.services;

import java.util.List;

import ru.cos.sim.driver.RoadRoute;
import ru.cos.sim.ras.duo.msg.AsyncResponse;
import ru.cos.sim.ras.duo.msg.FindRouteRequest;
import ru.cos.sim.ras.duo.msg.FindRouteResponse;
import ru.cos.sim.ras.duo.msg.AsyncResponse.Status;
import ru.cos.sim.services.UpdatingRouteServiceClient;

public class DuoRouteServiceClient implements UpdatingRouteServiceClient {

	public DuoRouteServiceClient(DuoSolutionService service) {
		this.service = service;
	}
	
	private final DuoSolutionService service;

	private FindRouteRequest lastRequest;
	private AsyncResponse<FindRouteResponse> lastResponse;
	
	@Override
	public RoadRoute findRoute(int sourceLinkId, int destinationNodeId) {
		RoadRoute result = null;
		
		if (isLastRequestFit(sourceLinkId, destinationNodeId) && lastResponse != null) {
			switch (lastResponse.getStatus()) {
			case Pending:
				return null;
			case Done:
				result = lastResponse.getResponseData().getRoute(); break;
			}
		}

		cancelLastRequest();
		issueNewRequest(sourceLinkId, destinationNodeId);
		return isResultFit(result, sourceLinkId, destinationNodeId) 
				? trimRoute(result, sourceLinkId) 
				: null;
	}
	
	protected void cancelLastRequest() {
		if (lastResponse != null)
			lastResponse.cancel();
	}
	
	protected void issueNewRequest(int sourceLinkId, int destinationNodeId) {
		lastRequest = new FindRouteRequest(sourceLinkId, destinationNodeId);
		lastResponse = service.getMessageBus().findRoute(lastRequest);
	}
	
	protected boolean isLastResponseDone() {
		return lastRequest != null && lastResponse != null
			&& lastResponse.getStatus() == Status.Done;
	}
	
	protected boolean isLastRequestFit(int sourceLinkId, int destinationNodeId) {
		return lastRequest != null 
			&& lastRequest.getDestinationNodeId() == destinationNodeId;
	}
	
	protected boolean isResultFit(RoadRoute result, int sourceLinkId, int destinationNodeId) {
		return result != null 
			&& result.getLinks().contains(sourceLinkId);
	}
	
	protected RoadRoute trimRoute(RoadRoute route, int startLinkId) {
		if (route.getLinks().size() > 0) {
			if (route.getLinks().get(0) != startLinkId) {
				List<Integer> links = route.getLinks();
				route = new RoadRoute();
				route.setLinks(links.subList(links.indexOf(startLinkId), links.size()));
			}
		}
		return route;
	}
}
