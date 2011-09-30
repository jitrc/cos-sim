package ru.cos.sim.driver.route;

import ru.cos.sim.driver.RoadRoute;
import ru.cos.sim.services.ServiceLocator;

public class FinalRouteProvider extends AbstractRouteProvider {

	private RoadRoute route;
	
	@Override
	protected void init() {
		route = ServiceLocator.getInstance().getInitialRouteService().findRoute(getSourceLinkId(), getDestinationNodeId());
	}
	
	@Override
	public void act(float dt) {
		
	}

	@Override
	public RoadRoute getCurrentRoute() {
		checkInitialization();
		return route;
	}
}
