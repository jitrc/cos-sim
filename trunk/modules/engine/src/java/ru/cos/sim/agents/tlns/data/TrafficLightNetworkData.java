/**
 * 
 */
package ru.cos.sim.agents.tlns.data;

import java.util.Set;

import ru.cos.sim.agents.TrafficAgent.TrafficAgentType;
import ru.cos.sim.agents.TrafficAgentData;
import ru.cos.sim.agents.tlns.TLNType;

/**
 * 
 * @author zroslaw
 */
public abstract class TrafficLightNetworkData implements TrafficAgentData{
	
	private int regularNodeId;
	
	private Set<LogicalTrafficLightData> trafficLightsData;
	
	public abstract TLNType getTLNType();

	public int getRegularNodeId() {
		return regularNodeId;
	}

	public void setRegularNodeId(int regularNodeId) {
		this.regularNodeId = regularNodeId;
	}

	public Set<LogicalTrafficLightData> getTrafficLightsData() {
		return trafficLightsData;
	}

	public void setTrafficLightsData(Set<LogicalTrafficLightData> trafficLightsData) {
		this.trafficLightsData = trafficLightsData;
	}

	@Override
	public TrafficAgentType getTrafficAgentType() {
		return TrafficAgentType.TrafficLightNetwork;
	}

}
