package ru.cos.cs.agents.framework;

import java.util.Set;

/**
 * Simulated Universe.
 * @author zroslaw
 */
public interface Universe {
	
	/**
	 * Simple universe behavior consists in incrementing its time.
	 * @param dt time step
	 */
	public void act(float dt);

	/**
	 * Lookup newborns in the Universe.
	 * Method return data about new agents that were born on this step and will be revived on next one.
	 * @return set of AgentData instances for each new agent
	 */
	public Set<Agent> lookupNewborns();
	
}
