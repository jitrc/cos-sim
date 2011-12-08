package vehicle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ru.cos.cs.lengthy.Fork;
import ru.cos.cs.lengthy.Join;
import ru.cos.cs.lengthy.Lengthy;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.link.Segment;
import ru.cos.sim.road.link.TrapeziumSegment;
import ru.cos.sim.utils.Hand;
import ru.cos.sim.utils.Pair;
import ru.cos.sim.vehicle.AbstractVehicle;
import ru.cos.sim.vehicle.Vehicle;

public class AbstractVehicleTest {

	@Test
	public void testActKinematics() {

		Lane lane = new Lane(0, 100);
		
		Vehicle vehicle = new ProbeVehicle();
		lane.putPoint(vehicle, 0);
		
		int stepsNumber = 10000;
		float stepLength = 0.001f;
		for(int i=0;i<stepsNumber;i++){
			vehicle.act(stepLength);
		}
		
		float simulationTime = stepLength*stepsNumber;
		assertEquals(
				simulationTime*simulationTime*ProbeVehicle.acceleration/2, // (a*t^2)/2
				vehicle.getPosition(),
				0.01);		// precision

		assertEquals(
				simulationTime*ProbeVehicle.acceleration, // a*t
				vehicle.getSpeed(),
				0.01);		// precision
	}
	
	@Test
	public void testActLaneChanging(){

		ProbeVehicle vehicle = new ProbeVehicle();

		// Create segment with two lanes
		Lane lane0 = new Lane(0, 100),
			lane1 = new Lane(1, 100);
		Segment segment = new TrapeziumSegment(0);
		segment.setLanes(new Lane[]{lane0,lane1});
		lane0.setSegment(segment);lane1.setSegment(segment);
		
		// put vehicle on left lane and try to change lane to the right
		lane0.putPoint(vehicle, 50);
		vehicle.laneChangeDesire = Hand.Right;
		vehicle.act(0.0001f);
		assertTrue(vehicle.getLengthy()==lane1);
		
		// try to change lane to the left
		lane0.setWidth(2.f);
		lane1.setWidth(2.f);
		vehicle.laneChangeDesire=Hand.Left;
		vehicle.act(0.1f);
		assertTrue(vehicle.getShift()<0);
		
	}

}

/**
 * Probe vehicle class to test AbstractVehicle functionality.
 * @author zroslaw
 */
class ProbeVehicle extends AbstractVehicle{

	public static float MASS = 2000.f;
	public static float acceleration = 1.f;
	
	public Hand laneChangeDesire = null;
	
	/**
	 * 
	 */
	public ProbeVehicle() {
	}

	@Override
	public Lengthy chooseNextLengthy(Join join) {
		return join.getJoinedLengthies().iterator().next();
	}

	@Override
	public Lengthy chooseNextLengthy(Fork fork) {
		return fork.getForkedLengthies().iterator().next();
	}

	@Override
	protected Pair<Float, Hand> drive(float dt) {
		// constant acceleration
		return new Pair<Float, Hand>(ProbeVehicle.acceleration,laneChangeDesire);
	}

	@Override
	public VehicleType getVehicleType() {
		return null;
	}
	
}
