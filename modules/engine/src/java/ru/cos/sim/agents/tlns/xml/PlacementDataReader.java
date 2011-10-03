package ru.cos.sim.agents.tlns.xml;

import java.util.HashSet;
import java.util.Set;

import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.agents.tlns.data.PlacementData;
import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.utils.Pair;

public class PlacementDataReader {

	public static final Namespace NS = MDFReader.MDF_NAMESPACE;

	public static final String TURN_TR_GROUP_ID = "turnTRGroupId";
	public static final String TRANSITION_RULE = "TransitionRule";
	public static final String TRANSITION_RULE_ID = "transitionRuleId";
	public static final String POSITION = "position";

	public static PlacementData read(Element placementElement) {
		PlacementData placementData = new PlacementData();
		
		Set<Integer> turnTRGroupIds = new HashSet<Integer>();
		for (Object turnTRGroupIdObj:placementElement.getChildren(TURN_TR_GROUP_ID,NS)){
			Element turnTRGroupIdElement = (Element)turnTRGroupIdObj;
			turnTRGroupIds.add(Integer.parseInt(turnTRGroupIdElement.getText()));
		}
		placementData.setTrurnTRGroups(turnTRGroupIds);
		
		Set<Pair<Integer, Float>> transitionRulePositions = new HashSet<Pair<Integer,Float>>();
		for (Object transitionRuleObj:placementElement.getChildren(TRANSITION_RULE,NS)){
			Element transitionRuleElement = (Element)transitionRuleObj;
			
			Element transitionRuleIdElement = transitionRuleElement.getChild(TRANSITION_RULE_ID,NS);
			int transitionRuleId = Integer.parseInt(transitionRuleIdElement.getText());
			
			Element positionElement = transitionRuleElement.getChild(POSITION,NS);
			float position = Float.parseFloat(positionElement.getText());
			
			transitionRulePositions.add(new Pair<Integer, Float>(transitionRuleId, position));
		}
		placementData.setTransitionRules(transitionRulePositions);
		
		return placementData;
	}

}
