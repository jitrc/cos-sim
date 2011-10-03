/**
 * 
 */
package ru.cos.sim.agents;


/**
 * Interface for all structures(classes, datas) that defines any agent in the system.
 * @author zroslaw
 */
public interface TrafficAgentData {

	/**
	 * Retrieve road type of the agent
	 * @return agent type
	 */
	public TrafficAgent.TrafficAgentType getTrafficAgentType();
	
}
