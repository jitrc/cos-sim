/**
 * 
 */
package ru.cos.sim.road.node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Group of transition rules that perform the same turn in the node.
 * @author zroslaw
 */
public class TurnTRGroup {
	
	private int id;
	
	private Map<Integer, TransitionRule> transitionRules = new HashMap<Integer, TransitionRule>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Retrieve set of transition rules that are in this turn group
	 * @return set of transition rules in the group
	 */
	public Set<TransitionRule> getTransitionRules() {
		Set<TransitionRule> trSet = new HashSet<TransitionRule>();
		trSet.addAll(transitionRules.values());
		return trSet;
	}

	public TransitionRule getTransitionRule(int transitionRuleId) {
		return transitionRules.get(transitionRuleId);
	}

	public void setTransitionRules(Map<Integer, TransitionRule> transitionRules) {
		this.transitionRules = transitionRules;
	}
	
	
}
