/**
 * 
 */
package ru.cos.sim.driver.composite.cases;

import static ru.cos.sim.driver.composite.framework.Priority.CarFollowing;
import ru.cos.sim.driver.composite.CompositeDriver;
import ru.cos.sim.driver.composite.Perception;
import ru.cos.sim.driver.composite.Percepts;
import ru.cos.sim.driver.composite.cases.utils.IDMCalculator;
import ru.cos.sim.driver.composite.framework.AbstractBehaviorCase;
import ru.cos.sim.driver.composite.framework.CCRange;
import ru.cos.sim.driver.composite.framework.RectangleCCRange;
import ru.cos.sim.driver.data.IDMDriverParameters;


/**
 * Car following road case.<br>
 * Current implementation employs IDM car-following model.
 * @author zroslaw
 */
public class CarFollowingCase extends AbstractBehaviorCase {
	
	protected IDMCalculator idmCalculator;

	public CarFollowingCase(CompositeDriver driver) {
		super(driver);
		idmCalculator = new IDMCalculator();
	}
	
	// init case
	public void init(IDMDriverParameters parameters){
		idmCalculator.init(parameters);
	}

	@Override
	public CCRange behave(float dt) {
		// retrieve information about front car
		Percepts percepts = driver.getPercepts();
		Perception frontObstacle = percepts.getCurrentPercepts().getFrontObstacle();

		float acceleration = idmCalculator.calculate(driver.getVehicle(),frontObstacle);
		
		RectangleCCRange result = new RectangleCCRange();
		result.getAccelerationRange().setHigher(acceleration);
		result.setPriority(CarFollowing);
		
		return result;
	}

}
