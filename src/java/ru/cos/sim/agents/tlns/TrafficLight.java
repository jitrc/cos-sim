package ru.cos.sim.agents.tlns;

import ru.cos.sim.road.objects.AbstractRoadObject;

public class TrafficLight extends AbstractRoadObject{
	
	private TrafficLightSignal signal = TrafficLightSignal.Green;

	@Override
	public RoadObjectType getRoadObjectType() {
		return RoadObjectType.TrafficLight;
	}

	public TrafficLightSignal getSignal() {
		return signal;
	}

	public void setSignal(TrafficLightSignal signal) {
		this.signal = signal;
	}

}
