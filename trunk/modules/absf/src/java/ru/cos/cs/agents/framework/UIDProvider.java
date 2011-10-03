/**
 * 
 */
package ru.cos.cs.agents.framework;

/**
 * Provider of agent's unique identifiers.
 * @author zroslaw
 */
public interface UIDProvider {
	
	/**
	 * Provides next unique agentId.
	 * @return next unigue agent agentId
	 */
	public int getNextId();
	
	/**
	 * Return agentId of dead agent. Such agentId can be reused in future.
	 * @param agentId to be reused
	 */
	public void returnId(int id);
	
}
