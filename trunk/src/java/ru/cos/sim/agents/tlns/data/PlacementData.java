/**
 * 
 */
package ru.cos.sim.agents.tlns.data;

import java.util.Set;

import ru.cos.sim.utils.Pair;

/**
 * 
 * @author zroslaw
 */
public class PlacementData {

	private Set<Integer> trurnTRGroups;
	
	private Set<Pair<Integer, Float>> transitionRules;

	public Set<Integer> getTrurnTRGroups() {
		return trurnTRGroups;
	}

	public void setTrurnTRGroups(Set<Integer> trurnTRGroups) {
		this.trurnTRGroups = trurnTRGroups;
	}

	public Set<Pair<Integer, Float>> getTransitionRules() {
		return transitionRules;
	}

	public void setTransitionRules(Set<Pair<Integer, Float>> transitionRules) {
		this.transitionRules = transitionRules;
	}
	
}
