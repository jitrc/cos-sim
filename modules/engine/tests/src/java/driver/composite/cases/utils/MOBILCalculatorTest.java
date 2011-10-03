/**
 * 
 */
package driver.composite.cases.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ru.cos.sim.driver.composite.CompositeDriverParameters;
import ru.cos.sim.driver.composite.Perception;
import ru.cos.sim.driver.composite.cases.utils.MOBILCalculator;
import ru.cos.sim.driver.composite.cases.utils.MOBILDriverParameters;
import ru.cos.sim.vehicle.RegularVehicle;

/**
 * 
 * @author zroslaw
 */
public class MOBILCalculatorTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link ru.cos.sim.driver.composite.cases.utils.MOBILCalculator#calculate(ru.cos.sim.vehicle.Vehicle, ru.cos.sim.driver.composite.Perception, ru.cos.sim.driver.composite.Perception, ru.cos.sim.driver.composite.Perception, ru.cos.sim.driver.composite.Perception)}.
	 */
	@Test
	public void testCalculate() {
		MOBILCalculator calc = new MOBILCalculator();
		MOBILDriverParameters params = new CompositeDriverParameters();
		params.setPoliteness(0.5f);
		params.setAccThreshold(0.2f);
		calc.init(params);
		
		RegularVehicle 
			vehicle = new RegularVehicle(),
			frontVehicle = new RegularVehicle(),
			backVehicle = new RegularVehicle(),
			nextFrontVehicle = new RegularVehicle(),
			nextBackVehicle = new RegularVehicle();
		
		vehicle.setSpeed(10);
		frontVehicle.setSpeed(10);

		// simple car following
		vehicle.setSpeed(10);
		frontVehicle.setSpeed(10);
		float profit = calc.calculate(vehicle, null, new Perception(10, frontVehicle), null, null);
		assertTrue(profit>0);

		// following the car, on another lane car is further
		vehicle.setSpeed(10);
		frontVehicle.setSpeed(10);
		nextFrontVehicle.setSpeed(10);
		profit = calc.calculate(
				vehicle, 
				null, new Perception(20, frontVehicle),
				null, new Perception(40, nextFrontVehicle));
		assertTrue(profit>0);

		// following the car, on another lane car is further and back vehicle on next lane exists
		vehicle.setSpeed(10);
		frontVehicle.setSpeed(10);
		nextFrontVehicle.setSpeed(10);
		nextBackVehicle.setSpeed(2);
		profit = calc.calculate(
				vehicle, 
				null, new Perception(20, frontVehicle),
				new Perception(-20, nextBackVehicle), new Perception(40, nextFrontVehicle));
		assertTrue(profit>0);
		
		// all four vehicles on the road
		vehicle.setSpeed(10);
		frontVehicle.setSpeed(10);
		nextFrontVehicle.setSpeed(10);
		nextBackVehicle.setSpeed(10);
		backVehicle.setSpeed(10);
		profit = calc.calculate(
				vehicle, 
				new Perception(-20, backVehicle), new Perception(20, frontVehicle),
				new Perception(-20, nextBackVehicle), new Perception(40, nextFrontVehicle));
		assertTrue(profit>0);
		
	}

}
