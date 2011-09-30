/**
 * 
 */
package ru.cos.sim.agents.tlns;

import java.util.Map;

import ru.cos.sim.agents.TrafficAgent;
import ru.cos.sim.road.node.RegularNode;

/**
 * Traffic light network agent.
 * Traffic Light Network it is a system of coordinated traffic lights
 * on a particular road node, that expose coordinated behavior, for example,
 * they all may obey one signal switching schedule.
 * @author zroslaw
 */
public interface TrafficLightNetwork extends TrafficAgent{
	
	public RegularNode getNode();
	
	public void setNode(RegularNode regularNode);

	public Map<Integer, LogicalTrafficLight> getLogicalTrafficLights();
	
	public void setLogicalTrafficLights(Map<Integer, LogicalTrafficLight> logicalTrafficLights);
	
	public LogicalTrafficLight getLogicalTrafficLight(int logicalTrafficLightId);
	
	public TLNType getTLNType();
	
}
