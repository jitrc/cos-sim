/**
 * 
 */
package ru.cos.cs.lengthies.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.*;

import ru.cos.cs.lengthy.*;
import ru.cos.cs.lengthy.impl.*;
import ru.cos.cs.lengthy.impl.objects.PointImpl;
import ru.cos.cs.lengthy.objects.*;

/**
 * 
 * @author zroslaw
 */
public class LengthiesTest {

	private LengthiesFactory factory = new LengthiesFactoryImpl();
	RegularLengthy l1,l2,l3;
	Point o1,o2,o3,o4,o5;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		l1 = factory.createLengthy(100.f);
		l2 = factory.createLengthy(100.f);
		l3 = factory.createLengthy(100.f);
		l1.setNext(l2);
		l2.setNext(l3);l2.setPrev(l1);
		l3.setPrev(l2);

		o1 = factory.createObservable();
		l2.putPoint(o1, 50);
		o2 = factory.createObservable();
		l2.putPoint(o2, 50);
		o3 = factory.createObservable();
		l2.putPoint(o3, 75);
		o4 = factory.createObservable();
		l3.putPoint(o4, 10);
		o5 = factory.createObservable();
		l3.putPoint(o5, 90);
	}

	@Test
	public void testPutObservable(){
		assertTrue(o1.getLengthy()==l2);
		assertTrue(o1.getPosition()==50);
		
		assertTrue(o2.getLengthy()==l2);
		assertTrue(o2.getPosition()==50);
	}

	@Test
	public void testRemoveObservable(){
		l2.removePoint(o1);
		assertTrue(o1.getLengthy()==null);
		assertTrue((new Float(Float.NaN)).equals(o1.getPosition()));
	}

	@Test
	public void testObserveForward(){
		// observe first two objects
		List<Observation> observations = l2.observeForward(10, 50, null);
		assertTrue(observations.size()==2);
		Observation observation1 = observations.get(0); 
		Observation observation2 = observations.get(1); 
		assertTrue(observation1.getPoint()==o1 || observation1.getPoint()==o2); 
		assertTrue(observation2.getPoint()==o2 || observation2.getPoint()==o1);
		assertTrue(observation1.getPoint()!=observation2.getPoint());
		assertTrue(observation1.getDistance()==40); 
		assertTrue(observation2.getDistance()==40);
		
		// observe three objects
		observations = l2.observeForward(10, 70, null);
		assertTrue(observations.size()==3);
		observation1 = observations.get(0); 
		observation2 = observations.get(1); 
		Observation observation3 = observations.get(2);
		assertTrue(observation1.getPoint()==o1 || observation1.getPoint()==o2); 
		assertTrue(observation2.getPoint()==o2 || observation2.getPoint()==o1);
		assertTrue(observation1.getPoint()!=observation2.getPoint());
		assertTrue(observation2.getDistance()==40);
		assertTrue(observation1.getDistance()==40);
		assertTrue(observation3.getPoint()==o3); 
		assertTrue(observation3.getDistance()==65);
		
		// observe on next lengthy too
		observations = l2.observeForward(10, 185, null);
		assertTrue(observations.size()==5);
		observation1 = observations.get(2); 
		observation2 = observations.get(3); 
		observation3 = observations.get(4);
		assertTrue(observation1.getPoint()==o3); 
		assertTrue(observation1.getDistance()==65);
		assertTrue(observation2.getPoint()==o4); 
		assertTrue(observation2.getDistance()==100);
		assertTrue(observation3.getPoint()==o5); 
		assertTrue(observation3.getDistance()==180);
		
	}
	
	@Test
	public void testMove(){
		Point point = new PointImpl();
		l2.putPoint(point, 40);
		
		// move a little
		l2.move(point, 10.f, null);
		assertTrue(point.getPosition()==50);
		
		// move on next lengthy forward
		l2.move(point, 70, null);
		assertEquals(20, point.getPosition(),0.0001);
		assertTrue(point.getLengthy()==l3);
		
		// move on prev lengthy backward
		l2.move(point, -70, null);
		assertEquals(50,point.getPosition(),0.0001);
		assertTrue(point.getLengthy()==l2);
	}

	@Test
	public void testObserveBackwad(){
		// observe first two objects
		List<Observation> observations = l3.observeBackward(95, 90, null);
		assertTrue(observations.size()==2);
		Observation observation1 = observations.get(0); 
		Observation observation2 = observations.get(1); 
		assertTrue(observation1.getPoint()==o5); 
		assertTrue(observation1.getDistance()==-5);
		assertTrue(observation2.getPoint()==o4); 
		assertTrue(observation2.getDistance()==-85);
		
		// observe three objects
		observations = l3.observeBackward(95, 190, null);
		assertTrue(observations.size()==5);
		observation1 = observations.get(0); 
		observation2 = observations.get(1); 
		Observation observation3 = observations.get(2);
		Observation observation4 = observations.get(3);
		Observation observation5 = observations.get(4);
		assertTrue(observation1.getPoint()==o5); 
		assertTrue(observation1.getDistance()==-5);
		assertTrue(observation2.getPoint()==o4); 
		assertTrue(observation2.getDistance()==-85);
		assertTrue(observation3.getPoint()==o3); 
		assertTrue(observation3.getDistance()==-120);
		assertTrue(observation4.getPoint()==o1 || observation4.getPoint()==o2); 
		assertTrue(observation5.getPoint()==o2 || observation5.getPoint()==o1);
		assertTrue(observation4.getPoint()!=observation5.getPoint());
		assertTrue(observation4.getDistance()==-145); 
		assertTrue(observation5.getDistance()==-145);
	}
	
}
