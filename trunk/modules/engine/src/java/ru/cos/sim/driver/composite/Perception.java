/**
 * 
 */
package ru.cos.sim.driver.composite;

import ru.cos.cs.lengthy.Observation;
import ru.cos.sim.road.objects.RectangleRoadObject;
import ru.cos.sim.road.objects.RoadObject;
import ru.cos.sim.road.objects.RoadObject.RoadObjectType;

/**
 * Perception of the road object.
 * Perception is essentially an {@linkplain Observation} but treated in way
 * like regular human does, i.e. observation contains distance between objects 
 * point positions, otherwise, perceptions treat distance between points with the
 * knowledge about their lengths and widths.<br>
 * Specifically, for longitudinal observations, percepts object will distract half of 
 * objects lengths to determine distance. 
 * 
 * @author zroslaw
 */
public class Perception {

	private float distance;
	
	private float actualDistance;
	
	private RoadObject roadObject;

	/**
	 * Create perception on the basis of road object and distance to it.
	 * @param distance distance to the object (distance are aware about object's length)
	 * @param roadObject road object instance
	 */
	public Perception(float distance, RoadObject roadObject) {
		this.distance = distance;
		this.roadObject = roadObject;
	}

	/**
	 * Create perception on the basis of observation and observer instances
	 * @param observation observed road object
	 * @param observer road object from which observation performed
	 */
	public Perception(Observation observation, RoadObject observer){
		actualDistance = observation.getDistance();
		this.roadObject = (RoadObject)observation.getPoint();
		float coveredLength = 0.5f*(getRoadObjectLength(this.roadObject)+getRoadObjectLength(observer));
		distance = actualDistance - (actualDistance>0?coveredLength:-coveredLength);
	}
	
	public static float getRoadObjectLength(RoadObject roadObject){
		RoadObjectType type = roadObject.getRoadObjectType();
		switch (type){
		case Block:
		case Vehicle:
			RectangleRoadObject rectangle = (RectangleRoadObject)roadObject;
			return rectangle.getLength();
		default:
			return 0;
		}
	}

	/**
	 * Get distance to percepted road subject to its and observator lengths.
	 * @return distance subject to objects lengths
	 */
	public float getDistance() {
		return distance;
	}

	/**
	 * Get actual distance to road object that was provided by {@linkplain Observation} object.<br>
	 * I.e. it is the distance between points on the lengthies.
	 * @return actual distance
	 */
	public float getActualDistance() {
		return actualDistance;
	}

	public RoadObject getRoadObject() {
		return roadObject;
	}

	public RoadObjectType getRoadObjectType() {
		return roadObject.getRoadObjectType();
	}
	
}
