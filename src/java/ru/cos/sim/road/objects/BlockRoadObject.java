/**
 * 
 */
package ru.cos.sim.road.objects;

/**
 * Block obstacle on the road.
 * @author zroslaw
 */
public class BlockRoadObject extends AbstractRectangleRoadObject {

	@Override
	public RoadObjectType getRoadObjectType() {
		return RoadObjectType.Block;
	}

}
