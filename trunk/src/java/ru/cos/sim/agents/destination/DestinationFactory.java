/**
 * 
 */
package ru.cos.sim.agents.destination;

import ru.cos.sim.engine.RoadNetworkUniverse;
import ru.cos.sim.road.node.DestinationNode;

/**
 * Destination factory create instances of destination agents from data about them.
 * @author zroslaw
 */
public class DestinationFactory{

	/**
	 * Create destination agent from data about it.
	 * @param destinationData data structure that defines destination
	 * @param universe universe where to create destination
	 * @return destination instance
	 */
	public static Destination createDestination(DestinationData destinationData, RoadNetworkUniverse universe) {
		
		Destination destination = new Destination();
		destination.setDestinationId(destinationData.getDestinationId());
		DestinationNode destinationNode = (DestinationNode)
			universe.getNode(destinationData.getDestinationNodeId());
		destination.setDestinationNode(destinationNode);
		
		return destination;
	}

}
