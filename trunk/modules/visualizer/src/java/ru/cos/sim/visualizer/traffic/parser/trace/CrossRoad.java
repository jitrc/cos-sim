package ru.cos.sim.visualizer.traffic.parser.trace;

import java.util.List;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.Parser;
import ru.cos.sim.visualizer.traffic.parser.ParserCollector;
import ru.cos.sim.visualizer.traffic.parser.trace.base.Node;
import ru.cos.sim.visualizer.traffic.parser.trace.base.TransitionRule;
import ru.cos.sim.visualizer.traffic.parser.trace.base.TransitionRule.GeometryType;

public class CrossRoad extends Node  {
	public static String Name = "RegularNode";
	protected ParserCollector collector;
	public NodeGeometry geometry;
	
	public CrossRoad(Element e) {
		super(e,Type.CrossRoad);
		collector = Parser.getParserCollector();
		this.geometry = new NodeGeometry(e.getChild(NodeGeometry.Name,Parser.getCurrentNamespace()));
		
		List<Element> rules = e.getChild(TransitionRule.ChapterName,Parser.getCurrentNamespace()
				).getChildren(TransitionRule.Name,Parser.getCurrentNamespace());
		GeometryType type;
		for (Element rule : rules)
		{
			type = TransitionRule.getType(rule);
			if (type == GeometryType.Bezier) {
				collector.rules.add(new BezierTransitionRule(rule,id()));
			} else {
				collector.rules.add(new PolylineTransitionRule(rule,id()));
			}
			
		}
	}


}
