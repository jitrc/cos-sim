/**
 * 
 */
package ru.cos.sim.agents.origin;

import java.util.List;
import java.util.Vector;

import ru.cos.sim.engine.RoadNetworkUniverse;
import ru.cos.sim.road.node.OriginNode;
import ru.cos.sim.utils.ProbabilityArray;
import ru.cos.sim.vehicle.init.data.VehicleData;

/**
 * Origin factory responsible for creating origin agents.
 * @author zroslaw
 */
public class OriginFactory {

	/**
	 * Create origin instance from data about it.
	 * @param originData instance of data structure that defines origin
	 * @param universe road network universe where to create origin
	 * @return instance of origin agent
	 */
	public static Origin createOrigin(OriginData originData, RoadNetworkUniverse universe) {
		Origin origin = new Origin();
		
		// set origin node
		OriginNode originNode = (OriginNode) universe.getNode(originData.getOriginNodeId());
		origin.setOriginNode(originNode);
		
		// set universe
		origin.setUniverse(universe);
		
		// create and set periods
		List<OriginPeriod> periods = new Vector<OriginPeriod>();
		for (OriginPeriodData periodData:originData.getTimePeriods()){
			OriginPeriod period = createPeriod(periodData, universe);
			periods.add(period);
		}
		origin.setPeriods(periods);
		
		return origin;
	}

	/**
	 * Create OriginPeriod instance from its data definition
	 * @param periodData instance of origin period data structure
	 * @param universe universe
	 * @return origin period instance
	 */
	private static OriginPeriod createPeriod(OriginPeriodData periodData, RoadNetworkUniverse universe) {
		OriginPeriod period = new OriginPeriod();
		
		// set universe
		period.setUniverse(universe);
		
		// create and set destinations
		ProbabilityArray<Integer> destinations = 
			new ProbabilityArray<Integer>(periodData.getListOfDestinations());
		period.setDestinations(destinations);
		
		// create and set vehicle profiles
		ProbabilityArray<VehicleData> vehicleProfiles = 
			new ProbabilityArray<VehicleData>(periodData.getVehicleProfiles());
		period.setVehicleProfiles(vehicleProfiles);
		
		// set duration and number of vehicles
		period.setDuration(periodData.getPeriodDuration());
		period.setNumberOfVehicles(periodData.getNumberOfVehicles());
		
		return period;
	}
	
}
