/**
 * 
 */
package ru.cos.sim.vehicle;

import static ru.cos.sim.utils.Hand.Left;
import static ru.cos.sim.utils.Hand.Right;
import ru.cos.cs.lengthy.Router;
import ru.cos.sim.exceptions.TrafficSimulationException;
import ru.cos.sim.road.RoadTrajectory;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.link.Segment;
import ru.cos.sim.road.objects.AbstractRectangleRoadObject;
import ru.cos.sim.road.objects.RoadObject;
import ru.cos.sim.services.ReportingServiceClient;
import ru.cos.sim.services.ServiceLocator;
import ru.cos.sim.utils.Hand;
import ru.cos.sim.utils.Pair;

/**
 * Abstract vehicle implementation.
 * @author zroslaw
 */
public abstract class AbstractVehicle extends AbstractRectangleRoadObject implements Vehicle, Router{
	
	public static float laneChangeSpeed = 1.f; // 1m/s

	/**
	 * Agent agentId
	 */
	protected int id;
	
	/**
	 * Unique vehicle id.
	 * For example could be its plate number.
	 */
	protected String vehicleId;
	
	/**
	 * Vehcile's class
	 */
	protected VehicleClass vehicleClass;
	
	/**
	 * Vehicle's travel time
	 */
	protected float travelTime;
	
	/**
	 * Vehicle's travel distance
	 */
	protected float travelDistance;
	
	/**
	 * Agent alive status.
	 */
	protected boolean isAlive = true;	
	
	/**
	 * Reporting vehicle parameters
	 */
	private ReportingServiceClient reporter;
	
	protected ReportingServiceClient getReporter() {
		if (reporter == null)
			reporter = ServiceLocator.getInstance().createReportingServiceClient();
		return reporter;
	}
	
	/** 
	 * React on current acceleration and current wheel turn.
	 * This method implements vehicles physics.
	 * @see model.agents.Agent#act(float)
	 */
	@Override
	public final void act(float dt) {
		// all lengthies of the road network must be instances of RoadTrajectory
		RoadTrajectory roadTrajectory = (RoadTrajectory) lengthy;
		
		// let something to drive vehicle
		Pair<Float,Hand> controlCommand = this.drive(dt);
		float acceleration = controlCommand.getFirst();
		Hand laneChangeDesire = controlCommand.getSecond();
		
		// react on traction force
		float distance = acceleration*dt*dt/2+speed*dt;
		distance = distance<0?0:distance;
		speed += dt*acceleration;
		speed=speed<0?0:speed;
		roadTrajectory.move(this, distance, this);
		roadTrajectory = (RoadTrajectory) lengthy;
		
		// react on lane change desire
		if (laneChangeDesire==Left){
			shift-=laneChangeSpeed*dt;
		}else if (laneChangeDesire==Right){
			shift+=laneChangeSpeed*dt;
		}
		
		// 
		if (roadTrajectory.isLane()){
			Lane lane = (Lane)roadTrajectory;
			Segment segment = lane.getSegment();
			if (shift<-roadTrajectory.getWidth()/2){
//			if (laneChangeDesire==Left){
				if (lane.getIndex()==0) 
					throw new TrafficSimulationException("Vehicle "+id+" moved out from the road to the left side");
				Lane leftLane = segment.getLanes()[lane.getIndex()-1];
				float appropriatePosition = segment.calculateAdjacentPosition(lane.getIndex(), position, Left);
				lane.removePoint(this);
				leftLane.putPoint(this, appropriatePosition);
				shift = leftLane.getWidth()/2;
			}else if (shift>roadTrajectory.getWidth()/2){
//			}else if (laneChangeDesire==Right){
				if (lane.isRightmost())
					throw new TrafficSimulationException("Vehicle "+id+" moved out from the road to the right side");
				Lane rightLane = segment.getLanes()[lane.getIndex()+1];
				float appropriatePosition = segment.calculateAdjacentPosition(lane.getIndex(), position, Right);
				lane.removePoint(this);
				rightLane.putPoint(this, appropriatePosition);
				shift = -rightLane.getWidth()/2;
			}
		}

		// count travel time and distance
		travelTime+=dt;
		travelDistance+=distance;
		
		// report on the vehicle
		getReporter().reportParameters(this);
	}

	/**
	 * Drive the vehicle. 
	 * Set appropriate traction and lane change desire values.
	 * @param dt time step value
	 */
	protected abstract Pair<Float,Hand> drive(float dt);

	@Override
	public final RoadObject.RoadObjectType getRoadObjectType() {
		return RoadObject.RoadObjectType.Vehicle;
	}
	
	@Override
	public boolean isAlive() {
		return isAlive ;
	}

	@Override
	public void setAgentId(int id) {
		this.id = id;
	}
	
	@Override
	public int getAgentId() {
		return id;
	}

	@Override
	public void kill() {
		isAlive = false;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	@Override
	public final TrafficAgentType getTrafficAgentType() {
		return TrafficAgentType.Vehicle;
	}

	@Override
	public VehicleClass getVehicleClass() {
		return vehicleClass;
	}
	
	/**
	 * Set vehicle's class
	 * @param vehicleClass vehicle class to set
	 */
	public void setVehicleClass(VehicleClass vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	@Override
	public float getTravelTime() {
		return travelTime;
	}

	@Override
	public float getTravelDistance() {
		return travelDistance;
	}

	@Override
	public float getHalfLength() {
		return getLength()/2.0f;
	}

	@Override
	public void destroy() {
		kill();
		lengthy.removePoint(this);
	}
	
}
