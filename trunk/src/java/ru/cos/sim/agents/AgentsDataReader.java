/**
 * 
 */
package ru.cos.sim.agents;

import java.util.HashSet;
import java.util.Set;

import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.agents.origin.OriginData;
import ru.cos.sim.agents.origin.OriginDataReader;
import ru.cos.sim.agents.tlns.data.TrafficLightNetworkData;
import ru.cos.sim.agents.tlns.xml.TrafficLightNetworkReader;
import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.vehicle.init.data.VehicleData;
import ru.cos.sim.vehicle.init.xml.VehicleDataReader;



/**
 * 
 * @author zroslaw
 */
public class AgentsDataReader {
	
	public static final Namespace NS = MDFReader.MDF_NAMESPACE;

	public static final String ORIGINS = "Origins";
	public static final String ORIGIN = "Origin";
	public static final String VEHICLES = "Vehicles";
	public static final String TLNS = "TrafficLightNetworks";

	public static Set<TrafficAgentData> read(Element agentsElement) {
		Set<TrafficAgentData> agentsData = new HashSet<TrafficAgentData>();
		
		// read origins
		Element originsElement = agentsElement.getChild(ORIGINS, NS);
		if (originsElement!=null){
			for (Object originObj:originsElement.getChildren(ORIGIN, NS)){
				Element originElement = (Element) originObj;
				OriginData originData = OriginDataReader.read(originElement);
				agentsData.add(originData);
			}
		}
		
		// read vehicles
		Element vehiclesElement = agentsElement.getChild(VEHICLES, NS);
		if (vehiclesElement!=null){
			for (Object vehicleObj:vehiclesElement.getChildren()){
				Element vehicleElement = (Element)vehicleObj;
				VehicleData vehicleData = VehicleDataReader.read(vehicleElement);
				agentsData.add(vehicleData);
			}
		}
		
		// read traffic light network
		Element tlnsElement = agentsElement.getChild(TLNS, NS);
		if (tlnsElement!=null){
			for (Object tlnObj:tlnsElement.getChildren()){
				Element tlnElement = (Element) tlnObj;
				TrafficLightNetworkData tlnData = TrafficLightNetworkReader.read(tlnElement);
				agentsData.add(tlnData);
			}
		}
		
		return agentsData;
	}

}
