/**
 * 
 */
package ru.cos.sim.road.init.data;

import ru.cos.sim.road.node.Node;

/**
 * 
 * @author zroslaw
 */
public abstract class AbstractNodeData {
	
	protected int id;
	
	protected String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract Node.NodeType getType();
	
}
