/**
 * 
 */
package ru.cos.cs.agents.framework.impl;

import java.util.HashSet;
import java.util.Set;

import ru.cos.cs.agents.framework.Agent;
import ru.cos.cs.agents.framework.Universe;

/**
 * Empty Universe.
 * @author zroslaw
 */
public class EmptyUniverse implements Universe {

	@Override
	public void act(float dt) {
		// do nothing
	}

	@Override
	public Set<Agent> lookupNewborns() {
		return new HashSet<Agent>();
	}

}
