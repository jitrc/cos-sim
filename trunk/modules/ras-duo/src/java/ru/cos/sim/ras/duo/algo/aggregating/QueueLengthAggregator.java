package ru.cos.sim.ras.duo.algo.aggregating;

import ru.cos.sim.ras.duo.DataSources;
import ru.cos.sim.ras.duo.Parameters;
import ru.cos.sim.ras.duo.DataAggregator;
import ru.cos.sim.ras.duo.DataCollector;
import ru.cos.sim.ras.duo.utils.SortedList;
import ru.cos.sim.services.ServiceLocator;
import ru.cos.sim.utils.ExpiringList;

public class QueueLengthAggregator implements DataCollector<DataSources>, DataAggregator {
	public QueueLengthAggregator(Parameters parameters) {
		this.parameters = parameters;
	}
	
	private Parameters parameters;
	
	public float getCurrentTime() {
		return ServiceLocator.getInstance().getTimeSerivce().getAbsoluteTime();
	}

	public float getExpirationPeriod() {
		return parameters.getExpiration().getExpirationPeriod() / 2;
	}

	public float getMinAcceptableSpeed() {
		return parameters.getSpeedProfile().getHighCongestionSpeed();
	}

	private float getQueueAdhesionLength() {
		return parameters.getQueuing().getQueueAdhesionLength();
	}
	
	private ExpiringList<Float> queuedVehicles = new ExpiringList<Float>();

	public float getQueueLength() {
		queuedVehicles.extractAllOlderThan(getCurrentTime() - getExpirationPeriod());

		SortedList<Float> sortedPositions = new SortedList<Float>();
		for (float vehiclePosition : queuedVehicles)
			sortedPositions.add(vehiclePosition);
		
		float queueLength = 0;
		float queueSegmentStart = -1, queueSegmentEnd = -1;
		for (float position : sortedPositions) {
			if (queueSegmentStart == -1)
				queueSegmentStart = position;
			if (queueSegmentEnd == -1)
				queueSegmentEnd = position;
			
			if (position - queueSegmentEnd <= getQueueAdhesionLength()) {
				queueSegmentEnd = position;
			} else {
				queueLength += queueSegmentEnd - queueSegmentStart + getQueueAdhesionLength();
				queueSegmentStart = queueSegmentEnd = -1;
			}
		}
		if (queueSegmentStart != -1 && queueSegmentEnd != -1)
			queueLength += queueSegmentEnd - queueSegmentStart + getQueueAdhesionLength();
		return queueLength;
	}
	
	@Override
	public void collectData(DataSources medium) {
		float position = medium.getPosition();
		float currentTime = getCurrentTime();
		
		if (medium.getSpeed() <= getMinAcceptableSpeed()) 
			queuedVehicles.add(currentTime, position);
	}

	@Override
	public float getAggregate() {
		return getQueueLength();
	}

	@Override
	public String toString() {
		return "RQL: " + getQueueLength() + "\tRQSZ:" + queuedVehicles.size();// + "\tQ:" + queuedVehicles;
	}
}
