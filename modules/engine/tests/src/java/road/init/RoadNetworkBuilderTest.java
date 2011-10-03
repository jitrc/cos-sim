package road.init;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import ru.cos.cs.lengthy.Observation;
import ru.cos.cs.lengthy.impl.CaseRouter;
import ru.cos.sim.road.RoadNetwork;
import ru.cos.sim.road.init.RoadNetworkBuilder;
import ru.cos.sim.road.init.data.AbstractNodeData;
import ru.cos.sim.road.init.data.LaneData;
import ru.cos.sim.road.init.data.LinkData;
import ru.cos.sim.road.init.data.RegularNodeData;
import ru.cos.sim.road.init.data.RoadNetworkData;
import ru.cos.sim.road.init.data.SegmentData;
import ru.cos.sim.road.init.data.TransitionRuleData;
import ru.cos.sim.road.init.data.TrapeziumSegmentData;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.link.Link;
import ru.cos.sim.road.link.Segment;
import ru.cos.sim.road.node.Node;
import ru.cos.sim.road.node.NodeFork;
import ru.cos.sim.road.node.NodeForkPoint;
import ru.cos.sim.road.node.NodeJoin;
import ru.cos.sim.road.node.NodeJoinPoint;
import ru.cos.sim.road.node.RegularNode;
import ru.cos.sim.road.node.TransitionRule;
import ru.cos.sim.road.objects.RoadObject;

/**
 * @author zroslaw
 *
 */
public class RoadNetworkBuilderTest {

	public static final int SEGMENT_ID = 1;
	public static final int LINK_ID = 2;
	public static final int TR_ID = 3;
	public static final int NODE_ID = 4;

	public static final float LANE_WIDTH = 3.1f;
	public static final float TR_WIDTH = 2.7f;
	
	RoadNetworkData roadNetworkData;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		roadNetworkData = new RoadNetworkData();
		
		LaneData laneData = new LaneData();
		laneData.setIndex(0);
		laneData.setLength(100);
		laneData.setWidth(LANE_WIDTH);
		laneData.setLength(100);
		laneData.setNextLaneIndex(-1);
		laneData.setPrevLaneIndex(-1);
		
		TrapeziumSegmentData segmentData = new TrapeziumSegmentData();
		segmentData.setId(SEGMENT_ID);
		segmentData.setLanes(new LaneData[]{laneData});
		segmentData.setLength(100);
		segmentData.setNextSegmentId(-1);
		segmentData.setPrevSegmentId(-1);
		segmentData.setTrapeziumShift(0);
		
		LinkData linkData = new LinkData();
		linkData.setId(LINK_ID);
		linkData.setLength(100);
		linkData.setDestinationNodeId(NODE_ID);
		linkData.setSourceNodeId(NODE_ID);
		Map<Integer, SegmentData> segments = new TreeMap<Integer, SegmentData>();
		segments.put(segmentData.getId(), segmentData);
		linkData.setSegments(segments);
		
		
		TransitionRuleData trData = new TransitionRuleData();
		trData.setId(TR_ID);
		trData.setLength(20);
		trData.setWidth(TR_WIDTH);
		trData.setDestinationLinkId(LINK_ID);
		trData.setDestinationLaneIndex(0);
		trData.setSourceLinkId(LINK_ID);
		trData.setSourceLaneIndex(0);
		
		RegularNodeData nodeData = new RegularNodeData();
		nodeData.setId(NODE_ID);
		Map<Integer, TransitionRuleData> transitionRules = new TreeMap<Integer, TransitionRuleData>();
		transitionRules.put(trData.getId(), trData);
		nodeData.setTransitionRules(transitionRules);
		
		Map<Integer,AbstractNodeData> nodes = new HashMap<Integer, AbstractNodeData>();
		nodes.put(nodeData.getId(), nodeData);
		Map<Integer,LinkData> links = new HashMap<Integer, LinkData>();
		links.put(linkData.getId(), linkData);
		
