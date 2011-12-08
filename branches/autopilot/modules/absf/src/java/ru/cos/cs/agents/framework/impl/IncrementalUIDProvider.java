/**
 * 
 */
package ru.cos.cs.agents.framework.impl;

import ru.cos.cs.agents.framework.UIDProvider;

/**
 * Simple implementation of {@link UIDProvider}
 * @author zroslaw
 */
public class IncrementalUIDProvider implements UIDProvider {

	/**
	 * UID counter
	 */
	private int counter = 0;
	
	/* (non-Javadoc)
	 * @see ru.cos.cs.agents.framework.AgentUIDSuplier#getNextId()
	 */
	@Override
	public synchronized int getNextId() {
		return counter++;
	}

	/* (non-Javadoc)
	 * @see ru.cos.cs.agents.framework.AgentUIDSuplier#returnId(long)
	 */
	@Override
	public void returnId(int id) {
		// do nothing
	}

}
