package ru.cos.sim.visualizer.traffic.parser.trace;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.Parser;
import ru.cos.sim.visualizer.traffic.parser.base.Entity;
import ru.cos.sim.visualizer.traffic.parser.trace.base.Staff;
import ru.cos.sim.visualizer.traffic.parser.trace.location.NodeLocation;
import ru.cos.sim.visualizer.traffic.parser.utils.ItemParser;

public class TrafficLightNetwork extends Staff {

	public static String Name = "RegularTrafficLightNetwork";
	public static String ChapterName = "TrafficLightNetworks";
	
	private static enum Fields {
		regularNodeId,
		Type
	}
	
	protected NodeLocation location;
	protected String type;
	protected ArrayList<TrafficLight> trafficLights;
	
	public TrafficLightNetwork(Element e)
	{
		super(e);
		this.setType(StaffType.TrafficLightNetwork);
		this.trafficLights = new ArrayList<TrafficLight>();
		this.location = new NodeLocation(ItemParser.getLong(e, Fields.regularNodeId.name()).intValue());
		//this.type = e.getChildText(Fields.Type.name(),Parser.getCurrentNamespace());
		
		List<Element> tls = e.getChild(TrafficLight.ChapterName,
				Parser.getCurrentNamespace()).
				getChildren(TrafficLight.Name,Parser.getCurrentNamespace());
		for (Element tl : tls) {
			this.trafficLights.add(new TrafficLight(tl));
		}
	}
}
