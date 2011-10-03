package ru.cos.sim.ras.duo.algo.aggregating;

import java.util.HashMap;
import java.util.Map;

import ru.cos.sim.ras.duo.DataSources;
import ru.cos.sim.ras.duo.Parameters;
import ru.cos.sim.ras.duo.DataAggregator;
import ru.cos.sim.ras.duo.DataCollector;
import ru.cos.sim.ras.duo.WeightProvider;
import ru.cos.sim.ras.duo.digraph.roadnet.LinkEdge;
import ru.cos.sim.road.init.data.LaneData;
import ru.cos.sim.road.init.data.SegmentData;
import ru.cos.sim.services.ServiceLocator;
import ru.cos.sim.utils.ExpiringList;

public class PredictedQueueLengthAggregator implements DataCollector<DataSources>, DataAggregator {

	public PredictedQueueLengthAggregator(LinkEdge link, DataAggregator flowAggregator, DataAggregator flowCapacityAggregator,
										  WeightProvider travelTimeWeightProvider, Parameters parameters) {
		this.link = link;
		this.flowAggregator = flowAggregator;
		this.flowCapacityAggregator = flowCapacityAggregator;
		this.travelTimeWeightProvider = travelTimeWeightProvider;
		this.parameters = parameters;
	}
	
	private LinkEdge link;
	
	private Float averageLaneCount;
	private float getAverageLaneCount() {
		if (averageLaneCount == null) {
			float segmentLaneCountSum = 0, segmentLengthSum = 0;
			for (Map.Entry<Integer, SegmentData> segmentEntry : link.getLinkData().getSegments().entrySet()) {
				SegmentData segment = segmentEntry.getValue();
				int segmentLaneCount = segment.getLanes().length;
				
				float laneLengthSum = 0;
				for (LaneData lane : segment.getLanes())
					laneLengthSum += lane.getLength();
				float segmentLength = laneLengthSum / segmentLaneCount;
				
				segmentLaneCountSum += segmentLaneCount * segmentLength;
				segmentLengthSum += segmentLength;
			}
			averageLaneCount = segmentLaneCountSum / segmentLengthSum;
		}
		return averageLaneCount;
	}
	
	protected float getCurrentTime() {
		return ServiceLocator.getInstance().getTimeSerivce().getAbsoluteTime();
	}

	private WeightProvider travelTimeWeightProvider;
	protected float getCurrentTravelTime() {
		return travelTimeWeightProvider.getWeight(link, null);
	}
	
	private DataAggregator flowCapacityAggregator;
	protected DataAggregator getFlowCapacityAggregator() {
		return flowCapacityAggregator;
	}
	
	private DataAggregator flowAggregator;
	protected DataAggregator getFlowAggregator() {
		return flowAggregator;
	}
	
	private Parameters parameters;
	
	private float getQueueLengthPerVehicle() {
		return parameters.getQueuing().getQueueLengthPerVehicle();
	}
	
	private float getMinimumSpeed() {
		return parameters.getSpeedProfile().getHighCongestionSpeed();
	}
	
	private ExpiringList<Registration> registeredVehiclesExpiration = new ExpiringList<Registration>();
	private Map<Integer, Registration> registeredVehicles = new HashMap<Integer, Registration>();

	protected static class Registration {
		public Registration(int vehicleId, boolean skipped) {
			this.vehicleId = vehicleId;
			this.skipped = skipped;
			this.accounted = false;
		}
		private final int vehicleId;
		public int getVehicleId() {
			return vehicleId;
		}

		private final boolean skipped;
		public boolean isSkipped() {
			return skipped;
		}
		
		private boolean accounted;
		public boolean isAccounted() {
			return accounted;
		}
		public void setAccounted(boolean accounted) {
			this.accounted = accounted;
		}
	}
	
	private int overflowingVehicles = 0;

	protected int getOverflowingVehicles() {
		return overflowingVehicles;
	} 

	private float getExpirationTime() {
		return getCurrentTime() + getCurrentTravelTime();
	}

	protected void accountVehicle(Registration registration) {
		if (!registration.isAccounted()) {
			registration.setAccounted(true);
			overflowingVehicles++;
		}
	}
	
	protected void unaccountVehicle(Registration registration) {
		if (registration.isAccounted()) {
			registration.setAccounted(false);
			overflowingVehicles--;
		}
	}
	
	private Registration getRegistration(int id) {
		Registration registration = registeredVehicles.get(id);
		if (registration == null)
			registration = registerVehicle(id);
		return registration;
	}
	
	private Registration registerVehicle(int id) {
		Registration registration = createRegistration(id, isVehicleOverflowing()); 
		registeredVehicles.put(id, registration);
		registeredVehiclesExpiration.add(getExpirationTime(), registration);
		return registration;
	}
	
	protected Registration createRegistration(int vehicleId, boolean isOverflowing) {
		return new Registration(vehicleId, !isOverflowing);
	}
	
	private void unregisterVehicle(Registration registration) {
		registeredVehicles.remove(registration.getVehicleId());
	}

	private void expireVehicles() {
		for (Registration registration : registeredVehiclesExpiration.extractAllOlderThan(getCurrentTime())) {
			unaccountVehicle(registration);
			unregisterVehicle(registration);
		}
	}
	
	protected boolean isVehicleOverflowing() {
		float flow = getFlowAggregator().getAggregate();
		float flowCapacity = getFlowCapacityAggregator().getAggregate();
		return (flow > flowCapacity ? getRandomFloat() < 1 - flowCapacity / flow : false);
	}
	
	private float getRandomFloat() {
		return ServiceLocator.getInstance().getRandomizationService().getRandom().nextFloat();
	}
	
	@Override
	public void collectData(DataSources medium) {
		int vehicleId = medium.getVehicleId();
		Registration registration = getRegistration(vehicleId);
		
		if (!registration.isSkipped())
			if (medium.getSpeed() > getMinimumSpeed())
				// Predicted queue
				accountVehicle(registration);
			else
				// Real queue
				unaccountVehicle(registration);
	}
	
	private float getQueueLength(float queueSize) {
		return queueSize * getQueueLengthPerVehicle() / getAverageLaneCount();
	}
	
	@Override
	public float getAggregate() {
		expireVehicles();
		
		return getQueueLength(getOverflowingVehicles());
	}
	
	@Override
	public String toString() {
		return "PQL: " + getAggregate() + "\tPQSZ: " + getOverflowingVehicles();
	}
}
