/**
 * 
 */
package ru.cos.cs.agents.framework;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import ru.cos.cs.agents.framework.impl.EmptyUniverse;
import ru.cos.cs.agents.framework.impl.IncrementalUIDProvider;

/**
 * Agent based system simulation engine.
 * @author zroslaw
 */
public class SimulationEngine {
	
	protected float timeStep = 0.1f;

	/**
	 * Map of agents. Map is implemented as TreeMap for fast agent search.
	 */
	protected TreeMap<Integer,Agent> aliveAgents = new TreeMap<Integer,Agent>();  // TOTHINK tree map?
	/**
	 * Universe of simulation.
	 */
	protected Universe universe = new EmptyUniverse();
	/**
	 * Agents unique agentId provider.
	 */
	protected UIDProvider uidProvider = new IncrementalUIDProvider();
	
	/**
	 * Model time
	 */
	protected float time = 0;
	
	/**
	 * Perform step of simulation.
	 * @param dt time step to simulate.
	 */
	public void step(float dt){
		startStep(dt);
		Set<Agent> corpses = new HashSet<Agent>();
		// agents & universe behavior
		for (Agent agent:aliveAgents.values()){
			startAgentAct(agent,dt);
			agent.act(dt);
			finishAgentAct(agent,dt);
			if (!agent.isAlive()) corpses.add(agent);
		}
		universe.act(dt);
		
		// remove dead agents
		for (Agent agent:corpses){
			removeAgent(agent);
		}
		
		// create new agents, if any
		Set<Agent> newborns = universe.lookupNewborns();
		for(Agent newborn:newborns){
			reviveAgent(newborn);
		}
		
		finishStep(dt);
		// increase model time
		time+=dt;
	}
	
	/**
	 * Simulate virtual time period with duration of "time".
	 * @param time duration of time period to simulate
	 */
	public void simulate(float time){
		while (time>0){
			time-=timeStep;
			step(timeStep);
		}
	}

	/**
	 * Method is invoked each time agent finishes its action
	 * @param agent agent that just finishing acting on response of time step
	 * @param dt time step value
	 */
	protected void finishAgentAct(Agent agent, float dt) {}

	/**
	 * Method is invoked each time agent starts its action
	 * @param agent agent that is going to on response of time step
	 * @param dt time step value
	 */
	protected void startAgentAct(Agent agent, float dt) {}

	/**
	 * Method is invoked each time new step of simulation begins
	 * @param dt time step to simulate
	 */
	protected void startStep(float dt) {}

	/**
	 * Method is invoked each time step of simulation finishes
	 * @param dt time step just being simulated
	 */
	protected void finishStep(float dt) {}

	/**
	 * Remove agent from simulation process.
	 * @param corpse instances of dead agent
	 */
	private void removeAgent(Agent corpse) {
		aliveAgents.remove(corpse.getAgentId());
		uidProvider.returnId(corpse.getAgentId());
		corpse.destroy();
	}

	/**
	 * Revive agent, bring its instance to life in simulation process.
	 * @param newborn
	 */
	protected void reviveAgent(Agent newborn) {
		int id=uidProvider.getNextId();
		newborn.setAgentId(id);
		aliveAgents.put(id, newborn);
	}

	/**
	 * Get model time.
	 * @return
	 */
	public float getTime(){
		return time;
	}
}
