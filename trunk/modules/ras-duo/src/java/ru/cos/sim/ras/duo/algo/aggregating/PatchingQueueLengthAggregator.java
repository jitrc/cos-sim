package ru.cos.sim.ras.duo.algo.aggregating;

import ru.cos.sim.ras.duo.Parameters;
import ru.cos.sim.ras.duo.DataAggregator;
import ru.cos.sim.ras.duo.WeightProvider;
import ru.cos.sim.ras.duo.digraph.roadnet.LinkEdge;

public class PatchingQueueLengthAggregator extends PredictedQueueLengthAggregator {

	public PatchingQueueLengthAggregator(LinkEdge link, DataAggregator flowAggregator, DataAggregator flowCapacityAggregator,
										 WeightProvider travelTimeWeightProvider, Parameters parameters) {
		super(link, flowAggregator, flowCapacityAggregator, travelTimeWeightProvider, parameters);
	}

	protected static class SwitchingRegistration extends Registration {
		public SwitchingRegistration(int vehicleId, boolean skipped) {
			super(vehicleId, skipped);
		}

		private boolean isActive = false;
		public boolean isActive() {
			return isActive;
		}
		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}
	}
	
	@Override
	protected Registration createRegistration(int vehicleId, boolean isOverflowing) {
		return new SwitchingRegistration(vehicleId, !isOverflowing);
	}
	
	@Override
	protected void accountVehicle(Registration registration) {
		// Don't account vehicle until it's in the real queue 
		SwitchingRegistration reg = (SwitchingRegistration)registration;
		if (reg.isActive())
			super.accountVehicle(registration);
	}
	
	@Override
	protected void unaccountVehicle(Registration registration) {
		SwitchingRegistration reg = (SwitchingRegistration)registration;
		if (reg.isActive())
			super.unaccountVehicle(registration);
		
		// Now vehicle is in the queue, so activate registration
		reg.setActive(true);
	}
}