		roadNetworkData.setLinks(links);
		roadNetworkData.setNodes(nodes);
	}

	/**
	 * Test method for {@link ru.cos.sim.road.init.RoadNetworkBuilder#build(ru.cos.sim.road.init.data.RoadNetworkData)}.
	 */
	@Test
	public void testBuild() {
		RoadNetwork roadNetwork = RoadNetworkBuilder.build(roadNetworkData);
		
		Map<Integer,Link> links = roadNetwork.getLinks();
		Link link = links.get(LINK_ID);
		assertTrue(links.size()==1);
		assertTrue(link!=null);
		assertEquals(100, link.getLength(),0);
		
		Map<Integer, Segment> segments = link.getSegments();
		Segment segment = segments.get(SEGMENT_ID);
		assertTrue(segments.size()==1);
		assertTrue(segment!=null);
		assertTrue(link.getFirstSegment()==segment);
		assertTrue(link.getLastSegment()==segment);
		assertEquals(100, segment.getLength(),0);
		
		Lane[] lanes = segment.getLanes();
		assertTrue(lanes.length==1);
		Lane lane = lanes[0];
		assertTrue(lane.getIndex()==0);
		assertEquals(100, lane.getLength(),0);
		assertEquals(LANE_WIDTH, lane.getWidth(),0);
		assertTrue(lane.getNext() instanceof NodeFork);
		assertTrue(lane.getPrev() instanceof NodeJoin);
		
		Map<Integer,Node> nodes = roadNetwork.getNodes();
		assertTrue(nodes.size()==1);
		RegularNode node = (RegularNode) nodes.get(NODE_ID);
		assertTrue(node!=null);
		assertTrue(node.getIncomingLinks().size()==1);
		assertTrue(node.getIncomingLinks().contains(link));
		assertTrue(node.getOutgoingLinks().size()==1);
		assertTrue(node.getOutgoingLinks().contains(link));
		assertTrue(node.getAppropriateNodeForks(LINK_ID, LINK_ID).contains(lane.getNext()));
		
		Map<Integer,TransitionRule> tRules = node.getTRules();
		TransitionRule tr = tRules.get(TR_ID);
		assertTrue(tRules.size()==1);
		assertTrue(tr!=null);
		assertEquals(20, tr.getLength(),0);
		assertEquals(TR_WIDTH, tr.getWidth(),0);
		assertTrue(tr.getPrev() instanceof NodeFork);
		assertTrue(((NodeFork)tr.getPrev()).getForkedLengthies().contains(tr));
		assertTrue(((NodeFork)tr.getPrev()).getForkedLengthies().size()==1);
		assertTrue(tr.getNext() instanceof NodeJoin);
		assertTrue(((NodeJoin)tr.getNext()).getJoinedLengthies().contains(tr));
		assertTrue(((NodeJoin)tr.getNext()).getJoinedLengthies().size()==1);
		assertTrue(((NodeJoin)tr.getNext()).getNext()==lane);
		assertTrue(((NodeFork)tr.getPrev()).getPrev()==lane);
		
		CaseRouter router = new CaseRouter();
		router.addCase((NodeFork)tr.getPrev(), tr);
		router.addCase((NodeJoin)tr.getNext(), tr);
		
		List<Observation> observations = lane.observeForward(90, 40, router);
		assertTrue(observations.size()==2);
		Observation observation = observations.get(0);
		RoadObject roadObject = (RoadObject) observation.getPoint();
		NodeForkPoint nfp = (NodeForkPoint) roadObject;
		assertTrue(nfp.getNodeFork()==tr.getPrev());
		assertEquals(10, observation.getDistance(),0);
		
		observation = observations.get(1);
		NodeJoinPoint njp = (NodeJoinPoint) observation.getPoint();
		assertTrue(njp.getNodeJoin()==tr.getNext());
		assertEquals(30, observation.getDistance(),0);
		
	}

}
