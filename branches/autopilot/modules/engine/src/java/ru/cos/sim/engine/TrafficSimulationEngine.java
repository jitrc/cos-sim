/**
 * 
 */
package ru.cos.sim.engine;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ru.cos.cs.agents.framework.Agent;
import ru.cos.cs.agents.framework.SimulationEngine;
import ru.cos.cs.lengthy.objects.MultiPoint;
import ru.cos.cs.lengthy.objects.Point;
import ru.cos.cs.lengthy.objects.Point.PointType;
import ru.cos.sim.agents.TrafficAgent;
import ru.cos.sim.agents.TrafficAgentData;
import ru.cos.sim.agents.TrafficAgentFactory;
import ru.cos.sim.agents.destination.Destination;
import ru.cos.sim.communication.FrameProperties;
import ru.cos.sim.communication.dto.StatisticsData;
import ru.cos.sim.meters.MeterFactory;
import ru.cos.sim.meters.data.MeterData;
import ru.cos.sim.meters.impl.AbstractMeter;
import ru.cos.sim.parameters.ModelParameters;
import ru.cos.sim.road.RoadNetwork;
import ru.cos.sim.road.RoadTrajectory;
import ru.cos.sim.road.init.RoadNetworkBuilder;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.link.Link;
import ru.cos.sim.road.link.Segment;
import ru.cos.sim.road.node.DestinationNode;
import ru.cos.sim.road.node.Node;
import ru.cos.sim.road.node.Node.NodeType;
import ru.cos.sim.road.node.RegularNode;
import ru.cos.sim.road.node.TransitionRule;
import ru.cos.sim.road.objects.RoadObject;
import ru.cos.sim.road.objects.RoadObject.RoadObjectType;
import ru.cos.sim.services.ServiceLocator;

/**
 * 
 * @author zroslaw
 */
public class TrafficSimulationEngine extends SimulationEngine{
	
	/**
	 * Set of road trajectories that are now in the frame.<br>
	 * Variable can be considered as compiled representation of frame properties.
	 */
	private Set<RoadTrajectory> frameTrajectories = new HashSet<RoadTrajectory>();
	
	private Map<Integer, AbstractMeter> meters = new TreeMap<Integer, AbstractMeter>(); 
	
	private TrafficDataCollector dataCollector = new TrafficDataCollector();
	
	
	public TrafficSimulationEngine(){
		
	}
	
	/**
	 * Initialize traffic simulation engine by traffic model definition
	 * @param def
	 */
	public void init(TrafficModelDefinition def){
		
		// init universe
		RoadNetwork roadNetwork = RoadNetworkBuilder.build(def.getRoadNetworkData());
		RoadNetworkUniverse universe = new RoadNetworkUniverse(roadNetwork);
		this.universe = universe;
		
		// init model parameters
		ModelParameters modelParameters = new ModelParameters(def.getModelParametersData());
		
		// init services
		ServiceLocator.init(def, roadNetwork, modelParameters, universe.getClock());
		
		// init agents
		Set<TrafficAgentData> agentsData = def.getAgentsData();
		for (TrafficAgentData agentData:agentsData){
			TrafficAgent agent = TrafficAgentFactory.createAgent(agentData, universe);
			reviveAgent(agent);
		}
		// init destination agents that are not explicitly specified in the model definition
		// but they must exist for each destination node
		for (Node node:roadNetwork.getNodes().values()){
			if (node.getNodeType().equals(Node.NodeType.DestinationNode)){
				DestinationNode dstNode = (DestinationNode) node;
				Destination destination = new Destination();
				destination.setDestinationNode(dstNode);
				reviveAgent(destination);
			}
		}
		
		// init meters
		Set<MeterData> meterDatas = def.getMetersData();
		if (meterDatas!=null){
			for (MeterData meterData:meterDatas){
				AbstractMeter meter = MeterFactory.createMeter(meterData, universe);
				dataCollector.initMeter(meter);
				meters.put(meter.getMeterId(), meter);
				reviveAgent(meter);
			}
		}
		
	}
	
	@Override
	protected void finishAgentAct(Agent agent, float dt) {
		dataCollector.examine(agent);
	}

	@Override
	protected void startStep(float dt) {
		dataCollector.startStep(dt);
	}
	
