/**
 * 
 */
package ru.cos.sim.agents.tlns;

import java.util.Map;

import ru.cos.sim.agents.AbstractRoadAgent;
import ru.cos.sim.road.node.RegularNode;

/**
 * 
 * @author zroslaw
 */
public abstract class AbstractTrafficLightNetwork extends AbstractRoadAgent implements TrafficLightNetwork{

	protected RegularNode node;
	
	protected Map<Integer, LogicalTrafficLight> trafficLights;

	/**
	 * Retrieve type of traffic light network.
	 * @return traffic light network type
	 */
	public abstract TLNType getTLNType();

	/**
	 * Set signals for logical traffic lights.
	 * @param trafficLightSignals map of traffic light signals. Keys are ids of logical traffic lights,
	 * values are signals to be set.
	 */
	protected void setSignals(Map<Integer,TrafficLightSignal> trafficLightSignals) {
		for (Integer logicalTrafficLightId:trafficLightSignals.keySet()){
			LogicalTrafficLight logicalTrafficLight = trafficLights.get(logicalTrafficLightId);
			if (logicalTrafficLight==null) continue;
			logicalTrafficLight.setSignal(trafficLightSignals.get(logicalTrafficLightId));
		}
	}
	
	public RegularNode getNode() {
		return node;
	}

	public void setNode(RegularNode node) {
		this.node = node;
	}

	public Map<Integer, LogicalTrafficLight> getTrafficLights() {
		return trafficLights;
	}

	public void setTrafficLights(Map<Integer, LogicalTrafficLight> trafficLights) {
		this.trafficLights = trafficLights;
	}

	@Override
	public Map<Integer, LogicalTrafficLight> getLogicalTrafficLights() {
		return trafficLights;
	}

	@Override
	public void setLogicalTrafficLights(Map<Integer, LogicalTrafficLight> logicalTrafficLights) {
		this.trafficLights = logicalTrafficLights;
	}

	@Override
	public LogicalTrafficLight getLogicalTrafficLight(int logicalTrafficLightId) {
		return trafficLights.get(logicalTrafficLightId);
	}

	@Override
	public final TrafficAgentType getTrafficAgentType() {
		return TrafficAgentType.TrafficLightNetwork;
	}
	
}
