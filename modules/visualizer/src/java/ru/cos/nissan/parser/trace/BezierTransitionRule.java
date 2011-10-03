package ru.cos.nissan.parser.trace;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import ru.cos.nissan.parser.Parser;
import ru.cos.nissan.parser.geometry.Waypoint;
import ru.cos.nissan.parser.trace.base.TransitionRule;
import ru.cos.nissan.parser.utils.ItemParser;

public class BezierTransitionRule extends TransitionRule {
	private static enum Fields {
		BezierCurveGeometry,
		ReferencePoints,
		Point,
		x,
		y
	}
	private ArrayList<Waypoint> points;
	
	public BezierTransitionRule(Element e,int nodeId) {
		super(e,nodeId);
		this.points = new ArrayList<Waypoint>();
		List<Element> points = e.getChild(Fields.BezierCurveGeometry.name(),Parser.getCurrentNamespace()).
		getChild(Fields.ReferencePoints.name(),Parser.getCurrentNamespace()).
		getChildren(Fields.Point.name(),Parser.getCurrentNamespace());
		
		for (Element point : points) {
			this.points.add(new Waypoint(ItemParser.getFloat(point, Fields.x.name()),
					ItemParser.getFloat(point, Fields.y.name())));
		}
	}

	public ArrayList<Waypoint> getPoints() {
		return points;
	}
	
}
