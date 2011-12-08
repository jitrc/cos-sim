/**
 * 
 */
package ru.cos.sim.communication.dto;

import ru.cos.sim.agents.tlns.TrafficLight;
import ru.cos.sim.agents.tlns.TrafficLightSignal;
import ru.cos.sim.road.init.data.NodeLocationData;
import ru.cos.sim.road.node.TransitionRule;

/**
 * Traffic light data transfer object.
 * @author zroslaw
 */
public class TrafficLightDTO extends AbstractDTO {

	protected NodeLocationData nodeLocation;
	
	protected TrafficLightSignal signal;
	
	public TrafficLightDTO() {
	}
	
	public TrafficLightDTO(TrafficLight trafficLight) {
		this.nodeLocation = new NodeLocationData((TransitionRule) trafficLight.getLengthy());
		this.signal = trafficLight.getSignal();
		this.nodeLocation.setPosition(trafficLight.getPosition());
	}

	public TrafficLightSignal getSignal() {
		return signal;
	}
	public void setSignal(TrafficLightSignal signal) {
		this.signal = signal;
	}
	@Override
	public final DTOType getDynamicObjectType() {
		return DTOType.TrafficLightDTO;
	}

	public long getNodeId() {
		return nodeLocation.getNodeId();
	}

	public int getTransitionRuleId() {
		return nodeLocation.getTransitionRuleId();
	}

	public float getPosition() {
		return nodeLocation.getPosition();
	}
}
