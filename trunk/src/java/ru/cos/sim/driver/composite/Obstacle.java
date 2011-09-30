/**
 * 
 */
package ru.cos.sim.driver.composite;

import ru.cos.sim.road.exceptions.RoadNetworkException;
import ru.cos.sim.road.objects.AbstractRoadObject;
import ru.cos.sim.road.objects.RoadObject;

/**
 * Wrapper for some road object that is an obstacle for a vehicle.
 * @author zroslaw
 */
public class Obstacle extends AbstractRoadObject{

	public enum ObstacleType{
		Vehicle,
		EndOfLane,
		StartOfLane,
		Block
	}
	
	private RoadObject obstacle;
	
	private ObstacleType obstacleType;
	
	public Obstacle(RoadObject roadObject){
		ObstacleType type = getObstacleType(roadObject);
		if (type==null)
			throw new RoadNetworkException("Attempt to create obstacle from an road object that is not an obstacle.");
		this.obstacle = roadObject;
		this.obstacleType = type;
	}
	
	@Override
	public RoadObjectType getRoadObjectType() {
		return RoadObjectType.Obstacle;
	}

	public ObstacleType getObstacleType(){
		return obstacleType;
	}
	
	public RoadObject getObstacle() {
		return obstacle;
	}

	/**
	 * Evaluate road object obstacle type.
	 * Will return null if road object is not an abstacle
	 * @param roadObject
	 * @return
	 */
	public static ObstacleType getObstacleType(RoadObject roadObject){
		RoadObjectType type = roadObject.getRoadObjectType();
		switch(type){
		case Vehicle:
			return ObstacleType.Vehicle;
		case EndOfLane:
			return ObstacleType.EndOfLane;
		case StartOfLane:
			return ObstacleType.StartOfLane;
		case Block:
			return ObstacleType.Block;
		default:
			return null;
		}
	}
	
}
