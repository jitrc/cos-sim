package ru.cos.sim.visualizer.trace;

import java.util.ArrayList;
import java.util.HashMap;


import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.renderer.Renderer.RenderType;
import ru.cos.sim.visualizer.renderer.impl.IRenderable;
import ru.cos.sim.visualizer.scene.impl.INode;
import ru.cos.sim.visualizer.scene.shapes.BackgroundForm;
import ru.cos.sim.visualizer.scene.shapes.BaseSegmentForm;
import ru.cos.sim.visualizer.scene.shapes.BeaconForm;
import ru.cos.sim.visualizer.scene.shapes.BoundNodeShape;
import ru.cos.sim.visualizer.scene.shapes.CrossRoadShape;
import ru.cos.sim.visualizer.scene.shapes.GradientForm;
import ru.cos.sim.visualizer.scene.shapes.LineDirectionShape;
import ru.cos.sim.visualizer.scene.shapes.MeterForm;
import ru.cos.sim.visualizer.scene.shapes.SignForm;
import ru.cos.sim.visualizer.scene.shapes.TransitionForm;
import ru.cos.sim.visualizer.scene.shapes.GradientForm.FormType;
import ru.cos.sim.visualizer.trace.item.Beacon;
import ru.cos.sim.visualizer.trace.item.BezierRule;
import ru.cos.sim.visualizer.trace.item.BoundaryNode;
import ru.cos.sim.visualizer.trace.item.CrossRoad;
import ru.cos.sim.visualizer.trace.item.Gradient;
import ru.cos.sim.visualizer.trace.item.Lane;
import ru.cos.sim.visualizer.trace.item.LaneClosure;
import ru.cos.sim.visualizer.trace.item.Link;
import ru.cos.sim.visualizer.trace.item.Meter;
import ru.cos.sim.visualizer.trace.item.Segment;
import ru.cos.sim.visualizer.trace.item.Sign;
import ru.cos.sim.visualizer.trace.item.TransitionRule;
import ru.cos.sim.visualizer.traffic.graphs.MetersDataManager;
import ru.cos.sim.visualizer.traffic.parser.geometry.Waypoint;
import ru.cos.sim.visualizer.traffic.parser.trace.BezierTransitionRule;
import ru.cos.sim.visualizer.traffic.parser.trace.base.Node;
import ru.cos.sim.visualizer.traffic.parser.trace.base.Staff;
import ru.cos.sim.visualizer.traffic.parser.trace.base.Staff.StaffType;
import ru.cos.sim.visualizer.traffic.parser.trace.base.TransitionRule.GeometryType;
import ru.cos.sim.visualizer.traffic.parser.trace.location.LaneLocation;
import ru.cos.sim.visualizer.traffic.parser.trace.location.LinkLocation;
import ru.cos.sim.visualizer.traffic.parser.trace.location.NodeLocation;
import ru.cos.sim.visualizer.traffic.parser.trace.location.SegmentLocation;
import ru.cos.sim.visualizer.traffic.parser.trace.staff.Background;
import ru.cos.sim.visualizer.traffic.parser.trace.staff.LineDirection;
import ru.cos.sim.visualizer.traffic.utils.RuleCutter;

public class TraceHandler {
	protected HashMap<Integer, Link> links;
	protected HashMap<Integer, INode> nodes;
	
	// Test ???
	public ArrayList<INode> lnodes;
	public ArrayList<Segment> segments;
	public ArrayList<TransitionRule> rules;
	
	public ArrayList<BackgroundForm> backgrounds;
	public MetersDataManager metersManager;
	public ArrayList<LaneClosure> laneClosures;

	public TraceHandler() {
		super();
		
		links = new HashMap<Integer, Link>();
		nodes = new HashMap<Integer, INode>();
		
		lnodes = new ArrayList<INode>();
		segments = new ArrayList<Segment>();
		rules = new ArrayList<TransitionRule>();
		metersManager = new MetersDataManager();
		laneClosures = new ArrayList<LaneClosure>();
		this.backgrounds = new ArrayList<BackgroundForm>();
	}
	
	public Link getLink(int id)
	{
		return links.get(id);
	}
	
	public Segment getSegment(SegmentLocation l)
	{
		return links.get(l.getLinkId()).getSegment(l.getSegmentId());
	}
	
	public Lane getLane(LaneLocation l) {
		return links.get(l.getLinkId()).getSegment(l.getSegmentId()).getLane(l.getLaneId());
	}
	
