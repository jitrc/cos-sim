/**
 * 
 */
package ru.cos.sim.agents.origin;

import java.util.ArrayList;
import java.util.List;


import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.utils.Pair;
import ru.cos.sim.vehicle.init.data.RegularVehicleData;
import ru.cos.sim.vehicle.init.data.VehicleData;
import ru.cos.sim.vehicle.init.xml.RegularVehicleDataReader;

/**
 * Reader for the &lt;Origin&gt; XML element definition.
 * @author zroslaw
 */
public class OriginDataReader {
	
	public static final Namespace NS = MDFReader.MDF_NAMESPACE;

	public static final String ID = "id";
	public static final String ORIGIN_NODE_ID = "originNodeId";
	public static final String TIME_PERIODS = "TimePeriods";
	public static final String TIME_PERIOD = "TimePeriod";
	public static final String DURATION = "duration";
	public static final String NUMBER_OF_VEHICLES = "numberOfVehicles";
	public static final String DESTINATIONS = "Destinations";
	public static final String DESTINATION = "Destination";
	public static final String DESTINATION_ID = "destinationNodeId";
	public static final String WEIGHT = "weight";
	public static final String CLASS = "class";
	public static final String VEHICLE_PROFILES = "VehicleProfiles";
	public static final String VEHICLE_PROFILE = "VehicleProfile";
	public static final String REGULAR_VEHICLE = "RegularVehicle";

	public static OriginData read(Element originElement){
		
		OriginData originData = new OriginData();
		originData.setOriginNodeId(Integer.parseInt(originElement.getChildText(ORIGIN_NODE_ID, NS)));
		List<OriginPeriodData> originPeriodDataList = new ArrayList<OriginPeriodData>();
		for(Object timePeriodObj:originElement.getChild(TIME_PERIODS, NS).getChildren(TIME_PERIOD, NS)){
			Element timePeriodElement = (Element)timePeriodObj;
			OriginPeriodData originPeriodData = new OriginPeriodData();
			originPeriodData.setPeriodDuration(Float.parseFloat(timePeriodElement.getChildText(DURATION, NS)));
			originPeriodData.setNumberOfVehicles(Integer.parseInt(timePeriodElement.getChildText(NUMBER_OF_VEHICLES, NS)));
			
			// Read destinations set with weights
			List<Pair<Integer, Integer>> destinationDataList = new ArrayList<Pair<Integer,Integer>>();
			for(Object destinationObj:timePeriodElement.getChild(DESTINATIONS, NS).getChildren(DESTINATION, NS)){
				Element destinationElement = (Element)destinationObj;
				int destinationNodeId = Integer.parseInt(destinationElement.getChildText(DESTINATION_ID, NS));
				int weight = Integer.parseInt(destinationElement.getChildText(WEIGHT, NS));
				destinationDataList.add(new Pair<Integer, Integer>(destinationNodeId, weight));
			}
			originPeriodData.setListOfDestinations(destinationDataList);
			
			// read vehicle profile data with its weight
			List<Pair<VehicleData,Integer>> vehicleProfilesList = new ArrayList<Pair<VehicleData,Integer>>();
			for (Object vehicleProfileObj:timePeriodElement.getChild(VEHICLE_PROFILES, NS).getChildren(VEHICLE_PROFILE, NS)){
				Element vehicleProfileElement = (Element)vehicleProfileObj;
				int weight = Integer.parseInt(vehicleProfileElement.getChildText(WEIGHT, NS));
				
				// read vehicle data
				// for now only RegularVehicle is available.
				RegularVehicleData vehicleProfileData =
					RegularVehicleDataReader.read(vehicleProfileElement.getChild(REGULAR_VEHICLE,NS));
				
				vehicleProfilesList.add(new Pair<VehicleData, Integer>(vehicleProfileData, weight));
			}
			originPeriodData.setVehicleProfiles(vehicleProfilesList);
			originPeriodDataList.add(originPeriodData);
		}
		originData.setTimePeriods(originPeriodDataList);
		
		return originData;
	}
	
}
