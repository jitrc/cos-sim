/**
 * 
 */
package integration;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ru.cos.sim.driver.DumbDriver;
import ru.cos.sim.engine.TrafficSimulationEngine;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.link.Link;
import ru.cos.sim.road.link.Segment;
import ru.cos.sim.road.link.TrapeziumSegment;
import ru.cos.sim.road.node.Node;
import ru.cos.sim.road.node.NodeFork;
import ru.cos.sim.road.node.NodeJoin;
import ru.cos.sim.road.node.RegularNode;
import ru.cos.sim.road.node.TransitionRule;
import ru.cos.sim.vehicle.RegularVehicle;

/**
 * 
 * @author zroslaw
 */
public class SimplestTest {
	Lane lane;
	Link link;
	Segment segment;
	Node node;
	NodeFork nFork;
	TransitionRule tr;
	NodeJoin nJoin;
	
	@Before
	public void init(){
		// Create simplest road network with one link and one node
		lane = new Lane(0, 100);
		segment = new TrapeziumSegment(0);
		segment.setLanes(new Lane[]{lane});
		link = new Link(100);
		Map<Integer, Segment> segments = new HashMap<Integer, Segment>();
		segments.put(segment.getId(), segment);
		link.setSegments(segments);

		node = new RegularNode(0);
		
		nFork = new NodeFork();
		nFork.setNode(node);
		
		tr = new TransitionRule(0,20);
		tr.setNode(node);
		
		nJoin = new NodeJoin();
		nJoin.setNode(node);
		
		lane.setNext(nFork);
		nFork.setPrev(lane);
		nFork.forkTo(tr);
		tr.setNext(nJoin);
		nJoin.setNext(lane);
	}

	@Test
	public void simplestPass(){
		TrafficSimulationEngine se = new TrafficSimulationEngine();
		
		RegularVehicle vehicle = new RegularVehicle();
		
		DumbDriver driver = new DumbDriver();
		vehicle.setDriver(driver);
		driver.setVehicle(vehicle);
		
		lane.putPoint(vehicle, 0);
		
//		se.reviveAgent(vehicle);
		
		float simulationTime = 60.f;
		float timeStep = 0.1f;
		for (float time=0;time<simulationTime;time+=timeStep){
			se.step(timeStep);
		}

//		assertEquals(0,vehicle.getPosition(),0.000001);
//		assertEquals(0,vehicle.getSpeed(),0.000001);
//		
	}
	
}
