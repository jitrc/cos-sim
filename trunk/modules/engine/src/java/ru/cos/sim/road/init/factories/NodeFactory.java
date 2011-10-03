/**
 * 
 */
package ru.cos.sim.road.init.factories;

import java.util.HashMap;
import java.util.Map;

import ru.cos.sim.road.init.data.AbstractNodeData;
import ru.cos.sim.road.init.data.DestinationNodeData;
import ru.cos.sim.road.init.data.OriginNodeData;
import ru.cos.sim.road.init.data.RegularNodeData;
import ru.cos.sim.road.init.data.TransitionRuleData;
import ru.cos.sim.road.init.data.TurnTRGroupData;
import ru.cos.sim.road.init.factories.exceptions.RoadNetworkFactoryException;
import ru.cos.sim.road.node.DestinationNode;
import ru.cos.sim.road.node.Node;
import ru.cos.sim.road.node.OriginNode;
import ru.cos.sim.road.node.RegularNode;
import ru.cos.sim.road.node.TransitionRule;
import ru.cos.sim.road.node.TurnTRGroup;

/**
 * 
 * @author zroslaw
 */
public class NodeFactory {

	public static Node createNode(AbstractNodeData nodeData) {
		Node node = null;
		
		switch (nodeData.getType()){
		case RegularNode:{
			RegularNodeData regularNodeData = (RegularNodeData) nodeData;
			RegularNode regularNode =new RegularNode(nodeData.getId());
			node = regularNode;
			// create transition rule instances
			Map<Integer,TransitionRule> tRules = new HashMap<Integer, TransitionRule>();
			for (TransitionRuleData trData:regularNodeData.getTransitionRules().values()){
				TransitionRule tr = TransitionRuleFactory.createTransitionRule(trData);
				tr.setNode(node);
				tRules.put(tr.getId(), tr);
			}
			regularNode.setTRules(tRules);
			// create Turn Transition Rule Groups
			if (regularNodeData.getTurnTRGroups()!=null){
				Map<Integer,TurnTRGroup> turnGroups = new HashMap<Integer, TurnTRGroup>();
				for (TurnTRGroupData turnGroupData:regularNodeData.getTurnTRGroups().values()){
					TurnTRGroup turnTRGroup = new TurnTRGroup();
					turnTRGroup.setId(turnGroupData.getId());
					Map<Integer,TransitionRule> groupTRules = new HashMap<Integer,TransitionRule>();
					for (Integer transitionRuleId:turnGroupData.getTransitionRuleIds()){
						TransitionRule transitionRule = tRules.get(transitionRuleId);
						if (transitionRuleId==null)
							throw new RoadNetworkFactoryException("TurnTRGroup definition with id "+turnTRGroup.getId()+
									" has link to transition rule "+transitionRuleId+" that is not in the node");
						groupTRules.put(transitionRuleId,transitionRule);
					}
					turnTRGroup.setTransitionRules(groupTRules);
					turnGroups.put(turnTRGroup.getId(), turnTRGroup);
				}
				regularNode.setTurnTRGroups(turnGroups);
			}
			
			break;
		}
		case OriginNode:{
			OriginNodeData originNodeData = (OriginNodeData) nodeData;
			OriginNode originNode = new OriginNode(originNodeData.getId());
			node = originNode;
			break;
		}
		case DestinationNode:{
			DestinationNodeData originNodeData = (DestinationNodeData) nodeData;
			DestinationNode destinationNode = new DestinationNode(originNodeData.getId());
			node = destinationNode;
			break;
		}
		default: throw new RoadNetworkFactoryException("Unknown type of the node "+nodeData.getType());
		}
		
		return node;
	}

}
