package ru.cos.sim.ras.duo;

public class ParametersData {

	private float communicatingVehiclesPercent = 100;
	public float getCommunicatingVehiclesPercent() {
		return communicatingVehiclesPercent;
	}
	public void setCommunicatingVehiclesPercent(float communicatingVehiclesPercent) {
		this.communicatingVehiclesPercent = communicatingVehiclesPercent;
	}
	
	private float communicationPeriod = 10;
	public float getCommunicationPeriod() {
		return communicationPeriod;
	}
	public void setCommunicationPeriod(float communicationPeriod) {
		this.communicationPeriod = communicationPeriod;
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

}