	public INode getNode(int id)
	{
		return nodes.get(id);
	}
	
	public CrossRoad getCrossRoad(int id){
		return (CrossRoad) nodes.get(id);
	}
	
	public void initLink(ru.cos.sim.visualizer.traffic.parser.trace.Link link)
	{
		Link l  = new Link(link.id());
		links.put(link.id(), l);
	}
	
	public void initSegment(ru.cos.sim.visualizer.traffic.parser.trace.Segment segment)
	{
		Link link = getLink(segment.getLocation().getLinkId());
		Segment seg = new Segment(segment,link);
		segments.add(seg);
		link.addSegment(seg);
	}
	
	public void precompleteSegment(ru.cos.sim.visualizer.traffic.parser.trace.Segment segment)
	{
		LinkLocation l = segment.getLocation();
		Link link = this.getLink(l.getLinkId());
		Segment cs = link.getSegment(segment.id());
		if (cs.precomplete()) {
			BaseSegmentForm form = new BaseSegmentForm(cs);
			cs.setForm(form);
		}
		Segment next = (segment.getNext()>-1) ? link.getSegment(segment.getNext()) : null;
		Segment previous = (segment.getPrevious()>-1) ? link.getSegment(segment.getPrevious()) : null;
		cs.setNext(next);
		cs.setPrevious(previous);
	}
	
	public void completeSegment(ru.cos.sim.visualizer.traffic.parser.trace.Segment segment) {
		LinkLocation l = segment.getLocation();
		Link link = this.getLink(l.getLinkId());
		Segment cs = link.getSegment(segment.id());
		cs.complete();
	}
	
	public void segmentPostComplete(ru.cos.sim.visualizer.traffic.parser.trace.Segment segment){
		LinkLocation l = segment.getLocation();
		Link link = this.getLink(l.getLinkId());
		Segment cs = link.getSegment(segment.id());
		if (cs.postComplete()) {
			BaseSegmentForm form = new BaseSegmentForm(cs);
			cs.setForm(form);
		}
	}
	
	public void initLane(ru.cos.sim.visualizer.traffic.parser.trace.Lane lane){
		Lane l = new Lane(lane.id());
		l.setDesiredLength(lane.getLength());
		l.setWidth(lane.getWidth());
		getLink(lane.getLocation().getLinkId()).
		getSegment(lane.getLocation().getSegmentId()).addLane(l);
	}
	
	public void completeLane(ru.cos.sim.visualizer.traffic.parser.trace.Lane lane)
	{
		SegmentLocation sl = lane.getLocation();
		Segment s = getLink(sl.getLinkId()).getSegment(sl.getSegmentId());
		Lane cl = s.getLane(lane.id());
		Segment nextSegment = s.getNext();
		Segment prevSegment = s.getPrevious();
		if (lane.getNext()>-1 && nextSegment != null) cl.setNext(nextSegment.getLane(lane.getNext()));
		if (lane.getPrevious()>-1 && prevSegment != null) cl.setPrevious(prevSegment.getLane(lane.getPrevious()));
	}
	
	public void initRule(ru.cos.sim.visualizer.traffic.parser.trace.base.TransitionRule rule)
	{
		TransitionRule r = new TransitionRule(rule.id());
		getCrossRoad(rule.getLocation().getNodeId()).addRule(r);
	}
	
	public void initNode(Node node){
		if (node.getType() == Node.Type.CrossRoad) {
			ru.cos.sim.visualizer.traffic.parser.trace.CrossRoad c = (ru.cos.sim.visualizer.traffic.parser.trace.CrossRoad) node;
			CrossRoad cs = new CrossRoad(c.id());
			CrossRoadShape css = new CrossRoadShape(c.geometry.points);
			cs.setForm(css);
			nodes.put(node.id(), cs);
			lnodes.add(cs);
		}
		
		if (node.getType() == Node.Type.DestinationNode || node.getType() == Node.Type.SourceNode ) {
			BoundNodeShape shape = new BoundNodeShape(node);
			BoundaryNode n = new BoundaryNode(node.id(), node, shape);
			nodes.put(node.id(), n);
			lnodes.add(n);
		}
	}
	
