/**
 * 
 */
package ru.cos.sim.road.objects;

/**
 * Stop line in the intersection.
 * @author zroslaw
 */
public class StopLine extends AbstractRoadObject {

	@Override
	public RoadObjectType getRoadObjectType() {
		return RoadObjectType.StopLine;
	}

}
