/**
 * 
 */
package ru.cos.sim.agents;

import ru.cos.sim.agents.destination.DestinationData;
import ru.cos.sim.agents.destination.DestinationFactory;
import ru.cos.sim.agents.origin.OriginData;
import ru.cos.sim.agents.origin.OriginFactory;
import ru.cos.sim.agents.tlns.data.TrafficLightNetworkData;
import ru.cos.sim.agents.tlns.factories.TrafficLightNetworkFactory;
import ru.cos.sim.engine.RoadNetworkUniverse;
import ru.cos.sim.exceptions.TrafficSimulationException;
import ru.cos.sim.vehicle.VehicleFactory;
import ru.cos.sim.vehicle.init.data.VehicleData;

/**
 * Traffic simulation factory creates agents instances from its data 
 * @author zroslaw
 */
public class TrafficAgentFactory {

	public static TrafficAgent createAgent(TrafficAgentData agentData, RoadNetworkUniverse universe) {
		TrafficAgent agent;
		
		switch (agentData.getTrafficAgentType()){
		case Origin:
			agent = OriginFactory.createOrigin((OriginData)agentData, universe);
			break;
		case Vehicle:
			agent = VehicleFactory.createVehicle((VehicleData)agentData, universe);
			break;
		case Destination:
			agent = DestinationFactory.createDestination((DestinationData)agentData, universe);
			break;
		case TrafficLightNetwork:
			agent = TrafficLightNetworkFactory.createTrafficLightNetwork((TrafficLightNetworkData)agentData, universe);
			break;
		default:
			throw new TrafficSimulationException("Unexpected agent data type "+agentData.getTrafficAgentType());
		}
		
		return agent;
	}

}
