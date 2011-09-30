/**
 * 
 */
package ru.cos.sim.driver.composite;

import ru.cos.cs.lengthy.Fork;
import ru.cos.cs.lengthy.Join;
import ru.cos.cs.lengthy.Lengthy;
import ru.cos.sim.driver.AbstractDriver;
import ru.cos.sim.driver.composite.cases.CarFollowingCase;
import ru.cos.sim.driver.composite.cases.ForthcomingNode;
import ru.cos.sim.driver.composite.cases.LaneAlignCase;
import ru.cos.sim.driver.composite.cases.LaneChangeCase;
import ru.cos.sim.driver.composite.cases.RespectQueueCase;
import ru.cos.sim.driver.composite.cases.RouterCase;
import ru.cos.sim.driver.composite.cases.SafetyCase;
import ru.cos.sim.driver.composite.cases.TrafficLightCase;
import ru.cos.sim.driver.composite.framework.ControlCommand;
import ru.cos.sim.driver.composite.framework.RectangleCCRange;
import ru.cos.sim.driver.route.RouteProvider;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.utils.Hand;
import ru.cos.sim.utils.Pair;

/**
 * Composite Driver.
 * @author zroslaw
 */
public class CompositeDriver extends AbstractDriver {
	
	private static float frontVisibleRange = 200.f;
	
	protected CompositeDriverParameters parameters;
	
	protected Perceptor perceptor;

	protected Percepts percepts;
	
	// driver behavior cases
	protected RouterCase routerCase = new RouterCase(this);
	protected CarFollowingCase cfCase = new CarFollowingCase(this);
	protected LaneAlignCase laneAlignCase = new LaneAlignCase(this);
	protected LaneChangeCase laneChangeCase = new LaneChangeCase(this);
	protected SafetyCase safetyCase = new SafetyCase(this);
	protected TrafficLightCase trafficLightCase = new TrafficLightCase(this);
	protected ForthcomingNode forthcomingNodeCase = new ForthcomingNode(this);
	protected RespectQueueCase respectQueueCase = new RespectQueueCase(this);
	
	public CompositeDriver() {
		this.perceptor = new Perceptor(this);
	}

	private boolean isInitialized = false;
	
	/**
	 * Initialize driver cases
	 * @return
	 */
	public void init(){
		cfCase.init(parameters);
		trafficLightCase.init(parameters);
		routerCase.init(vehicle, destinationNodeId);
		forthcomingNodeCase.init(parameters);
		respectQueueCase.init(parameters);
	}
	
	@Override
	public Pair<Float, Hand> drive(float dt) {
		if (!isInitialized) {
			init();
			isInitialized = true;
		}
		
		// perceive data from the road network
		percepts = perceptor.createPercepts(vehicle.getLengthy(), vehicle.getPosition());
		
		/*
		 *  Lets generate road case handlers to do their work
		 */
		// run router
		routerCase.behave(dt);
		RectangleCCRange ccRange;
		ccRange = (RectangleCCRange) safetyCase.behave(dt);
		ccRange = (RectangleCCRange) ccRange.intersect(trafficLightCase.behave(dt));
//		ccRange = (RectangleCCRange) ccRange.intersect(respectQueueCase.behave(dt));
		ccRange = (RectangleCCRange) ccRange.intersect(forthcomingNodeCase.behave(dt));
		ccRange = (RectangleCCRange) ccRange.intersect(laneChangeCase.behave(dt));
		ccRange = (RectangleCCRange) ccRange.intersect(cfCase.behave(dt));
		ccRange = (RectangleCCRange) ccRange.intersect(laneAlignCase.behave(dt));
		
		// calculate final control command value
		ControlCommand cc = ccRange.controlCommand();
		
		float acceleration = cc.getAcceleration();
		Hand turn = cc.getTurn();
		Pair<Float, Hand> result = new Pair<Float, Hand>(acceleration, turn);
		return result;
	}

	@Override
	public Lengthy chooseNextLengthy(Join join) {
		return routerCase.chooseNextLengthy(join);
	}

	@Override
	public Lengthy chooseNextLengthy(Fork fork) {
		return routerCase.chooseNextLengthy(fork);
	}

	public Percepts getPercepts() {
		return percepts;
	}

	@Override
	public final DriverType getDriverType() {
		return DriverType.Composite;
	}

	public CompositeDriverParameters getParameters() {
		return parameters;
	}

	public void setParameters(CompositeDriverParameters parameters) {
		this.parameters = parameters;
	}

	public void setRouteProvider(RouteProvider routeProvider) {
		routerCase.setRouteProvider(routeProvider);
	}
	public RouterCase getRouter() {
		return routerCase;
	}

	public float getFrontVisibleRange() {
		return frontVisibleRange;
	}

	public boolean isTurnSafe(Hand turnHand) {
		return this.safetyCase.getSafeTurns().contains(turnHand);
	}

	/**
	 * Retrieve lane from which driver wants to enter the node.
	 * @return desired incoming lane
	 */
	public Lane getNodesDesiredIncomingLane() {
		return forthcomingNodeCase.getDesiredLane();
	}

}
