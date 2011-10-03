/**
 * 
 */
package ru.cos.sim.road.init.data;

import java.util.Set;

/**
 * 
 * @author zroslaw
 */
public class TurnTRGroupData {

	private int id;
	
	private String name;
	
	private Set<Integer> transitionRuleIds;

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

	public Set<Integer> getTransitionRuleIds() {
		return transitionRuleIds;
	}

	public void setTransitionRuleIds(Set<Integer> transitionRuleIds) {
		this.transitionRuleIds = transitionRuleIds;
	}
	
}
