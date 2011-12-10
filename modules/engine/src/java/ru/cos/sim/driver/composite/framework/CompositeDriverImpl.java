/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

import java.util.ArrayList;
import java.util.List;

import ru.cos.cs.lengthy.Fork;
import ru.cos.cs.lengthy.Join;
import ru.cos.cs.lengthy.Lengthy;
import ru.cos.sim.driver.AbstractDriver;
import ru.cos.sim.driver.CompositeDriver;
import ru.cos.sim.driver.DriverRouter;
import ru.cos.sim.driver.composite.CompositeDriverParameters;
import ru.cos.sim.driver.composite.Perceptor;
import ru.cos.sim.driver.composite.Percepts;
import ru.cos.sim.utils.Hand;
import ru.cos.sim.utils.Pair;

/**
 * Composite Driver.
 * @author zroslaw
 * @author Denis
 */
public class CompositeDriverImpl extends AbstractDriver implements CompositeDriver {
	
	private static float frontVisibleRange = 200.f;
	
	protected CompositeDriverParameters parameters;
	
	protected Perceptor perceptor;

	protected Percepts percepts;
	
	private List<BehaviorCase> behaviorCases = new ArrayList<BehaviorCase>();
	private DriverRouter navigator;
	
	public CompositeDriverImpl(DriverRouter navigator) {
		this.perceptor = new Perceptor(this);
		this.navigator = navigator;
	}
	
	@Override
	public void addBehaviorCase(BehaviorCase behaviorCase) {
		behaviorCases.add(behaviorCase);
	}

	@Override
	public void removeBehaviorCase(BehaviorCase behaviorCase) {
		behaviorCases.remove(behaviorCase);
	}

	private boolean isInitialized = false;
	
	/**
	 * Initialize driver cases
	 * @return
	 */
	public void init(){
		if(isInitialized){
			return;
		}
		for (BehaviorCase behaviorCase : behaviorCases) {
			behaviorCase.init(parameters);
		}
		navigator.init(vehicle, destinationNodeId);
		isInitialized = true;
	}
	
	@Override
	public Pair<Float, Hand> drive(float dt) {
		init();
		
		// perceive data from the road network
		percepts = perceptor.createPercepts(vehicle.getLengthy(), vehicle.getPosition());
		navigator.navigate(dt);
		/*
		 *  Lets generate road case handlers to do their work
		 */
		// TODO why roterCase is separated from others?
		//routerCase.behave(dt);
		CCRange ccRange = null;
		for (BehaviorCase behaviorCase : behaviorCases) {
			ccRange = CCRange.calculateResultantRange(ccRange, behaviorCase.behave(dt));
		}
		
		// calculate final control command value
		ControlCommand cc = ccRange.controlCommand();
		
		float acceleration = cc.getAcceleration();
		Hand turn = cc.getTurn();
		Pair<Float, Hand> result = new Pair<Float, Hand>(acceleration, turn);
		return result;
	}

	@Override
	public Lengthy chooseNextLengthy(Join join) {
		return navigator.chooseNextLengthy(join);
	}

	@Override
	public Lengthy chooseNextLengthy(Fork fork) {
		return navigator.chooseNextLengthy(fork);
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

	public float getFrontVisibleRange() {
		return frontVisibleRange;
	}

	@Override
	public DriverRouter getNavigator() {
		return navigator;
	}

	
//	public boolean isTurnSafe(Hand turnHand) {
//		return this.safetyCase.getSafeTurns().contains(turnHand);
//	}

	/**
	 * Retrieve lane from which driver wants to enter the node.
	 * @return desired incoming lane
	 */
//	public Lane getNodesDesiredIncomingLane() {
//		return mandatoryLaneChangingCase.getDesiredLane();
//	}

}
