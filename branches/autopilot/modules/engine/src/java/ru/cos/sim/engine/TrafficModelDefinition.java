/**
 * 
 */
package ru.cos.sim.engine;

import java.util.Set;


import ru.cos.sim.agents.TrafficAgentData;
import ru.cos.sim.meters.data.MeterData;
import ru.cos.sim.parameters.ModelParameters;
import ru.cos.sim.parameters.data.ModelParametersData;
import ru.cos.sim.road.init.data.RoadNetworkData;

/**
 * Definition of traffic model 
 * @author zroslaw
 */
public class TrafficModelDefinition {

	protected RoadNetworkData roadNetworkData;
	
	protected Set<TrafficAgentData> agentsData;
	
	protected Set<MeterData> metersData;

	protected ModelParametersData modelParametersData;
	
	public RoadNetworkData getRoadNetworkData() {
		return roadNetworkData;
	}

	public void setRoadNetworkData(RoadNetworkData roadNetworkData) {
		this.roadNetworkData = roadNetworkData;
	}

	public Set<TrafficAgentData> getAgentsData() {
		return agentsData;
	}

	public void setAgentsData(Set<TrafficAgentData> agentsData) {
		this.agentsData = agentsData;
	}

	public Set<MeterData> getMetersData() {
		return metersData;
	}

	public void setMetersData(Set<MeterData> metersData) {
		this.metersData = metersData;
	}

	public ModelParametersData getModelParametersData() {
		return modelParametersData;
	}
	
	public void setModelParametersData(ModelParametersData modelParametersData) {
		this.modelParametersData = modelParametersData;
	}
}