	public boolean isStopCriterionSatisfied() {
		return dataCollector.getNumberOfAliveOrigins()==0 &&
				dataCollector.getNumberOfVehicles()==0;
			
	}

	/**
	 * Set properties of the visualizer frame.
	 * @see TrafficSimulationEngine#frameTrajectories
	 * @param frameProperties
	 */
	public void setFrameProperties(FrameProperties frameProperties){
		RoadNetworkUniverse rnUniverse = (RoadNetworkUniverse) universe;
		// create new set of trajectories
		frameTrajectories = new HashSet<RoadTrajectory>();
		// add all transition rules from regular nodes to frame trajectories
		for (Integer nodeId:frameProperties.getNodeIds()){
			Node node = rnUniverse.getNode(nodeId);
			if (node!=null && node.getNodeType()==NodeType.RegularNode){
				Collection<TransitionRule> transitionRules = ((RegularNode)node).getTRules().values();
				frameTrajectories.addAll(transitionRules);
			}
		}
		// add all lanes from all segments described in frame properties
		Map<Integer, Set<Integer>> linkSegments = frameProperties.getLinkSegments();
		for (Integer linkId:linkSegments.keySet()){
			Link link = rnUniverse.getLink(linkId);
			if (link==null) continue;
			for (Integer segmentId:linkSegments.get(linkId)){
				Segment segment = link.getSegment(segmentId);
				if (segment==null) continue;
				for (Lane lane:segment.getLanes()){
					frameTrajectories.add(lane);
				}
			}
		}
	}
	
	/**
	 * Get all dynamic road objects that are visible in the visualizer window frame.<br>
	 * It is required to set frame properties first before information about dynamic
	 * objects will be gathered.
	 * <p>
	 * For now, following road objects are considered as dynamic:
	 * <ul>
	 * 	<li>Vehicle</li>
	 * 	<li>TrafficLight</li>
	 * </ul>
	 */
	public Set<RoadObject> getDynamicObjects(){
		Set<RoadObject> result = new HashSet<RoadObject>();
		// iterate through set of frame trajectories and gather all dynamic road objects
		for (RoadTrajectory trajectory:frameTrajectories){
			for (Point point:trajectory.getPoints()){
				// for multi points check for all its internal regular points
				if (point.getPointType()==PointType.MultiPoint){
					MultiPoint multiPoint = (MultiPoint)point;
					for(Point regPoint:multiPoint.getPoints()){
						RoadObject roadObject = (RoadObject)regPoint;
						RoadObjectType type = roadObject.getRoadObjectType();
						if (type==RoadObjectType.Vehicle || type==RoadObjectType.TrafficLight)
							result.add((RoadObject) regPoint);
					}
				}else{
					RoadObject roadObject = (RoadObject)point;
					RoadObjectType type = roadObject.getRoadObjectType();
					if (type==RoadObjectType.Vehicle || type==RoadObjectType.TrafficLight)
						result.add((RoadObject) point);
				}
			}
		}
		return result;
	}
	
	/**
	 * Retrieve set of meters installed in the system.
	 * @return
	 */
	public Set<AbstractMeter> getMeters(){
		Set<AbstractMeter> metersSet = new HashSet<AbstractMeter>();
		metersSet.addAll(meters.values());
		return metersSet;
	}
	
	/**
	 * Retrieve specificmeter instance by its id.
	 * @param meterId meter id
	 * @return meter instance or null, if it is not found
	 */
	public AbstractMeter getMeter(int meterId){
		return meters.get(meterId);
	}

	public StatisticsData getStatistics() {
		StatisticsData statisticsData = new StatisticsData();
		statisticsData.setUniverseTime(getTime());
		statisticsData.setAverageSpeed(dataCollector.getAverageSpeed());
		statisticsData.setNumberOfAliveOrigins(dataCollector.getNumberOfAliveOrigins());
		statisticsData.setNumberOfArrivedVehicles(dataCollector.getNumberOfArrivedVehicles());
		statisticsData.setNumberOfVehicles(dataCollector.getNumberOfVehicles());
		statisticsData.setTotalTime(dataCollector.getTotalTime());
		statisticsData.setTotalStops(dataCollector.getTotalStops());
		return statisticsData;
	}
	
}
