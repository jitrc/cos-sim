package ru.cos.sim.ras.duo;


public class Parameters {
	
	public Parameters(Expiration expiration, Segmentation segmentation, SpeedProfile speedProfile, Queuing queuing, TemperatureProfile temperatureProfile) {
		this.expiration = expiration;
		this.segmentation = segmentation;
		this.speedProfile = speedProfile;
		this.queuing = queuing;
		this.temperatureProfile = temperatureProfile;
	}
	public Parameters(ParametersData data) {
		this(new Expiration(), new Segmentation(), new SpeedProfile(), new Queuing(), new TemperatureProfile());
		load(data);
	}
	public Parameters() {
		this(new ParametersData());
	}
	
	public void load(ParametersData parametersData) {
		getExpiration().load(parametersData.getCommunicationPeriod());
		getQueuing().load(parametersData.getCommunicatingVehiclesPercent());
		getTemperatureProfile().load(parametersData);
	}

	private Expiration expiration;
	public Expiration getExpiration() {
		return expiration;
	}
	public void setExpiration(Expiration expiration) {
		this.expiration = expiration;
	}

	private Segmentation segmentation;
	public Segmentation getSegmentation() {
		return segmentation;
	}
	public void setSegmentation(Segmentation segmentation) {
		this.segmentation = segmentation;
	}

	private SpeedProfile speedProfile;
	public SpeedProfile getSpeedProfile() {
		return speedProfile;
	}
	public void setSpeedProfile(SpeedProfile speedProfile) {
		this.speedProfile = speedProfile;
	}
	
	private Queuing queuing;
	public Queuing getQueuing() {
		return queuing;
	}
	public void setQueuing(Queuing queuing) {
		this.queuing = queuing;
	}
	
	private TemperatureProfile temperatureProfile;
	public TemperatureProfile  getTemperatureProfile() {
		return temperatureProfile;
	}
	public void setTemperatureProfile(TemperatureProfile temperatureProfile) {
		this.temperatureProfile = temperatureProfile;
	}

	public static class Expiration {
		public Expiration() { }
		public Expiration(float routeUpdatePeriod) {
			load(routeUpdatePeriod);
		}
		
		private void load(float routeUpdatePeriod) {
			this.expirationPeriod = routeUpdatePeriod;
		}
		
		private float expirationPeriod = 30;
		public float getExpirationPeriod() {
			return expirationPeriod;
		}
		public void setExpirationPeriod(float expirationPeriod) {
			this.expirationPeriod = expirationPeriod;
		}
		
		private float expirationStrength = 2.5f;
		public float getExpirationStrength() {
			return expirationStrength;
		}
		public void setExpirationStrength(float expirationStrength) {
			this.expirationStrength = expirationStrength;
		}
	
		private float recoveryStrength = 1;
		public float getRecoveryStrength() {
			return recoveryStrength;
		}
		public void setRecoveryStrength(float recoveryStrength) {
			this.recoveryStrength = recoveryStrength;
		}
	}

	public static class Segmentation {
		private float segmentSize = 30;
		public float getSegmentSize() {
			return segmentSize;
		}
		public void setSegmentSize(float segmentSize) {
			this.segmentSize = segmentSize;
		}
	}	

	public static class SpeedProfile {
		public SpeedProfile() { }
		public SpeedProfile(float aveageMaxSpeed) {
			load(aveageMaxSpeed);
		}
		
		private void load(float aveageMaxSpeed) {
			this.setLowCongestionSpeed(aveageMaxSpeed);
			this.setMediumCongestionSpeed(aveageMaxSpeed / 2.5f);
		}
		
		private float lowCongestionSpeed = 25;
		public float getLowCongestionSpeed() {
			return lowCongestionSpeed;
		}
		public void setLowCongestionSpeed(float lowCongestionSpeed) {
			this.lowCongestionSpeed = lowCongestionSpeed;
		}

		private float mediumCongestionSpeed = 10;
		public float getMediumCongestionSpeed() {
			return mediumCongestionSpeed;
		}
		public void setMediumCongestionSpeed(float mediumCongestionSpeed) {
			this.mediumCongestionSpeed = mediumCongestionSpeed;
		}
		
		private float highCongestionSpeed = 1.5f;
		public float getHighCongestionSpeed() {
			return highCongestionSpeed;
		}
		public void setHighCongestionSpeed(float highCongestionSpeed) {
			this.highCongestionSpeed = highCongestionSpeed;
		}
	}
	
	public static class Queuing {
		public Queuing() { }
		public Queuing(float communicatingVehiclesPercent) {
			load(communicatingVehiclesPercent);
		}
		
		private void load(float communicatingVehiclesPercent) {
			this.queueAdhesionLength = 10 / communicatingVehiclesPercent * 100;
		}
		
		private float queueAdhesionLength = 10;
		public float getQueueAdhesionLength() {
			return queueAdhesionLength;
		}
		public void setQueueAdhesionLength(float queueAdhesionLength) {
			this.queueAdhesionLength = queueAdhesionLength;
		}

		private float queuePhaseSpeed = 2.4f; // = 1 / (1 / 8.0 + 1 / 3.4)
		public float getQueuePhaseSpeed() {
			return queuePhaseSpeed;
		}
		public void setQueuePhaseSpeed(float queuePhaseSpeed) {
			this.queuePhaseSpeed = queuePhaseSpeed;
		}
		
		private float destinationFlowCapacity = 0.4f;
		public float getDestinationFlowCapacity() {
			return destinationFlowCapacity;
		}
		public void setDestinationFlowCapacity(float destinationFowCapacity) {
			this.destinationFlowCapacity = destinationFowCapacity;
		}
		
		private float queueLengthPerVehicle = 8;
		public float getQueueLengthPerVehicle() {
			return queueLengthPerVehicle;
		}
		public void setQueueLengthPerVehicle(float queueLengthPerVehicle) {
			this.queueLengthPerVehicle = queueLengthPerVehicle;
		}
	}
	
	public static class TemperatureProfile {
		public TemperatureProfile() { }
		public TemperatureProfile(ParametersData parameters) {
			load(parameters);
		}
		
		private void load(ParametersData parameters) {
			this.maxTemperature = parameters.getMaxTemperature();
			this.threshold = parameters.getThreshold();
		}
		
		private float maxTemperature = 4;
		public float getMaxTemperature() {
			return maxTemperature;
		}
		public void setMaxTemperature(float maxTemperature) {
			this.maxTemperature = maxTemperature;
		}
		
		private float threshold = 0.3f;
		public float getThreshold() {
			return threshold;
		}
		public void setThreshold(float threshold) {
			this.threshold = threshold;
		}
		
		public float getTemperature(float congestion) {
			return (congestion < getThreshold() ? 0 : getMaxTemperature() * (congestion - getThreshold()) / (1 - getThreshold()));
		}
	}
}
