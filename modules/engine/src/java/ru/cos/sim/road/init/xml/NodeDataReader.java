/**
 * 
 */
package ru.cos.sim.road.init.xml;

import java.util.Map;
import java.util.TreeMap;


import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.road.init.data.AbstractNodeData;
import ru.cos.sim.road.init.data.DestinationNodeData;
import ru.cos.sim.road.init.data.OriginNodeData;
import ru.cos.sim.road.init.data.RegularNodeData;
import ru.cos.sim.road.init.data.TransitionRuleData;
import ru.cos.sim.road.init.data.TurnTRGroupData;
import ru.cos.sim.road.init.xml.exceptions.XMLReaderException;
import ru.cos.sim.road.node.Node.NodeType;

/**
 * 
 * @author zroslaw
 */
public class NodeDataReader {
	
	private static Namespace NS = MDFReader.MDF_NAMESPACE;
	
	public static final String ID = "id"; 
	public static final String NAME = "name"; 
	public static final String TRANSITION_RULE = "TransitionRule"; 
	public static final String OUTGOING_LINK_ID = "outgoingLinkId"; 
	public static final String INCOMING_LINK_ID = "incomingLinkId";
	public static final String TRANSITION_RULES = "TransitionRules"; 
	public static final String TURN_TR_GROUPS = "TurnTRGroups"; 
	public static final String TURN_TR_GROUP = "TurnTRGroup"; 

	public static AbstractNodeData read(Element nodeElement) {
		AbstractNodeData nodeData = null;

		NodeType nodeType = NodeType.valueOf(nodeElement.getName());
		
		switch(nodeType){
			case RegularNode:{
				RegularNodeData regularNodeData = new RegularNodeData();
				// read transition rules
				Map<Integer, TransitionRuleData> tRulesData = new TreeMap<Integer, TransitionRuleData>();
				for (Object trObj:nodeElement.getChild(TRANSITION_RULES, NS).getChildren(TRANSITION_RULE, NS)){
					Element trElement = (Element)trObj;
					TransitionRuleData trData = TransitionRuleDataReader.read(trElement);
					tRulesData.put(trData.getId(), trData);
				}
				regularNodeData.setTransitionRules(tRulesData);
				// read Turn Transition Rule Groups, if any
				Element turnTRgroupsElement = nodeElement.getChild(TURN_TR_GROUPS, NS);
				if (turnTRgroupsElement!=null){
					Map<Integer, TurnTRGroupData> turnTRGroupsDatas = new TreeMap<Integer, TurnTRGroupData>();
					for (Object turnTRGroupObj:nodeElement.getChild(TURN_TR_GROUPS, NS).getChildren(TURN_TR_GROUP, NS)){
						Element turnTRGroupElement = (Element)turnTRGroupObj;
						TurnTRGroupData turnTRGroupData = TurnTRGroupDataReader.read(turnTRGroupElement);
						turnTRGroupsDatas.put(turnTRGroupData.getId(), turnTRGroupData);
					}
					regularNodeData.setTurnTRGroups(turnTRGroupsDatas);
				}
				
				nodeData = regularNodeData;
				break;
			}
			case OriginNode:{
				OriginNodeData originNodeData = new OriginNodeData();
				Element outgoingLinkElement = nodeElement.getChild(OUTGOING_LINK_ID, NS);
				originNodeData.setOutgoingLinkId(Integer.parseInt(outgoingLinkElement.getText()));
				nodeData = originNodeData;
				break;
			}
			case DestinationNode:{
				DestinationNodeData destinationNodeData = new DestinationNodeData();
				Element incomingLinkElement = nodeElement.getChild(INCOMING_LINK_ID, NS);
				destinationNodeData.setIncomingLinkId(Integer.parseInt(incomingLinkElement.getText()));
				nodeData = destinationNodeData;
				break;
			}
			default: throw new XMLReaderException("Unexpected node type "+nodeType);
		}

		Element idElement = nodeElement.getChild(ID, NS);
		nodeData.setId(Integer.parseInt(idElement.getText()));

		Element nameElement = nodeElement.getChild(NAME, NS);
		if (nameElement!=null)
			nodeData.setName(nameElement.getText());

		return nodeData;
	}

}
