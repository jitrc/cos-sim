package ru.cos.cs.agents.framework;

/**
 * Abstract agent of a model. Has its own agentId and abstract type.
 * @author zroslaw
 */
public abstract class AbstractAgent implements Agent{
	
	/**
	 * Agent's agentId
	 */
	protected int agentId;
	
	protected boolean isAlive = true;
	
	/**
	 * Set agents agentId.
	 * @param agentId agents agentId
	 */
	public final void setAgentId(int agentId) {
		this.agentId=agentId;
	}
	
	/**
	 * Get agent's agentId.
	 * @return agent's agentId
	 */
	public final int getAgentId() {
		return agentId;
	}

	@Override
	public final boolean isAlive() {
		return true;
	}

	@Override
	public final void kill() {
		isAlive = false;
	}
	
}
