/**
 * 
 */
package ru.cos.cs.agents.framework.example;

import ru.cos.cs.agents.framework.AbstractAgent;



/**
 * 
 * @author zroslaw
 */
public class SimpleAgent extends AbstractAgent{

	// internal state
	private float desiredDS;
	private float v = 1;
	
	// external state 
	private float x = 0;
	
	/* (non-Javadoc)
	 * @see ru.cos.cs.agents.framework.Agent#act(ru.cos.cs.agents.framework.Percepts, float)
	 */
	@Override
	public void act(float dt) {
		if (x > 100) isAlive=false;
		desiredDS = dt*v;
		
		x+=desiredDS;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return the desiredDS
	 */
	public float getDesiredDS() {
		return desiredDS;
	}

	@Override
	public String toString() {
		return "SA-"+agentId+":("+x+","+v+")";
	}

	@Override
	public void destroy() {
		// do nothing
	}
	
}
