package ru.cos.sim.ras.duo.algo.aggregating;

import ru.cos.sim.ras.duo.DataSources;
import ru.cos.sim.ras.duo.Parameters;
import ru.cos.sim.ras.duo.DataAggregator;
import ru.cos.sim.ras.duo.DataCollector;
import ru.cos.sim.services.ServiceLocator;
import ru.cos.sim.utils.ExpiringList;
import ru.cos.sim.utils.ExpiringList.Timestamped;

public class AverageSpeedAggregator implements DataCollector<DataSources>, DataAggregator {
	public AverageSpeedAggregator(Parameters parameters, float defaultValue) {
		this.parameters = parameters;
		this.defaultValue = defaultValue;
	}
	
	public AverageSpeedAggregator(Parameters parameters) {
		this(parameters, parameters.getSpeedProfile().getLowCongestionSpeed());
	}
	
	public float getCurrentTime() {
		return ServiceLocator.getInstance().getTimeSerivce().getAbsoluteTime();
	}
	
	private Parameters parameters;
	
	public float getExpirationPeriod() {
		return parameters.getExpiration().getExpirationPeriod();
	}
	
	public float getExpirationStrength() {
		return parameters.getExpiration().getExpirationStrength();
	}
	
	public float getRecoveryStrength() {
		return parameters.getExpiration().getRecoveryStrength();
	}
	
	public float getMinAcceptableSpeed() {
		return parameters.getSpeedProfile().getHighCongestionSpeed();
	}
	
	private float defaultValue;
	
	public float getDefaultAverage() {
		return defaultValue;
	}
	
	private ExpiringList<Float> values = new ExpiringList<Float>();
	
	@Override
	public void collectData(DataSources medium) {
		float speed = Math.max(medium.getSpeed(), getMinAcceptableSpeed());
		values.add(medium.getTimestamp(), speed);
	}

	@Override
	public float getAggregate() {
		float weightSum = 0;
		float weightedValuesSum = 0;
		
		float currentTime = getCurrentTime();
		
		values.extractAllOlderThan(currentTime - getExpirationPeriod());
		
		for (Timestamped<Float> valueStamp : values.getTimestampedValues()) {
			double weight = Math.exp(-(currentTime - valueStamp.getTimestamp()) / (getExpirationPeriod() / getExpirationStrength()));
			weightSum += weight;
			weightedValuesSum += weight * valueStamp.getValue();
		}
	
		if (weightSum < getRecoveryStrength()) {
			float recoveryWeight = getRecoveryStrength() - weightSum;
			weightSum += recoveryWeight;
			weightedValuesSum += recoveryWeight * getDefaultAverage();
		}
		
		float currentAverage = weightedValuesSum / weightSum;
		
		return currentAverage;
	}
	
	@Override
	public String toString() {
		return "AS:" + getAggregate() + "\tQSZ:" + values.size() + "\tQ:" + values;
	}
}
