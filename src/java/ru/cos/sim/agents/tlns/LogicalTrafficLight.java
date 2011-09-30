/**
 * 
 */
package ru.cos.sim.agents.tlns;

import java.util.Set;

/**
 * 
 * @author zroslaw
 */
public class LogicalTrafficLight {

	protected int id;
	
	protected TrafficLightSignal signal;
	
	protected Set<TrafficLight> trafficLights;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTrafficLights(Set<TrafficLight> trafficLights) {
		this.trafficLights = trafficLights;
	}

	public Set<TrafficLight> getTrafficLights() {
		return trafficLights;
	}

	public void setSignal(TrafficLightSignal trafficLightSignal) {
		this.signal = trafficLightSignal;
		for (TrafficLight trafficLight:trafficLights){
			trafficLight.setSignal(trafficLightSignal);
		}
	}

	public TrafficLightSignal getSignal() {
		return signal;
	}
	
}
