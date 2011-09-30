/**
 * 
 */
package ru.cos.sim.agents.tlns.data;

import java.util.Map;

import ru.cos.sim.agents.tlns.TrafficLightSignal;

/**
 * 
 * @author zroslaw
 */
public class TimePeriodData {

	private float duration;
	
	private Map<Integer, TrafficLightSignal> trafficLightSignals;

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public Map<Integer, TrafficLightSignal> getTrafficLightSignals() {
		return trafficLightSignals;
	}

	public void setTrafficLightSignals(
			Map<Integer, TrafficLightSignal> trafficLightSignals) {
		this.trafficLightSignals = trafficLightSignals;
	}
	
}
