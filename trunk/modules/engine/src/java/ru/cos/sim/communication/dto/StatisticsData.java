package ru.cos.sim.communication.dto;

/**
 * Statistics of simulation process
 * @author zroslaw
 */
public class StatisticsData{
	protected float universeTime;
	protected int numberOfVehicles;
	protected int numberOfAliveOrigins;
	protected float averageSpeed;
	protected float totalTime;
	private int totalStops;
	private long numberOfArrivedVehicles;

	public float getUniverseTime() {
		return universeTime;
	}
	public void setUniverseTime(float universeTime) {
		this.universeTime = universeTime;
	}
	public int getNumberOfVehicles() {
		return numberOfVehicles;
	}
	public void setNumberOfVehicles(int numberOfVehicles) {
		this.numberOfVehicles = numberOfVehicles;
	}
	public int getNumberOfAliveOrigins() {
		return numberOfAliveOrigins;
	}
	public void setNumberOfAliveOrigins(int numberOfAliveOrigins) {
		this.numberOfAliveOrigins = numberOfAliveOrigins;
	}
	public float getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
	}
	public float getAverageSpeed() {
		return averageSpeed;
	}
	public void setAverageSpeed(float averageSpeed) {
		this.averageSpeed = averageSpeed;
	}
	public int getTotalStops() {
		return totalStops;
	}
	public void setTotalStops(int totalStops) {
		this.totalStops = totalStops;
	}
	public long getNumberOfArrivedVehicles() {
		return numberOfArrivedVehicles;
	}
	public void setNumberOfArrivedVehicles(long numberOfArrivedVehicles) {
		this.numberOfArrivedVehicles = numberOfArrivedVehicles;
	}
}
