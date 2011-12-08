/**
 * 
 */
package ru.cos.cs.agents.framework;

/**
 * Agent in the agent-based simulation system.
 * @author zroslaw
 */
public interface Agent {
	
	/**
	 * Agent behavior for next time step dt.
	 * Method must change both agent and universe states.
	 * @param dt time step
	 */
	public void act(float dt);
	
	/**
	 * Method should return false when agent has completed his life cycle.
	 * @return alive status
	 */
	public boolean isAlive();
	
	/**
	 * Set agent's agentId
	 * @param id 
	 */
	void setAgentId(int id);
	
	/**
	 * Get agent's agentId
	 * @return
	 */
	public int getAgentId();

	/**
	 * Kill the agent.<br>
	 * It will no longer acts in the system as agent,
	 * but still will be presented in the universe as agent's corpse.
	 */
	public void kill();
	
	/**
	 * Destroy the agent.<br>
	 * Remove all relations between the agent and universe.<br>
	 * I.e. agent instance will be completely removed from universe.
	 */
	public void destroy();
	
}
