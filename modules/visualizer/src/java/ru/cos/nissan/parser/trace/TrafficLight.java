package ru.cos.nissan.parser.trace;

import org.jdom.Element;

import ru.cos.nissan.parser.Parser;
import ru.cos.nissan.parser.base.Entity;
import ru.cos.nissan.parser.trace.base.TransitionRule;
import ru.cos.nissan.parser.utils.ItemParser;

public class TrafficLight extends Entity{
	
	public static String Name = "TrafficLight";
	public static String ChapterName = "TrafficLights";
	
	private static enum Fields {
		TransitionRule,
		transitionRuleId,
		Placement,
		position,
		name
	}
	
	protected String name;
	protected long id;
	protected TransitionRule rule;
	
	public TrafficLight(Element e)
	{
		super(e);
		Element transitionRule = e.getChild(Fields.Placement.name(),Parser.getCurrentNamespace()).
				getChild(Fields.TransitionRule.name(),Parser.getCurrentNamespace());
		if (transitionRule != null) {
			this.rule = new TransitionRule(ItemParser.getInteger(transitionRule, Fields.transitionRuleId.name()));
		}
	}
}
