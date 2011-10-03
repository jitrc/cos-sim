/**
 * 
 */
package ru.cos.sim.road.link;

import ru.cos.sim.road.objects.AbstractRoadObject;
import ru.cos.sim.road.objects.RoadObject;

/**
 * 
 * @author zroslaw
 */
public class StartOfLanePoint extends AbstractRoadObject {

	public StartOfLanePoint(Lane lane) {
		this.lengthy = lane;
		this.position = 0;
	}

	/* (non-Javadoc)
	 * @see road.objects.RoadObject#getRoadObjectType()
	 */
	@Override
	public final RoadObject.RoadObjectType getRoadObjectType() {
		return RoadObject.RoadObjectType.StartOfLane;
	}

}
