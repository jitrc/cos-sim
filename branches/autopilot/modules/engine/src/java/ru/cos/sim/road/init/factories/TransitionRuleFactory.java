/**
 * 
 */
package ru.cos.sim.road.init.factories;

import ru.cos.sim.road.init.data.TransitionRuleData;
import ru.cos.sim.road.node.TransitionRule;

/**
 * 
 * @author zroslaw
 */
public class TransitionRuleFactory {

	public static TransitionRule createTransitionRule(TransitionRuleData trData) {
		TransitionRule tr = new TransitionRule(trData.getId(), trData.getLength());
		tr.setWidth(trData.getWidth());
		return tr;
	}

}
