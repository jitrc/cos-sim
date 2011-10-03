/**
 * 
 */
package ru.cos.sim.agents;

import ru.cos.cs.agents.framework.Agent;

/**
 * Common interface for all agents in the traffic model.
 * @author zroslaw
 */
public interface TrafficAgent extends Agent{

	/**
	 * Types of traffic agents.
	 * @author zroslaw
	 */
	public enum TrafficAgentType{
		Vehicle,
		Origin,
		Destination, 
		TrafficLightNetwork, 
		Meter
	}
	
	public abstract TrafficAgentType getTrafficAgentType();
	
}
