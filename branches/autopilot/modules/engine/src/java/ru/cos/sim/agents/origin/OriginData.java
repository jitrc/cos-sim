/**
 * 
 */
package ru.cos.sim.agents.origin;

import java.util.List;

import ru.cos.sim.agents.TrafficAgentData;
import ru.cos.sim.agents.TrafficAgent.TrafficAgentType;


/**
 * Data about origin agent.
 * @author zroslaw
 */
public class OriginData implements TrafficAgentData{	

	/**
	 * Id of an origin node on which this origin agent will be placed.
	 */
	private int originNodeId;
	
	/**
	 * List of origin's time periods data.
	 */
	private List <OriginPeriodData> timePeriods;

	public int getOriginNodeId() {
		return originNodeId;
	}

	public void setOriginNodeId(int originNodeId) {
		this.originNodeId = originNodeId;
	}

	public List<OriginPeriodData> getTimePeriods() {
		return timePeriods;
	}

	public void setTimePeriods(List<OriginPeriodData> timePeriods) {
		this.timePeriods = timePeriods;
	}

	@Override
	public final TrafficAgentType getTrafficAgentType() {
		return TrafficAgentType.Origin;
	}
	
}
