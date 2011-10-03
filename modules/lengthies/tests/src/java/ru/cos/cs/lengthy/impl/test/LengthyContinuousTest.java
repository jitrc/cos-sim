/**
 * 
 */
package ru.cos.cs.lengthy.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ru.cos.cs.lengthy.Lengthy;
import ru.cos.cs.lengthy.Observation;
import ru.cos.cs.lengthy.RegularLengthy;
import ru.cos.cs.lengthy.impl.LengthiesFactory;
import ru.cos.cs.lengthy.impl.LengthiesFactoryImpl;
import ru.cos.cs.lengthy.objects.continuous.Continuous;
import ru.cos.cs.lengthy.objects.continuous.ContinuousPoint;

/**
 * 
 * @author zroslaw
 */
public class LengthyContinuousTest {

	private LengthiesFactory factory = new LengthiesFactoryImpl();
	Continuous c1,c2,c3;
	RegularLengthy l1,l2,l3;
	
	/**
	 * @throws java.lang.Exceptiong
	 */
	@Before
	public void setUp() throws Exception {
		l1 = factory.createLengthy(100.f);
		l2 = factory.createLengthy(100.f);
		l3 = factory.createLengthy(100.f);
		l1.setNext(l2);
		l2.setNext(l3);l2.setPrev(l1);
		l3.setPrev(l2);
		
		c1 = factory.createContinuous(70);
		l2.putContinuous(c1, 50, null);
	}

	/**
	 * Test method for {@link ru.cos.cs.lengthy.Lengthy#putContinuous(ru.cos.cs.lengthy.objects.continuous.Continuous, float, ru.cos.cs.lengthy.Router)}.
	 */
	@Test
	public void testPutContinuous() {
		ContinuousPoint backPoint = c1.getBackPoint();
		ContinuousPoint frontPoint =	c1.getFrontPoint();
		List<Lengthy> occupiedLengthies = (List<Lengthy>)c1.getOccupiedLengthies();

		assertTrue(backPoint.getLengthy()==l2);
		assertTrue(frontPoint.getLengthy()==l3);
		assertTrue(backPoint.getContinuous()==c1);
		assertTrue(frontPoint.getContinuous()==c1);
		assertEquals(backPoint.getPosition(),50,0.001);
		assertEquals(frontPoint.getPosition(),20,0.001);
		
		assertTrue(occupiedLengthies.size()==2);
		assertTrue(occupiedLengthies.get(0)==l2);
		assertTrue(occupiedLengthies.get(1)==l3);
		
		
		List<Observation> observations = l2.observeForward(40, 95, null);
		assertTrue(observations.size()==2);
		Observation backPointObs = observations.get(0);	
		Observation frontPointObs = observations.get(1);	
		assertTrue(backPointObs.getPoint()==backPoint);
		assertTrue(frontPointObs.getPoint()==frontPoint);
		
		Set<ContinuousPoint> cPoints = l2.observeContinuous(60);
		assertTrue(cPoints.size()==1);
		ContinuousPoint cPoint = cPoints.iterator().next();
		assertTrue(cPoint.getContinuous()==c1);
		assertEquals(cPoint.getPositionOnContinuous(),10,0.001);
		
		cPoints = l3.observeContinuous(10);
		assertTrue(cPoints.size()==1);
		cPoint = cPoints.iterator().next();
		assertTrue(cPoint.getContinuous()==c1);
		assertTrue(cPoint.getLengthy()==l3);
		assertEquals(cPoint.getPositionOnContinuous(),60,0.001);
	}


	/**
	 * Test method for {@link ru.cos.cs.lengthy.Lengthy#removeContinuous(ru.cos.cs.lengthy.objects.continuous.Continuous)}.
	 */
	@Test
	public void testRemoveContinuous() {
		l2.removeContinuous(c1);
		ContinuousPoint backPoint = c1.getBackPoint();
		ContinuousPoint frontPoint =	c1.getFrontPoint();
		List<Lengthy> occupiedLengthies = (List<Lengthy>)c1.getOccupiedLengthies();
		assertTrue(backPoint.getLengthy()==null);
		assertTrue(new Float(backPoint.getPosition()).equals(Float.NaN));
		assertTrue(frontPoint.getLengthy()==null);
		assertTrue(new Float(frontPoint.getPosition()).equals(Float.NaN));
		assertTrue(occupiedLengthies.size()==0);
		
		List<Observation> observations = l2.observeForward(40, 55, null);
		assertTrue(observations.size()==0);

		Set<ContinuousPoint> cPoints = l2.observeContinuous(60);
		assertTrue(cPoints.size()==0);
		
	}
	
	/**
	 * Test method for {@link ru.cos.cs.lengthy.Lengthy#putContinuousFromEndPoint(ru.cos.cs.lengthy.objects.continuous.Continuous, float, ru.cos.cs.lengthy.Router)}.
	 */
	@Test
	public void testPutContinuousFromEndPoint() {
		l2.removeContinuous(c1);
		l3.putContinuousFromEndPoint(c1, 20, null);
		testPutContinuous();
	}
	
	@Test
	public void testMoveContinuous(){
		l2.move(c1, 40, null);
		
		assertTrue(c1.getBackPoint().getLengthy()==l2);
		assertEquals(c1.getBackPoint().getPosition(),90,0.001);
		assertTrue(c1.getFrontPoint().getLengthy()==l3);
		assertEquals(c1.getFrontPoint().getPosition(),60,0.001);
		
		Set<ContinuousPoint> contObservations = l3.observeContinuous(10);
		assertTrue(contObservations.size()==1);
		assertEquals(contObservations.iterator().next().getPositionOnContinuous(),20,0.0001);
		
		List<Observation> observations = l2.observeForward(80, 100, null);
		assertTrue(observations.size()==2);
		Observation o1 = observations.get(0),
		o2 = observations.get(1);
		assertTrue(o1.getPoint()==c1.getBackPoint());
		assertEquals(o1.getDistance(), 10, 0.0001);
		assertTrue(o2.getPoint()==c1.getFrontPoint());
		assertEquals(o2.getDistance(), 80, 0.0001);
	}

}
