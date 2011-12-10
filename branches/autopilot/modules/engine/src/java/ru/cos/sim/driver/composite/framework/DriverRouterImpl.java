/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

import java.util.List;
import java.util.Set;

import ru.cos.cs.lengthy.Fork;
import ru.cos.cs.lengthy.Join;
import ru.cos.cs.lengthy.Lengthy;
import ru.cos.cs.lengthy.Router;
import ru.cos.sim.driver.CompositeDriver;
import ru.cos.sim.driver.Driver;
import ru.cos.sim.driver.DriverException;
import ru.cos.sim.driver.DriverRouter;
import ru.cos.sim.driver.RoadRoute;
import ru.cos.sim.driver.composite.Perception;
import ru.cos.sim.driver.composite.Percepts;
import ru.cos.sim.driver.composite.TrajectoryPercepts;
import ru.cos.sim.driver.composite.framework.AbstractBehaviorCase;
import ru.cos.sim.driver.composite.framework.CCRange;
import ru.cos.sim.driver.route.RouteProvider;
import ru.cos.sim.road.RoadTrajectory;
import ru.cos.sim.road.RoadTrajectory.RoadTrajectoryType;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.link.Link;
import ru.cos.sim.road.node.Node;
import ru.cos.sim.road.node.NodeFork;
import ru.cos.sim.road.node.NodeForkPoint;
import ru.cos.sim.road.node.RegularNode;
import ru.cos.sim.road.node.TransitionRule;
import ru.cos.sim.road.node.Node.NodeType;
import ru.cos.sim.vehicle.RegularVehicle;
import ru.cos.sim.vehicle.Vehicle;

/**
 * Router case responsible to navigate vehicle while it is moving on the road.
 * @author zroslaw
 */
public class DriverRouterImpl implements DriverRouter{
	
	public void init(Vehicle vehicle) {
		routeProvider.setVehicle(vehicle);;
	}
	
	protected Driver driver;
	protected RouteProvider routeProvider;
	
	protected boolean isInitialized = false;
	protected boolean onLink = false;
	protected boolean onNode = false;

	public DriverRouterImpl(CompositeDriver driver, RouteProvider rProvider) {
		this.driver = driver;
		routeProvider = rProvider;
	}

	@Override
	public void act(float dt) {
		// init case if it is not so yet
		if (!isInitialized) init();
		
		// track route pass, increment currentLinkIndex when vehicle enter in the node
		Vehicle vehicle = driver.getVehicle();
		Lengthy lengthy = vehicle.getLengthy();
		TransitionRule tr = null;
		if(lengthy instanceof TransitionRule){
			tr = (TransitionRule)lengthy;
		}
		if (vehicle.isOnNode()){
			if (onLink){
				if (tr.getNode().getNodeType()!=Node.NodeType.DestinationNode){
					nextLink();
					if (tr.getNextLink().getId() != getCurrentLinkId())
						throw new DriverException("Error during route handling behaviour. Route may be inconsistent.");
				}
			}
			onLink=false;
		}else{
			onLink=true;
		}
		
		if (onLink)
			routeProvider.act(dt);
		
//		Lane desiredLane = null;
//		Percepts percepts = driver.getPercepts();
//		TrajectoryPercepts currentPercepts = percepts.getCurrentPercepts();
//		Perception frontNofrForkPoint = currentPercepts.getFrontFork();
//		if (frontNofrForkPoint==null) return null;
//		
//		NodeForkPoint forkPoint = (NodeForkPoint) frontNofrForkPoint.getRoadObject();
//		NodeFork fork = forkPoint.getNodeFork();
//		Node node = fork.getNode();
//		if (node.getNodeType()!=NodeType.RegularNode) return null;
//		RegularVehicle vehicle = driver.getVehicle();
//		Lane lane = (Lane) fork.getPrev();
//		RegularNode regularNode = (RegularNode) node;
//		Link incomingLink = ((Lane)fork.getPrev()).getLink();
//		int incomingLinkId = incomingLink.getId();
//		int ougtoingLinkId = driver.getNavigator().getNextLinkId(incomingLinkId);
//		
//		Set<Lane> appropriateLanes = regularNode.getAppropriateLanes(incomingLinkId, ougtoingLinkId);
	}

	protected void init(){
		// init route
		// if route is null, this means that route was not specified explicitly
		// and driver must choose route by itself
		RoadTrajectory roadTrajectory = driver.getVehicle().getLengthy();

		// set onLink and onNode flags
		if (roadTrajectory.getRoadTrajectoryType()==RoadTrajectoryType.Lane){
			onLink=true;
		}else{
			onNode=true;
		}
		
		// set init flag
		isInitialized = true;
	}
	
	@Override
	public Lengthy getPrevLengthy(Join join) {
		return null;
	}

	@Override
	public Lengthy getNextLengthy(Fork fork) {
		Lengthy result = null;
		Lane prevLane = (Lane)fork.getPrev();
		NodeFork nodeFork = (NodeFork)fork;
		Node node = nodeFork.getNode();
		
		switch (node.getNodeType()){
			case RegularNode:{
				// in case of regular node we must choose
				// such transition rule that will led us to 
				// appropriate link according to road route
				
				// retrieve next link id
				int nextLinkId = getNextLinkId(prevLane.getLink().getId());
				
				// find and return first appropriate TR
				for (Lengthy lengthy:nodeFork.getForkedLengthies()){
					TransitionRule tRule = (TransitionRule)lengthy;
					if (tRule.getNextLink().getId()==nextLinkId){
						result = tRule;
					}
				}
				break;
			}
			case DestinationNode:{
				// in case of destination just choose first
				// forked lengthy
				result = nodeFork.getForkedLengthies().iterator().next();
				break;
			}
			default:
				throw new DriverException("Unexpected type of observed node: Origin");
		}
		
//		if (result==null)
//			throw new DriverException("Unable to choose next link in the route. " +
//					"Driver's route may be inconsistent.");
		
		return result;
	}

	private void nextLink() {
		getCurrentRoute().next();
	}
	
	private int getCurrentLinkId() {
		return getCurrentRoute().getCurrentLinkId();
	}
	
	/**
	 * Method finds next link id in the route.<br>
	 * Because route may have loops and one link id may encountered several times in the list, 
	 * therefore it must be specified from which place on the route start to search next link id.
	 * @param prevLinkId id of the link that is predecessor of next one in the route
	 * @param startFromIndex index of the link in the route's link list from which start to search,
	 * when it is needed to start from very beginning of the route, then specify any negative value
	 * @return next link id according to the route
	 */
	public int getNextLinkId(int prevLinkId) {
		int result = -1; // -1 if link is not found on the route
		List<Integer> routeLinks = getCurrentRoute().getLinks();
		// find previous link and return next one in the route
		for(int i = 0; i < routeLinks.size(); i++){
			if(routeLinks.get(i)==prevLinkId){
				result = routeLinks.get(i+1);
				break;
			}
		}
		return result;
	}

	protected RoadRoute getCurrentRoute() {
		return routeProvider.getCurrentRoute();
	}
}
