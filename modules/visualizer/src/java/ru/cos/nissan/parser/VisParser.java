package ru.cos.nissan.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import ru.cos.math.BezierLane;
import ru.cos.math.Vector2f;
import ru.cos.math.Vector3f;
import ru.cos.nissan.core.SimulationSystemManager;
import ru.cos.nissan.parser.geometry.Point;
import ru.cos.nissan.parser.geometry.Waypoint;
import ru.cos.nissan.parser.trace.BezierTransitionRule;
import ru.cos.nissan.parser.trace.base.Node;
import ru.cos.nissan.parser.trace.base.Staff;
import ru.cos.nissan.parser.trace.base.Staff.StaffType;
import ru.cos.nissan.parser.trace.base.TransitionRule.GeometryType;
import ru.cos.nissan.parser.trace.location.LinkLocation;
import ru.cos.nissan.parser.trace.location.NodeLocation;
import ru.cos.nissan.parser.trace.location.SegmentLocation;
import ru.cos.nissan.parser.trace.staff.Sign;
import ru.cos.nissan.utils.RuleCutter;
import ru.cos.scene.impl.INode;
import ru.cos.scene.shapes.BaseSegmentForm;
import ru.cos.scene.shapes.BoundNodeShape;
import ru.cos.scene.shapes.CrossRoadShape;
import ru.cos.scene.shapes.SignForm;
import ru.cos.scene.shapes.TetrapodItem;
import ru.cos.scene.shapes.TransitionForm;
import ru.cos.trace.TraceHandler;
import ru.cos.trace.item.BezierRule;
import ru.cos.trace.item.BoundaryNode;
import ru.cos.trace.item.CrossRoad;
import ru.cos.trace.item.Lane;
import ru.cos.trace.item.Link;
import ru.cos.trace.item.Meter;
import ru.cos.trace.item.Segment;
import ru.cos.trace.item.TransitionRule;


public class VisParser extends Parser {

	private TraceHandler handler;
	
	public VisParser(File file) {
		super(file);
		
		logger.log(Level.FINE, "MDF file parsing sucessful");
		
		handler = SimulationSystemManager.getInstance().getTraceHandler();
		
		createNetwork();
		createStaff();
	}
	
	private void createNetwork()
	{
		// Initing RoadNetwork
		initLinks();
		initSegments();
		initLanes();
		
		initNodes();
		initTransitionRules();
		//
		
		//Finish creating road network
		precompleteLSegments();
		completeSegments();
		completeLanes();
		completeRules();
		//
		postCompleteSegments();
		
	}
	
	private void initLinks()
	{
		logger.log(Level.INFO, "Links - begin initialization");
		
		for (ru.cos.nissan.parser.trace.Link link : parserCollector.links)
		{
			handler.initLink(link);
		}
		
		logger.log(Level.FINE, "Links sucessfully inited");
	}
	
	private void createStaff()
	{
		logger.log(Level.INFO, "Staff - begin initialization");
		
		for (Staff s : parserCollector.staff) {
			handler.initStaff(s);
		}
		
		logger.log(Level.FINE, "Staff sucessfully inited");
	}
	
	private void initNodes(){
		
		logger.log(Level.INFO, "Nodes - begin initialization");
		
		for (Node node : parserCollector.nodes)
		{
			handler.initNode(node);
		}
		
		logger.log(Level.FINE, "Nodes sucessfully inited");
	}
	
	private void initTransitionRules()
	{
		logger.log(Level.INFO, "Transition rules - begin initialization");
		
		for (ru.cos.nissan.parser.trace.base.TransitionRule rule : parserCollector.rules)
		{
			handler.initRule(rule);
		}
		
		logger.log(Level.FINE, "Transition rules sucessfully inited");
	}
	
	
	private void completeRules()
	{
		logger.log(Level.INFO, "Transition rules - begin completing relations");
		
		for (ru.cos.nissan.parser.trace.base.TransitionRule rule : parserCollector.rules)
		{
			handler.completeRule(rule);
		}
		
		logger.log(Level.FINE, "Transition rules sucessfully completed");
	}
	
	private void initSegments()
	{
		logger.log(Level.INFO, "Segments - begin initialization");
		
		for (ru.cos.nissan.parser.trace.Segment s : parserCollector.segments) {
			handler.initSegment(s);
		}
		
		logger.log(Level.FINE, "Segments sucessfully inited");
	}
	
	private void completeSegments()
	{
		logger.log(Level.INFO, "Segments - begin completing process");
		
		for (ru.cos.nissan.parser.trace.Segment s : parserCollector.segments)
		{
			handler.completeSegment(s);
		}
		
		
		logger.log(Level.FINE, "Segments relations sucessfully completed");
	}
	
	private void precompleteLSegments()
	{
		logger.log(Level.INFO, "Segments - begin completing process");
		
		for (ru.cos.nissan.parser.trace.Segment s : parserCollector.segments)
		{
			handler.precompleteSegment(s);
		}
		
		
		logger.log(Level.FINE, "Segments relations sucessfully completed");
	}
	
	private void postCompleteSegments()
	{
		logger.log(Level.INFO, "Segments - begin post completing process");
		
		for (ru.cos.nissan.parser.trace.Segment s : parserCollector.segments)
		{
			handler.segmentPostComplete(s);
		}
		
		logger.log(Level.FINE, "Segments sucessfully completed");
	}
	
	private void completeLanes()
	{
		logger.log(Level.INFO, "Lanes - begin completing process");
		
		for (ru.cos.nissan.parser.trace.Lane lane : parserCollector.lanes)
		{
			handler.completeLane(lane);		
		}
		
		logger.log(Level.FINE, "Lanes relations sucessfully completed");
	}
	
	private void initLanes()
	{
		logger.log(Level.INFO, "Lanes - begin initialization");
		
		for (ru.cos.nissan.parser.trace.Lane lane : parserCollector.lanes) {
			handler.initLane(lane);
		}
		
		logger.log(Level.FINE, "Lanes sucessfully inited");
	}
	

	 public static void main(String[] args) {
		 String path = "/home/ivan/Downloads/hell-downtown.mdf";
		 VisParser parser = new VisParser(new File(path));
		 return;
	 }
	 

}
