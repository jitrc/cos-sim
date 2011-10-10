/**
 * 
 */
package ru.cos.sim.driver.composite.cases;

import java.util.List;

import ru.cos.cs.lengthy.Observation;
import ru.cos.sim.driver.composite.CompositeDriver;
import ru.cos.sim.driver.composite.CompositeDriverParameters;
import ru.cos.sim.driver.composite.Perception;
import ru.cos.sim.driver.composite.Percepts;
import ru.cos.sim.driver.composite.TrajectoryPercepts;
import ru.cos.sim.driver.composite.cases.utils.IDMCalculator;
import ru.cos.sim.driver.composite.framework.AbstractBehaviorCase;
import ru.cos.sim.driver.composite.framework.CCRange;
import ru.cos.sim.driver.composite.framework.Priority;
import ru.cos.sim.driver.composite.framework.RectangleCCRange;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.objects.BlockRoadObject;
import ru.cos.sim.road.objects.RoadObject;
import ru.cos.sim.vehicle.Vehicle;


/**
 * In case of front fork is observed,
 * we must respect queue of the vehicles that stays on the lane
 * we choose as desired, we must not go ahead them, instead must to stop
 * and try to join end of the queue.
 * <ol>
		 <li> Retrieve desired incoming lane from the driver.</li>
		 <li> Observe lane backward on the distance of driver front visible range.</li>
		 <li> Start of lanes must be walked around and observation continues on the next lanes.</li>
		 <li> Stop backward observation when we saw this vehicle</li>
		 <li> Distance from end of incoming lane to last vehicle is the queue's length.</li>
		 <li> Stop before queue anyway like it is an obstacle</li>
	</ol>
 * @author zroslaw
 */
public class RespectQueueCase extends AbstractBehaviorCase {
	
	private IDMCalculator idmCalculator;

	public RespectQueueCase(CompositeDriver driver) {
		super(driver);
		this.idmCalculator = new IDMCalculator();
	}
	
	public void init(CompositeDriverParameters parameters){
		idmCalculator.init(parameters);
	}

	@Override
	public CCRange behave(float dt) {
		/**
		 * 1. Retrieve desired incoming lane from the driver.
		 * 2. Observe lane backward on the distance of driver front visible range.
		 * 	Start of lanes must be walked around and observation continues on the next lanes.
		 * 3. Stop backward observation when we saw this vehicle
		 * 4. Distance from end of incoming lane to last vehicle is the queue's length.
		 * 5. Stop before queue anyway like it is an obstacle 
		 */
		Percepts percepts = driver.getPercepts();
		TrajectoryPercepts currentPercepts = percepts.getCurrentPercepts();
		Perception nodeForkPoint = currentPercepts.getFrontFork();
		if (nodeForkPoint==null) return null;
		
		// retrieve desired lane from the driver
		Lane desiredLane = driver.getNodesDesiredIncomingLane();
		if (desiredLane==null) return null;
		
		float queueLength = evalueateQueueLength(desiredLane, desiredLane.getLength(), nodeForkPoint.getActualDistance());

		RectangleCCRange ccRange = null;
		
		if (queueLength>0){
			float distanceToStop = nodeForkPoint.getActualDistance()-queueLength;
			float acceleration = idmCalculator.calculate(driver.getVehicle(), new Perception(distanceToStop, new BlockRoadObject()));
			ccRange = new RectangleCCRange();
			ccRange.getAccelerationRange().setHigher(acceleration);
			ccRange.setPriority(Priority.RespectQueueCase);
		}
		
		return ccRange;
	}

	private float evalueateQueueLength(Lane lane, float fromPosition, float distanceToObserve) {
		float queueLength = 0;
		List<Observation> observations= lane.observeBackward(fromPosition, distanceToObserve, null);
		for (Observation observation:observations){
			RoadObject roadObject = (RoadObject)observation.getPoint();
			switch (roadObject.getRoadObjectType()){
			case Vehicle:
				if (roadObject==driver.getVehicle()) return queueLength;
				queueLength=-observation.getDistance()+((Vehicle)roadObject).getHalfLength();
				break;
			case StartOfLane:
				if (lane.isLeftmost()){
					float queueLengthOnRight = 
						evalueateQueueLength(lane.getRightLane(), lane.getRightPosition(0.01f),distanceToObserve-lane.getLength());
					return lane.getLength()+queueLengthOnRight;
				}
				if (lane.isRightmost()){
					float queueLengthOnLeft = 
						evalueateQueueLength(lane.getLeftLane(), lane.getLeftPosition(0.01f),distanceToObserve-lane.getLength());
					return lane.getLength()+queueLengthOnLeft;
				}
				break;
			}
		}
		return queueLength;
	}

}
