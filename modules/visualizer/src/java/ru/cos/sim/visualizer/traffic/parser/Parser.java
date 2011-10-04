package ru.cos.sim.visualizer.traffic.parser;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import ru.cos.sim.visualizer.traffic.parser.trace.CrossRoad;
import ru.cos.sim.visualizer.traffic.parser.trace.DestinationNode;
import ru.cos.sim.visualizer.traffic.parser.trace.Link;
import ru.cos.sim.visualizer.traffic.parser.trace.SourceNode;
import ru.cos.sim.visualizer.traffic.parser.trace.TrafficLightNetwork;
import ru.cos.sim.visualizer.traffic.parser.trace.staff.Background;
import ru.cos.sim.visualizer.traffic.parser.trace.staff.Detector;
import ru.cos.sim.visualizer.traffic.parser.trace.staff.LaneClosure;
import ru.cos.sim.visualizer.traffic.parser.trace.staff.Meter;

public class Parser {
	protected static Logger logger = Logger.getLogger(VisParser.class.getName());
	private static enum Fields {
		TrafficModel,
		RoadNetwork,
		Agents,
		ModelParameters,
		OverPasses
	}
	
	private static Namespace currentNamespace;
	
	protected static ParserCollector parserCollector;
	
	public Parser(File file)
	{
		parserCollector = new ParserCollector();
		Document map;
		try
		{
			SAXBuilder builder = new SAXBuilder();
			map = builder.build(file);
		}
		catch (Exception e)
		{
			throw new Error("Problem during reading map file", e);
		}
		logger.log(Level.FINE, "MDF is correctly parsed by SaxBuilder");
		
		currentNamespace = map.getRootElement().getNamespace();
		readLinks(map.getRootElement().getChild(Fields.RoadNetwork.name(),currentNamespace));
		readNodes(map.getRootElement().getChild(Fields.RoadNetwork.name(),currentNamespace));
		readStaff(map.getRootElement());
		
	}
	
	private void readLinks(Element roadNetwork)
	{
		List<Element> links = roadNetwork.getChild(Link.ChapterName,currentNamespace).getChildren(Link.Name,currentNamespace);
		for (Element link : links)
		{
			parserCollector.links.add(new Link(link));
		}
		logger.log(Level.FINE, "Links are correctly parsed");
	}
	
	private void readNodes(Element roadNetwork)
	{
		List<Element> nodes = roadNetwork.getChild(CrossRoad.ChapterName,currentNamespace).getChildren();
		for (Element node : nodes)
		{
			if (node.getName().equals(CrossRoad.Name)) {
				parserCollector.nodes.add(new CrossRoad(node));
			} else if (node.getName().equals(SourceNode.Name)) {
				parserCollector.nodes.add(new SourceNode(node));
			} else if (node.getName().equals(DestinationNode.Name)) {
				parserCollector.nodes.add(new DestinationNode(node));
			}
		}
		logger.log(Level.FINE, "Nodes are correctly parsed");
	}
	
	private void readStaff(Element model)
	{
		
		Element background = model.getChild(Background.Name,currentNamespace);
		if (background != null && background.getChildren().size() != 0){
			
			List<Element> backgrounds = background.getChildren(Background.Fields.Tile.name(),currentNamespace);
			
			for (Element b : backgrounds) {
				parserCollector.staff.add(new Background(b));
			} 
		}
		
		
		
		Element tlnsElement = model.getChild(Fields.Agents.name(),currentNamespace).
		getChild(TrafficLightNetwork.ChapterName,currentNamespace);
		if (tlnsElement!=null){
			List<Element> tlnElementList = tlnsElement.getChildren(TrafficLightNetwork.Name,currentNamespace);
			for (Element tln : tlnElementList){
				parserCollector.staff.add(new TrafficLightNetwork(tln));
			}
			logger.log(Level.FINE, "Traffic light networks are correctly parsed");
		}
		
		Element detectorsitem = model.getChild(Fields.Agents.name(),currentNamespace).
				getChild(Detector.ChapterName,currentNamespace); 
		
		if (  detectorsitem != null) {
			
			List<Element> detectors = detectorsitem.getChildren(Detector.Name,currentNamespace);
					for (Element detector : detectors)
					{
						parserCollector.staff.add(new Detector(detector));
					}
					logger.log(Level.FINE, "Detectors are correctly parsed");
		}
		
		
		//Parsing closures
		Element closuresitem = model.getChild(Fields.Agents.name(),currentNamespace).
				getChild(LaneClosure.ChapterName,currentNamespace);
		
		if (closuresitem != null) {
			List<Element> closures = closuresitem.getChildren(LaneClosure.Name,currentNamespace);
			
			for (Element closure : closures) {
				parserCollector.staff.add(new LaneClosure(closure));
			}
			
			logger.log(Level.FINE, "LaneClosures are correctly parsed");
		}
		
		//Parsing Meters
		if (model.getChild(Meter.ChapterName,currentNamespace) == null) return;
		List<Element> meters = model.getChild(Meter.ChapterName,currentNamespace).
				getChildren();
		for (Element meter : meters)
		{
			parserCollector.staff.add(new Meter(meter));
		}
		
		logger.log(Level.FINE, "Meters are correctly parsed");
	}
	
	public static ParserCollector getParserCollector() {
		return parserCollector;
	}
	
	public static Namespace getCurrentNamespace() {
		return currentNamespace;
	}
	
}
