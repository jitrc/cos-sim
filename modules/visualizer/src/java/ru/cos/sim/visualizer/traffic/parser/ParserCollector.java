package ru.cos.sim.visualizer.traffic.parser;

import java.util.ArrayList;

import ru.cos.sim.visualizer.traffic.parser.trace.Lane;
import ru.cos.sim.visualizer.traffic.parser.trace.Link;
import ru.cos.sim.visualizer.traffic.parser.trace.Segment;
import ru.cos.sim.visualizer.traffic.parser.trace.base.Node;
import ru.cos.sim.visualizer.traffic.parser.trace.base.Staff;
import ru.cos.sim.visualizer.traffic.parser.trace.base.TransitionRule;

public class ParserCollector {
	public ArrayList<Link> links;
	public ArrayList<Segment> segments;
	public ArrayList<Lane> lanes;
	public ArrayList<Node> nodes;
	public ArrayList<TransitionRule> rules;
	
	public ArrayList<Staff> staff;

	public ParserCollector() {
		super();
		
		this.nodes = new ArrayList<Node>();
		this.links = new ArrayList<Link>();
		this.segments = new ArrayList<Segment>();
		this.lanes = new ArrayList<Lane>();
		this.staff = new ArrayList<Staff>();
		this.rules = new ArrayList<TransitionRule>();
	}
}
