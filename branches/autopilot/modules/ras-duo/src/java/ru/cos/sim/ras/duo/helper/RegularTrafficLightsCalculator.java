package ru.cos.sim.ras.duo.helper;

import java.util.ArrayList;
import java.util.List;

import ru.cos.sim.agents.tlns.TrafficLightSignal;
import ru.cos.sim.agents.tlns.data.RegularTLNData;
import ru.cos.sim.agents.tlns.data.TimePeriodData;


public class RegularTrafficLightsCalculator {

	public static float getPhaseRatio(RegularTLNData trafficLights, int trafficLightId) {
		List<Integer> trafficLightIds = new ArrayList<Integer>();
		trafficLightIds.add(trafficLightId);
		return getPhaseRatio(trafficLights, trafficLightIds); 
	}
	
	public static float getPhaseRatio(RegularTLNData trafficLights, Iterable<Integer> trafficLightIds) {
		PhaseSum sum = getPhaseSum(trafficLights, trafficLightIds);
		return (sum.getGreenTime() != 0 ? sum.getRedTime() / sum.getGreenTime() : Float.POSITIVE_INFINITY); 
	}
	
	public static PhaseSum getPhaseSum(RegularTLNData trafficLights, Iterable<Integer> trafficLightIds) {
		float greenTime = 0, redTime = 0;
		for (TimePeriodData period : trafficLights.getScheduleTable().getTimePeriods()) {
			for (int trafficLightId : trafficLightIds) {
				if (trafficLightId != -1)
					if (period.getTrafficLightSignals().get(trafficLightId) == TrafficLightSignal.Green)
						greenTime += period.getDuration();
					else
						redTime += period.getDuration();
			}
		}
		return new PhaseSum(greenTime, redTime);
	}
	
	public static class PhaseSum {
		public PhaseSum(float greenTime, float redTime) {
			this.greenTime = greenTime;
			this.redTime = redTime;
		}
		
		private final float greenTime;
		public float getGreenTime() {
			return greenTime;
		}
		
		private final float redTime;
		public float getRedTime() {
			return redTime;
		}
	}
}
