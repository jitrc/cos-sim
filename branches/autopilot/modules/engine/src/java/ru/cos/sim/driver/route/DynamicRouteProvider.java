package ru.cos.sim.driver.route;

import ru.cos.sim.driver.RoadRoute;
import ru.cos.sim.services.UpdatingRouteServiceClient;
import ru.cos.sim.services.ServiceLocator;

public class DynamicRouteProvider extends AbstractRouteProvider {
	public DynamicRouteProvider(float updatePeriod, int destLinkId) {
		super(destLinkId);
		this.updatePeriod = updatePeriod;
	}
	
	private RoadRoute route;
	
	private final float updatePeriod;
	private float updateTimeout;
	
	@Override
	public RoadRoute getCurrentRoute() {
		return route;
	}

	@Override
	public void act(float dt) {
		//Waiting for update time
		if ((updateTimeout -= dt) > 0) {
			return;
		}
		updateTimeout += updatePeriod;
		updateRoute();
	}
	
	protected void updateRoute() {
		route = routeServiceClient.findRoute(getSourceLinkId(), getDestinationNodeId());
	}
}

