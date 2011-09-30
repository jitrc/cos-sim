/**
 * 
 */
package ru.cos.sim.road.init.data;

import java.util.Map;

import ru.cos.sim.road.node.Node.NodeType;

/**
 * 
 * @author zroslaw
 */
public class RegularNodeData extends AbstractNodeData {
	
	protected Map<Integer,TransitionRuleData> transitionRules;
	
	protected Map<Integer, TurnTRGroupData> turnTRGroups;

	public Map<Integer, TransitionRuleData> getTransitionRules() {
		return transitionRules;
	}

	public void setTransitionRules(Map<Integer, TransitionRuleData> transitionRules) {
		this.transitionRules = transitionRules;
	}

	public Map<Integer, TurnTRGroupData> getTurnTRGroups() {
		return turnTRGroups;
	}

	public void setTurnTRGroups(Map<Integer, TurnTRGroupData> turnTRGroups) {
		this.turnTRGroups = turnTRGroups;
	}

	@Override
	public final NodeType getType() {
		return NodeType.RegularNode;
	}

}