	public void completeRule(ru.cos.sim.visualizer.traffic.parser.trace.base.TransitionRule rule)
	{
		NodeLocation location = rule.getLocation();
		TransitionRule r = ((CrossRoad)getNode(location.getNodeId())).getRule(rule.id());
		rules.add(r);
		// Set relations to the distanation and source lanes
		Segment dSegment = getLink(rule.getDestLane().getLinkId()).getFirst();
		rule.getDestLane().setSegmentId(dSegment.id());
		
		Lane dlane = dSegment.getLane(rule.getDestLane().getLaneId());
		r.setDestinationLane(dlane);
		
		Segment sSegment = getLink(rule.getSrcLane().getLinkId()).getLast();
		rule.getSrcLane().setSegmentId(sSegment.id());
		
		Lane slane = sSegment.getLane(rule.getSrcLane().getLaneId());
		r.setSourceLane(slane);
		//
		//Generate rule shapes
		if (rule.getType() == GeometryType.Bezier) {
			float width  = r.getSourceLane().getWidth()*0.6f;
			BezierRule br  = createBezierLane((BezierTransitionRule) rule, width);
			r.setCurve(br);
//			RuleCutter cutter = new RuleCutter(rule.isHasStopLine() ? rule.getStopLine() : 0);
//			if (rule.isHasStopLine()) {
//				TransitionForm[] shapes = cutter.addSplittedBezierCurve(br, new Vector2f(0, 0));
//				r.setMainLight(shapes[0]);
//				r.setWaitLight(shapes[1]);
//				r.setWaitPosition(cutter.getWaitingShape());
//			} else {
//				TransitionForm form = cutter.addSimpleBezierCurve(br, new Vector2f(0, 0)); 
//				r.setMainLight(form);
//			}
		} 
		//
	}
	
	public void initStaff(Staff staff)
	{
		StaffType type =  staff.getStaffType();
		switch (type) {
		case Sign : 
			ru.cos.sim.visualizer.traffic.parser.trace.staff.Sign s = (ru.cos.sim.visualizer.traffic.parser.trace.staff.Sign) staff;
			Sign sign = new Sign(s); 
			SignForm form = new SignForm(sign);
			sign.getSegment().addStaff(form);
			break;
		case Meter : Meter m = new Meter((ru.cos.sim.visualizer.traffic.parser.trace.staff.Meter) staff);
			if (m.getSegment() != null) {
				MeterForm mform = new MeterForm(m);
				m.getSegment().addStaff(mform);
			}
			metersManager.addMeter(m);
			break;
		case Beacon : Beacon b = new Beacon((ru.cos.sim.visualizer.traffic.parser.trace.staff.Beacon) staff); 
			if (b.getSegment() != null) {
				BeaconForm bform = new BeaconForm(b);
				b.getSegment().addStaff(bform);
			}
			break;
		case Gradient : Gradient g = new Gradient((ru.cos.sim.visualizer.traffic.parser.trace.staff.Gradient) staff);
			if (g.getBeginPosition() != null) {
				GradientForm gform1 = new GradientForm(g.getBeginPosition().position,
						g.getBeginPosition().direction, FormType.Start);
				g.getBeginPosition().segment.addStaff(gform1);
			}
			
			if (g.getEndPosition() != null) {
				GradientForm gform2 = new GradientForm(g.getEndPosition().position,
						g.getEndPosition().direction, FormType.Finish);
				g.getEndPosition().segment.addStaff(gform2);
			}
			break;
		case LineDirection : LineDirectionShape shape = new LineDirectionShape((LineDirection) staff);
			SegmentLocation loc = (SegmentLocation) staff.getLocation();
			this.getSegment(loc).addStaff(shape);
			break;
			
		case Background : BackgroundForm background = new BackgroundForm((Background) staff);
			this.backgrounds.add(background);
			break;
		case LaneClosure : LaneClosure lc = new LaneClosure((ru.cos.sim.visualizer.traffic.parser.trace.staff.LaneClosure) staff);
			this.laneClosures.add(lc);
			break;
			
		}
	}
	
	private BezierRule createBezierLane(BezierTransitionRule br, float width)
	{
		Vector2f[] points = new Vector2f[4];
		ArrayList<Waypoint> wps = br.getPoints();
		for (int i = 0 ; i < wps.size(); i++)
		{
			points[i] = new Vector2f(wps.get(i).x,wps.get(i).y);
		}
		BezierRule r = new BezierRule(points, width);
		r.setDesiredLength(br.getLength());
		return r;
	}

	public MetersDataManager getMetersManager() {
		return metersManager;
	}

	public void doUpdate(){ 
		if (metersManager != null) {
			metersManager.checkRequests();
		}
	}

}
