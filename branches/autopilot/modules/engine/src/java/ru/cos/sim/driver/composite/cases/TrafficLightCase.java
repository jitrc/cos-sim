/**
 * 
 */
package ru.cos.sim.driver.composite.cases;

import java.util.List;

import ru.cos.sim.agents.tlns.TrafficLight;
import ru.cos.sim.agents.tlns.TrafficLightSignal;
import ru.cos.sim.driver.CompositeDriver;
import ru.cos.sim.driver.composite.CompositeDriverParameters;
import ru.cos.sim.driver.composite.Perception;
import ru.cos.sim.driver.composite.Percepts;
import ru.cos.sim.driver.composite.TrajectoryPercepts;
import ru.cos.sim.driver.composite.cases.utils.IDMCalculator;
import ru.cos.sim.driver.composite.framework.AbstractBehaviorCase;
import ru.cos.sim.driver.composite.framework.CCRange;
import ru.cos.sim.driver.composite.framework.Priority;
import ru.cos.sim.driver.composite.framework.RectangleCCRange;

/**
 * Reaction on traffic light.
 * @author zroslaw
 */
public class TrafficLightCase extends AbstractBehaviorCase {
	
	private IDMCalculator idmCalculator;

	public TrafficLightCase(CompositeDriver driver) {
		super(driver);
		this.idmCalculator = new IDMCalculator();
	}
	
	public void init(CompositeDriverParameters params){
		idmCalculator.init(params);
	}

	@Override
	public CCRange behave(float dt) {
		Percepts percepts = driver.getPercepts();
		TrajectoryPercepts lanePercepts = percepts.getCurrentPercepts();

		Perception frontActiveTrafficLight = 
			getFirstActiveTrafficLight(lanePercepts.getFrontTrafficLights());
		
		if (frontActiveTrafficLight==null) return null;

		RectangleCCRange ccRange = new RectangleCCRange();
		float maxAcceleration = idmCalculator
			.calculate(driver.getVehicle(), frontActiveTrafficLight);
		ccRange.getAccelerationRange().setHigher(maxAcceleration);
		ccRange.setPriority(Priority.TrafficLightCase);
		
		return ccRange;
	}

	private Perception getFirstActiveTrafficLight(List<Perception> trafficLights){
		for (Perception perception:trafficLights){
			TrafficLight trafficLight = (TrafficLight) perception.getRoadObject();
			if (trafficLight.getSignal()==TrafficLightSignal.Red ||
				trafficLight.getSignal()==TrafficLightSignal.Yellow)
				return perception;
		}
		return null;
	}
	
}
