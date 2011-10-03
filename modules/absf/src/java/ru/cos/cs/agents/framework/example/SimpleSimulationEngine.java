/**
 * 
 */
package ru.cos.cs.agents.framework.example;

import java.util.List;
import java.util.Vector;

import ru.cos.cs.agents.framework.Agent;
import ru.cos.cs.agents.framework.SimulationEngine;
import ru.cos.cs.agents.framework.impl.IncrementalUIDProvider;

/**
 * 
 * @author zroslaw
 */
public class SimpleSimulationEngine extends SimulationEngine {

	/**
	 * Initialization of concrete model.
	 */
	public void init() {
		universe = new SimpleUniverse();
		uidProvider = new IncrementalUIDProvider();
		
		Agent simpleOrigin = new SimpleOrigin((SimpleUniverse) universe);
		aliveAgents.put(uidProvider.getNextId(), simpleOrigin);
	}

	public List<SimpleAgent> getSimpleAgents(){
		List<SimpleAgent> result = new Vector<SimpleAgent>();
		for(Agent agent:aliveAgents.values()){
			if(agent instanceof SimpleAgent){
				result.add((SimpleAgent) agent);
			}
		}
		return result;
	}
	
}
