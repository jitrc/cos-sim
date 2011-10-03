/**
 * 
 */
package ru.cos.cs.agents.framework.example;

import ru.cos.cs.agents.framework.Universe;

/**
 * 
 * @author zroslaw
 */
public class SimpleOriginFactory{

	public static SimpleOrigin createAgent(Universe universe) {
		return new SimpleOrigin((SimpleUniverse) universe);
	}

}
