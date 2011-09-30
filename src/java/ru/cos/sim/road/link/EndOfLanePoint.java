/**
 * 
 */
package ru.cos.sim.road.link;

import ru.cos.sim.road.objects.AbstractRoadObject;
import ru.cos.sim.road.objects.RoadObject;

/**
 * Point of the end of the lane. Such point is observed on the end of lanes which have no continue.
 * @author zroslaw
 */
public class EndOfLanePoint extends AbstractRoadObject {

	/**
	 * Constructor
	 * @param lane lane of which this point is end
	 */
	public EndOfLanePoint(Lane lane) {
		this.lengthy = lane;
		this.position = lane.getLength();
	}

	@Override
	public final RoadObject.RoadObjectType getRoadObjectType() {
		return RoadObject.RoadObjectType.EndOfLane;
	}

}
