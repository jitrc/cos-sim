/**
 * 
 */
package ru.cos.sim.road.node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ru.cos.sim.road.exceptions.RoadNetworkException;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.link.Link;

/**
 * Regular road node that connects incoming and outgoing links by means of transition rules.
 * @author zroslaw
 */
public class RegularNode extends Node{

	/**
	 * All node transition rules.
	 */
	protected Map<Integer,TransitionRule> tRules = new HashMap<Integer, TransitionRule>();
	
	/**
	 * Map of Turn Transition Rule groups in the node.
	 */
	protected Map<Integer,TurnTRGroup> turnTRGroups = new HashMap<Integer, TurnTRGroup>();
	
	/**
	 * Lane correspondence matrix.
	 */
	protected Map<Integer,Map<Integer,Set<NodeFork>>> laneCorrespondanceMatrix = 
		new HashMap<Integer, Map<Integer,Set<NodeFork>>>();
	/**
	 * Set of incoming links.
	 */
	protected Set<Link> incomingLinks = new HashSet<Link>();
	
	/**
	 * Set of outgoing links.
	 */
	protected Set<Link> outgoingLinks = new HashSet<Link>();

	/**
	 * Constructor by node agentId.
	 * @param agentId unique node agentId
	 */
	public RegularNode(int id) {
		super(id);
	}

	/**
	 * Retrieve set of appropriate node forks from which transition from incoming to outgoing link is allowed.
	 * @param incLinkId incoming link agentId
	 * @param outLinkId outgoing link agentId
	 * @return set of appropriate node forks
	 * @throws RoadNetworkException if incoming link agentId is incorrect
	 */
	public Set<NodeFork> getAppropriateNodeForks(int incLinkId, int outLinkId){
		 Map<Integer,Set<NodeFork>> fromLinkSubMatrix = laneCorrespondanceMatrix.get(incLinkId);
		 if (fromLinkSubMatrix==null)
			 throw new RoadNetworkException("There is no information " +
			 		"about incoming link "+incLinkId+" in the lane correspondance matrix.");
		 Set<NodeFork> lanes = fromLinkSubMatrix.get(outLinkId);
		 if (lanes==null)
			 lanes = new HashSet<NodeFork>();
		 return lanes;
	}
	
	/**
	 * Initialize all node's internal data structures.
	 * Initialize lane correspondence matrix.<br>
	 * <b>All related links objects and internal transition rules must be completely 
	 * initialized before this method will be invoked.</b>
	 */
	public void init(){
		for (TransitionRule tr:tRules.values()){
			NodeFork nodeFork = (NodeFork) tr.getPrev();
			NodeJoin nodeJoin = (NodeJoin) tr.getNext();

			Lane incLane = (Lane)nodeFork.getPrev();
			Lane outLane = (Lane)nodeJoin.getNext();
			
			Link incLink = incLane.getSegment().getLink();
			Link outLink = outLane.getSegment().getLink();
			
			Map<Integer,Set<NodeFork>> fromLinkSubMatrix = laneCorrespondanceMatrix.get(incLink.getId());
			if (fromLinkSubMatrix==null){
				fromLinkSubMatrix = new HashMap<Integer, Set<NodeFork>>();
				laneCorrespondanceMatrix.put(incLink.getId(), fromLinkSubMatrix);
			}
			
			Set<NodeFork> lanes = fromLinkSubMatrix.get(outLink.getId());
			if (lanes==null){
				lanes = new HashSet<NodeFork>();
				fromLinkSubMatrix.put(outLink.getId(), lanes);
			}
			
			lanes.add(nodeFork);
			incomingLinks.add(incLink);
			outgoingLinks.add(outLink);			
		}
	}

	public Set<Link> getIncomingLinks() {
		return incomingLinks;
	}

	public Set<Link> getOutgoingLinks() {
		return outgoingLinks;
	}

	public Map<Integer, TransitionRule> getTRules() {
		return tRules;
	}

	public void setTRules(Map<Integer, TransitionRule> tRules) {
		this.tRules = tRules;
	}

	@Override
	public final NodeType getNodeType() {
		return NodeType.RegularNode;
	}

	/**
	 * Retrieve set of links that can be reached by vehicle when it enters the node from specified link.
	 * Set of link that can be accessed from link with specified id.
	 * @param meterId id of this node's incoming link
	 * @return set of links to which there are exist paths (transition rules) from link with specified id 
	 */
	public Set<Integer> getAccessibleLinks(int incomingLinkId) {
		 Map<Integer,Set<NodeFork>> fromLinkSubMatrix = laneCorrespondanceMatrix.get(incomingLinkId);
		 if (fromLinkSubMatrix==null)
			 throw new RoadNetworkException("There is no information " +
			 		"about incoming link "+incomingLinkId+" in the lane correspondance matrix.");
		return fromLinkSubMatrix.keySet();
	}

	public TransitionRule getTransitionRule(int transitionRuleId) {
		return tRules.get(transitionRuleId);
	}

	public void setTurnTRGroups(Map<Integer, TurnTRGroup> turnTRGroups) {
		this.turnTRGroups = turnTRGroups;
	}
	
	public Map<Integer, TurnTRGroup> getTurnTRGroups() {
		return turnTRGroups;
	}

	public TurnTRGroup getTurnTRGroup(int turnTrGroupId){
		return turnTRGroups.get(turnTrGroupId);
	}

	public Set<Lane> getAppropriateLanes(int incomingLinkId, int ougtoingLinkId) {
		Set<Lane> appropriateLanes = new HashSet<Lane>();
		for (NodeFork nodeFork:laneCorrespondanceMatrix.get(incomingLinkId).get(ougtoingLinkId)){
			appropriateLanes.add((Lane) nodeFork.getPrev());
		}
		return appropriateLanes;
	}
}
