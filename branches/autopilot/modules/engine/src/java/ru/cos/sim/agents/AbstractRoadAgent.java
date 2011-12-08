/**
 * 
 */
package ru.cos.sim.agents;

/**
 * Abstract road agent.
 * @author zroslaw
 */
public abstract class AbstractRoadAgent implements TrafficAgent {

	protected int agentId;
	
	protected boolean isAlive = true;
	
	@Override
	public boolean isAlive() {
		return isAlive;
	}

	@Override
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	@Override
	public int getAgentId() {
		return agentId;
	}

	@Override
	public void kill() {
		isAlive = false;
	}

	@Override
	public void destroy() {
		kill();
	}

}
