/**
 * 
 */
package ru.cos.cs.agents.framework.example.asycnh;

import java.util.List;
import java.util.Vector;

import ru.cos.cs.agents.framework.asynch.AsynchSimulationEngine;
import ru.cos.cs.agents.framework.asynch.Message;
import ru.cos.cs.agents.framework.example.SimpleAgent;
import ru.cos.cs.agents.framework.example.SimpleSimulationEngine;
import ru.cos.cs.agents.framework.example.asycnh.messages.AgentsListMessage;

/**
 * 
 * @author zroslaw
 */
public class SimpleAsynchSE extends AsynchSimulationEngine<SimpleSimulationEngine> {

	public SimpleAsynchSE() {
		super(new SimpleSimulationEngine());
	}
	
	public void init(){
		simulationEngine.init();
	}

	@Override
	protected void processIncomingMessage(Message message) {
		System.out.println(simulationEngine.getTime()+": Process message...");
		List<SimpleAgent> simpleAgents = simulationEngine.getSimpleAgents();
		List<SimpleAgent> resultList = new Vector<SimpleAgent>();
		resultList.addAll(simpleAgents);
		outQueue.add(new AgentsListMessage(resultList));
	}

}
