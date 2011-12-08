package driver.composite.framework;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ru.cos.sim.driver.DriverException;
import ru.cos.sim.driver.composite.framework.FloatInterval;
import ru.cos.sim.driver.composite.framework.HandRange;
import ru.cos.sim.driver.composite.framework.Priority;
import ru.cos.sim.driver.composite.framework.RectangleCCRange;

public class CompositeFrameworkTest {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Simplest test.
	 */
	@Test
	public void test(){
//		RectangleCCRange range1 = new RectangleCCRange();
//		RectangleCCRange range2 = new RectangleCCRange();
//		range1.setPriority(Priority.Lowest);
//		range1.setAccelerationRange(new FloatInterval(10, 20));
//		range2.setAccelerationRange(new FloatInterval(15, 25));
//		range2.setPriority(Priority.CarFollowing);
//		
//	
//		RectangleCCRange resultRange;
//		boolean expetionThowed = false;
//		try{
//			resultRange = (RectangleCCRange) range1.intersect(range2);
//		}catch(DriverException ex){
//			expetionThowed = true;
//		}
//		assertTrue(expetionThowed);
//		
//		resultRange = (RectangleCCRange) range2.intersect(range1);
//		FloatInterval resultInterval = resultRange.getAccelerationRange();
//		assertEquals(resultInterval.getLowest(),15,0);
//		assertEquals(resultInterval.getHigher(),20,0);
//		
//		assertTrue(resultRange.getTurnRange().equals(new HandRange()));
	}
	
}
