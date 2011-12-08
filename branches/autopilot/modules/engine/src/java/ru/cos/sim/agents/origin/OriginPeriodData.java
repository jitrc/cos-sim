package ru.cos.sim.agents.origin;

import java.util.List;

import ru.cos.sim.utils.Pair;
import ru.cos.sim.vehicle.init.data.VehicleData;

/**
 * Class represents a period of the origin activity.
 * During this period origin will generate traffic flow with uniform characteristics, i.e.
 * with constant intensity, uniform destinations distribution, uniform distribution of vehicle types and with
 * @author zroslaw
 */
public class OriginPeriodData{
	
	/**
	 * Duration of the period
	 */
	private float periodDuration;

	/**
	 * Number of vehicles to be originated during period 
	 */
	 private int numberOfVehicles;
	
	/**
	 * List of destinations for this period.<br>
	 * List of pairs, each pair is a pair of (First)Id of a destination node and (Second) its probability weight.
	 * According to this probability weight each next originated vehicle will be assigned to
	 * corresponding destination node.
	 */
	List<Pair<Integer,Integer>> listOfDestinations;
	
	/**
	 * List of vehicle profiles to be originated during the period.
	 * List of pairs, each pair is a pair of (First) vehicle profile data and (Second) its probability weight.
	 * According to this probability weight each next originated vehicle will be created on the base of 
	 * particular vehicle profile.
	 */
	List<Pair<VehicleData,Integer>> vehicleProfiles;
	
	public void setNumberOfVehicles(int numberOfVehicles) {
		this.numberOfVehicles = numberOfVehicles;
	}
	
	public void setListOfDestinations(
			List<Pair<Integer, Integer>> listOfDestinations) {
		this.listOfDestinations = listOfDestinations;
	}
	
	public void setVehicleProfiles(List<Pair<VehicleData, Integer>> vehicleProfiles) {
		this.vehicleProfiles = vehicleProfiles;
	}

	public float getPeriodDuration() {
		return periodDuration;
	}

	public void setPeriodDuration(float periodDiration) {
		this.periodDuration = periodDiration;
	}

	public int getNumberOfVehicles() {
		return numberOfVehicles;
	}

	public List<Pair<Integer, Integer>> getListOfDestinations() {
		return listOfDestinations;
	}

	public List<Pair<VehicleData, Integer>> getVehicleProfiles() {
		return vehicleProfiles;
	}
	
}