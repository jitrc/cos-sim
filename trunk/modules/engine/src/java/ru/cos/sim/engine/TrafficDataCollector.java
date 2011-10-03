/**
 * 
 */
package ru.cos.sim.engine;

import ru.cos.cs.agents.framework.Agent;
import ru.cos.sim.agents.TrafficAgent;
import ru.cos.sim.agents.destination.Destination;
import ru.cos.sim.meters.impl.AbstractMeter;
import ru.cos.sim.meters.impl.SimpleSpeedMeasurer;
import ru.cos.sim.meters.impl.SimpleTimeMeasurer;
import ru.cos.sim.vehicle.Vehicle;

/**
 * Traffic Data Collector responsible for collecting data about simulation process
 * @author zroslaw
 */
public class TrafficDataCollector {
	
	/**
	 * Global meters measurers
	 */
	private SimpleSpeedMeasurer instantAverageSpeedMeasurer = new SimpleSpeedMeasurer();
	private SimpleTimeMeasurer totalTravelTimeMeasurer = new SimpleTimeMeasurer();
	private SimpleTimeMeasurer averageTravelTimeMeasurer = new SimpleTimeMeasurer();
	private SimpleSpeedMeasurer networkAverageTravelSpeedMeasurer = new SimpleSpeedMeasurer();
	
	/**
	 * Some intermediate data
	 */
	private int numberOfVehicles = 0;
	private int numberOfAliveOrigins = 0;
	private float summarySpeed = 0;
	private long totalArrivedVehicles = 0;
	private float totalTravelTime = 0;
	private int totalStops;
	private float totalTravelDistance = 0;


	/**
	 * Initialize meter
	 * @param meters
	 */
	public void initMeter(AbstractMeter meter) {
			switch (meter.getType()){
				case InstantAverageSpeedMeter:{
					meter.setMeasurer(instantAverageSpeedMeasurer);
					break;
				}
				case TotalTravelTimeMeter:{
					meter.setMeasurer(totalTravelTimeMeasurer);
					break;
				}
				case AverageTravelTimeMeter:{
					meter.setMeasurer(averageTravelTimeMeasurer);
					break;
				}
				case NetworkAverageTravelSpeedMeter:{
					meter.setMeasurer(networkAverageTravelSpeedMeasurer);
					break;
				}
		}
	}

	public void startStep(float dt){
		// calculate and set data for measurers
		// set instant average speed
		float instantAverageSpeed = numberOfVehicles==0?0:summarySpeed/numberOfVehicles;
		instantAverageSpeedMeasurer.setSpeed(instantAverageSpeed);
		// set total travel time
		totalTravelTimeMeasurer.setTime(totalTravelTime);
		// set average travel time
		averageTravelTimeMeasurer.setTime(((float)totalTravelTime)/totalArrivedVehicles);
		// set network average travel speed
		networkAverageTravelSpeedMeasurer.setSpeed(totalTravelDistance/totalTravelTime);
		
		// reset intermediate data
		totalTravelDistance=0;
		numberOfVehicles=0;
		numberOfAliveOrigins=0;
		summarySpeed=0;
//		totalArrivedVehicles=0;
//		totalTravelTime=0;
		totalStops=0;
	}
	
	public void examine(Agent agent){
		TrafficAgent trafficAgent = (TrafficAgent)agent;
		switch (trafficAgent.getTrafficAgentType()) {
			case Vehicle:{
				Vehicle vehicleAgent = (Vehicle)agent;
				numberOfVehicles++;
				summarySpeed += vehicleAgent.getSpeed();
				break;
			}
			case Origin:{
				numberOfAliveOrigins++;
				break;
			}
			case Destination:{
				Destination destination = (Destination)agent;
				totalTravelTime += destination.getTotalTravelTime();
				totalStops += destination.getTotalStops();
				totalArrivedVehicles += destination.getArrivedVehicles();
				totalTravelDistance += destination.getTotalTravelDistance();
				break;
			}
		}
	}
	
	public int getNumberOfVehicles() {
		return numberOfVehicles;
	}
	public int getNumberOfAliveOrigins() {
		return numberOfAliveOrigins;
	}
	public float getTotalTime() {
		return totalTravelTime;
	}
	public float getAverageSpeed(){
		return numberOfVehicles==0?0:summarySpeed/numberOfVehicles;
	}
	public int getTotalStops() {
		return totalStops;
	}

	public long getNumberOfArrivedVehicles() {
		return totalArrivedVehicles;
	}
	
	public boolean isStopCriterionSatisfied() {
		return numberOfVehicles==0 && numberOfAliveOrigins==0;
	}

}
