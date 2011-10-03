/**
 * 
 */
package ru.cos.cs.agents.framework.example;

import java.util.HashSet;
import java.util.Set;

import ru.cos.cs.agents.framework.AbstractAgent;
import ru.cos.cs.agents.framework.Agent;

/**
 * 
 * @author zroslaw
 */
public class SimpleOrigin extends AbstractAgent {

	protected SimpleUniverse universe;
	private Set<Agent> newbornsData = new HashSet<Agent>();
	private float timer = 0;
	private float bornTimer = 0;
	private float bornPeriod = 5;


	protected SimpleOrigin(SimpleUniverse universe) {
		this.universe=universe;
	}

	/* (non-Javadoc)
	 * @see ru.cos.cs.agents.framework.Agent#act(ru.cos.cs.agents.framework.Percepts, float)
	 */
	@Override
	public void act(float dt) {
		timer+=dt;
		bornTimer+=dt;
		if (timer>100) isAlive=false;
		if (bornTimer>=bornPeriod){
			bornTimer = 0;
			newbornsData.add(new SimpleAgent());
		}
		universe.addNewborns(newbornsData);
		newbornsData = new HashSet<Agent>();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
