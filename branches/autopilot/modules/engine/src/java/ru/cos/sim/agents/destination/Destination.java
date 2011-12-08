/**
 * 
 */
package ru.cos.sim.agents.destination;

import ru.cos.cs.agents.framework.AbstractAgent;
import ru.cos.cs.agents.framework.Agent;
import ru.cos.cs.lengthy.objects.Point;
import ru.cos.sim.agents.TrafficAgent;
import ru.cos.sim.engine.RoadNetworkUniverse;
import ru.cos.sim.road.node.DestinationNode;
import ru.cos.sim.road.node.TransitionRule;
import ru.cos.sim.vehicle.Vehicle;

/**
 * 
 * @author zroslaw
 */
public class Destination extends AbstractAgent implements TrafficAgent {
	
	protected int destinationId;
	
	protected DestinationNode destinationNode;
	
	protected float totalTravelTime = 0;
	
	protected float totalTravelDistance = 0;
	
	protected int arrivedVehiles = 0;
	
	protected float clock = 0;

	@Override
	public void act(float dt) {
		clock+=dt;
		// kill all vehicles that are on the destination node transition rules
		for (TransitionRule tr:destinationNode.geTtRules().values()){
			for (Point point:tr.getPoints()){
				if (point instanceof Agent){
					TrafficAgent agent = ((TrafficAgent)point);
					if (agent.getTrafficAgentType()==TrafficAgentType.Vehicle){
						Vehicle vehicle = (Vehicle)agent;
						arrivedVehiles++;
						totalTravelTime+=vehicle.getTravelTime();
						totalTravelDistance+=vehicle.getTravelDistance();
					}
					agent.kill();
				}
			}
		}
	}

	public int getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(int destinationId) {
		this.destinationId = destinationId;
	}

	public DestinationNode getDestinationNode() {
		return destinationNode;
	}

	public void setDestinationNode(DestinationNode destinationNode) {
		this.destinationNode = destinationNode;
	}

	@Override
	public final TrafficAgentType getTrafficAgentType() {
		return TrafficAgentType.Destination;
	}

	@Override
	public void destroy() {
		// do nothing
	}

	public float getTotalTravelTime() {
		float result = totalTravelTime;
		totalTravelTime = 0;
		return result;
	}

	public int getTotalStops() {
		return 0;
	}

	public long getArrivedVehicles() {
		int result = arrivedVehiles;
		arrivedVehiles = 0;
		return result;
	}

	public float getTotalTravelDistance() {
		float result = totalTravelDistance;
		totalTravelDistance = 0;
		return result;
	}

}
