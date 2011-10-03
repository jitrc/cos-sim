/**
 * 
 */
package ru.cos.sim.agents.tlns.factories;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ru.cos.sim.agents.tlns.LogicalTrafficLight;
import ru.cos.sim.agents.tlns.TrafficLight;
import ru.cos.sim.agents.tlns.TrafficLightNetwork;
import ru.cos.sim.agents.tlns.data.LogicalTrafficLightData;
import ru.cos.sim.agents.tlns.data.PeakAwareTLNData;
import ru.cos.sim.agents.tlns.data.PlacementData;
import ru.cos.sim.agents.tlns.data.RegularTLNData;
import ru.cos.sim.agents.tlns.data.TrafficLightNetworkData;
import ru.cos.sim.engine.RoadNetworkUniverse;
import ru.cos.sim.exceptions.TrafficAgentCreationException;
import ru.cos.sim.road.node.RegularNode;
import ru.cos.sim.road.node.TransitionRule;
import ru.cos.sim.road.node.TurnTRGroup;
import ru.cos.sim.utils.Pair;

/**
 * 
 * @author zroslaw
 */
public class TrafficLightNetworkFactory {

	public static TrafficLightNetwork createTrafficLightNetwork(
			TrafficLightNetworkData tlnData, RoadNetworkUniverse universe) {
		TrafficLightNetwork tln;
		
		switch(tlnData.getTLNType()){
		case RegularTrafficLightNetwork:
			tln = RegularTLNFactory.createRegularTLN((RegularTLNData)tlnData, universe);
			break;
		case PeakAwareTrafficLightNetwork:
			tln = PeakAwareTLNFactory.createPeakAwareTLN((PeakAwareTLNData)tlnData, universe);
			break;
		default:
			throw new TrafficAgentCreationException("Unexpected Traffic Light Network type "+tlnData.getTLNType());
		}
		
		// retrieve and set regular node
		RegularNode regularNode = (RegularNode)universe.getNode(tlnData.getRegularNodeId());
		Map<Integer, TransitionRule> tRules = regularNode.getTRules();
		tln.setNode(regularNode);
		
		// creating logical traffic lights and sets of their child regular traffic light instances
		// and put them on appropriate places (according to placement data)
		Map<Integer, LogicalTrafficLight> logicalTrafficLights = new HashMap<Integer,LogicalTrafficLight>();
		for (LogicalTrafficLightData logicalTLData:tlnData.getTrafficLightsData()){
			LogicalTrafficLight logicalTrafficLight = new LogicalTrafficLight();
			logicalTrafficLight.setId(logicalTLData.getId());
			Set<TrafficLight> trafficLights = new HashSet<TrafficLight>();
			PlacementData placementData = logicalTLData.getPlacement();
			// set simple traffic light instances on the transition rules
			for (Pair<Integer,Float> trPlacement:placementData.getTransitionRules()){
				TrafficLight trafficLight = new TrafficLight();
				TransitionRule tRule = tRules.get(trPlacement.getFirst());
				tRule.putPoint(trafficLight, trPlacement.getSecond());
				trafficLights.add(trafficLight);
			}
			// set traffic lights instances on the turn groups
			for (Integer turnGroupId:placementData.getTrurnTRGroups()){
				TurnTRGroup turnGroup = regularNode.getTurnTRGroup(turnGroupId);
				for (TransitionRule transitionRule:turnGroup.getTransitionRules()){
					TrafficLight trafficLight = new TrafficLight();
					transitionRule.putPoint(trafficLight, 0);
					trafficLights.add(trafficLight);
				}
			}
			logicalTrafficLight.setTrafficLights(trafficLights);
			logicalTrafficLights.put(logicalTrafficLight.getId(),logicalTrafficLight);
		}
		tln.setLogicalTrafficLights(logicalTrafficLights);		
		
		return tln;
	}

	
	
}
