/**
 * 
 */
package ru.cos.cs.agents.framework.example.synch;

import java.util.List;

import ru.cos.cs.agents.framework.example.SimpleAgent;
import ru.cos.cs.agents.framework.example.SimpleSimulationEngine;

/**
 * 
 * @author zroslaw
 */
public class SynchMain {
	

	public static void main(String[] args) {

		float timeToSimulate = 55.69973f;
		float timeStep = 0.1f;
		
		SimpleSimulationEngine sse = new SimpleSimulationEngine();
		
		// simulate
		sse.init();
		while(timeToSimulate>0){
			timeToSimulate-=timeStep;
			sse.step(timeStep);
		}
		
		// retrieve results
		System.out.println("Time: "+sse.getTime());
		System.out.println("--- Alive agents list ---");
		List<SimpleAgent> simpleAgents = sse.getSimpleAgents();
		for (SimpleAgent agent:simpleAgents){
			System.out.println(agent);
		}
		System.out.println("--- End of alive agents list ---");
	}

}
