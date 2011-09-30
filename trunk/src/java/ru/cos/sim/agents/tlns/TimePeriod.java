/**
 * 
 */
package ru.cos.sim.agents.tlns;

import java.util.Map;

/**
 * Period of time when all traffic lights have constant signals.
 * @author zroslaw
 */
public class TimePeriod {
	
	private float periodDuration;
	
	private float periodTimer;
	
	private Map<Integer, TrafficLightSignal> trafficLightSignals;

	public void act(float dt) {
		periodTimer+=dt;
	}

	public boolean isActive() {
		return periodTimer<periodDuration;
	}

	public void start() {
		periodTimer = 0;
	}

	public Map<Integer, TrafficLightSignal> getTrafficLightSignals() {
		return trafficLightSignals;
	}

	public void setPeriodDuration(float periodDuration) {
		this.periodDuration = periodDuration;
	}

	public void setTrafficLightSignals(
			Map<Integer, TrafficLightSignal> trafficLightSignals) {
		this.trafficLightSignals = trafficLightSignals;
	}

	public float getPeriodDuration() {
		return periodDuration;
	}

	public void setPeriodTimer(float truncatedShift) {
		this.periodTimer = truncatedShift;
	}
	
}
