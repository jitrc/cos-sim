/**
 * 
 */
package ru.cos.cs.agents.framework.example.asycnh.messages;

import java.util.List;

import ru.cos.cs.agents.framework.asynch.Message;
import ru.cos.cs.agents.framework.example.SimpleAgent;

/**
 * 
 * @author zroslaw
 */
public class AgentsListMessage implements Message {

	private List<SimpleAgent> agents;
	
	public AgentsListMessage(List<SimpleAgent> agents){
		this.agents=agents;
	}

	/**
	 * @return the agents
	 */
	public List<SimpleAgent> getAgents() {
		return agents;
	}
	
}
