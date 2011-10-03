/**
 * 
 */
package ru.cos.sim.engine;

import java.util.HashSet;
import java.util.Set;

import ru.cos.cs.agents.framework.asynch.AsynchSimulationEngine;
import ru.cos.cs.agents.framework.asynch.Message;
import ru.cos.sim.agents.tlns.TrafficLight;
import ru.cos.sim.communication.FrameProperties;
import ru.cos.sim.communication.dto.AbstractDTO;
import ru.cos.sim.communication.dto.FrameData;
import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.communication.dto.MeterShortData;
import ru.cos.sim.communication.dto.StatisticsData;
import ru.cos.sim.communication.dto.TrafficLightDTO;
import ru.cos.sim.communication.dto.VehicleDTO;
import ru.cos.sim.communication.messages.AbstractMessage;
import ru.cos.sim.communication.messages.AvailableMetersMessage;
import ru.cos.sim.communication.messages.FrameDataMessage;
import ru.cos.sim.communication.messages.FramePropertiesMessage;
import ru.cos.sim.communication.messages.MeterDataMessage;
import ru.cos.sim.communication.messages.MeterDataRequest;
import ru.cos.sim.exceptions.TrafficSimulationException;
import ru.cos.sim.meters.impl.AbstractMeter;
import ru.cos.sim.road.objects.RoadObject;
import ru.cos.sim.vehicle.Vehicle;

/**
 * Asynchronous version of traffic simulation engine.<br>
 * @see AsynchSimulationEngine
 * @author zroslaw
 */
public class TrafficAsynchSimulationEngine extends AsynchSimulationEngine<TrafficSimulationEngine> {

	public TrafficAsynchSimulationEngine() {
		super(new TrafficSimulationEngine());
	}

	@Override
	protected void finishStep() {
		if (simulationEngine.isStopCriterionSatisfied())
			stopSimulation();
	}

	@Override
	protected void processIncomingMessage(Message message) {
		AbstractMessage abstractMessage = (AbstractMessage)message;
		switch (abstractMessage.getMessageType()){
		case REQUEST_FRAME_DATA:
			processFrameDataRequest();
			break;
		case REQUEST_METER_DATA:
			MeterDataRequest meterDataRequest = (MeterDataRequest)abstractMessage;
			processMeterDataRequest(meterDataRequest);
			break;
		case REQUEST_AVAILABLE_METERS:
			processAvailableMetersRequest();
			break;
		case FRAME_PROPERTIES:
			FramePropertiesMessage frameProperiesMessage = (FramePropertiesMessage)abstractMessage;
			processFramePropertiesMessage(frameProperiesMessage);
			break;
		default:
			throw new TrafficSimulationException("Unexpected request message type "+abstractMessage.getMessageType());
		}
	}

	private void processFramePropertiesMessage(FramePropertiesMessage frameProperiesMessage) {
		FrameProperties frameProperties = frameProperiesMessage.getFrameProperties();
		simulationEngine.setFrameProperties(frameProperties);
	}

	private void processFrameDataRequest() {
		FrameData frameData = new FrameData();
		Set<RoadObject> dynamicRoadObjects = simulationEngine.getDynamicObjects();
		
		Set<AbstractDTO> abstractDTOs = new HashSet<AbstractDTO>();
		// transform dynamic road objects to their DTO instances
		for (RoadObject roadObject:dynamicRoadObjects){
			switch(roadObject.getRoadObjectType()){
			case Vehicle:
				Vehicle vehicle = (Vehicle)roadObject;
				abstractDTOs.add(new VehicleDTO(vehicle));
				break;
			case TrafficLight:
				TrafficLight trafficLight = (TrafficLight)roadObject;
				abstractDTOs.add(new TrafficLightDTO(trafficLight));
				break;
			default:
				throw new TrafficSimulationException("Unexpected dynamic road object type "+roadObject.getRoadObjectType());
			}
		}
		frameData.setDataTransferObjects(abstractDTOs);
		
		// get statistics data
		StatisticsData statisticsData = simulationEngine.getStatistics();
		frameData.setStatisticsData(statisticsData);
		
		FrameDataMessage frameMessage = new FrameDataMessage();
		frameMessage.setFrameData(frameData);
		outQueue.add(frameMessage);
	}

	private void processAvailableMetersRequest() {
		AvailableMetersMessage availableMetersMessage = new AvailableMetersMessage();
		
		Set<MeterShortData> meterShortDatas = new HashSet<MeterShortData>();
		for(AbstractMeter meter:simulationEngine.getMeters()){
			MeterShortData meterShortData = new MeterShortData(meter);
			meterShortDatas.add(meterShortData);
		}
		
		availableMetersMessage.setMeterShortData(meterShortDatas);
		outQueue.add(availableMetersMessage);
	}

	private void processMeterDataRequest(MeterDataRequest meterDataRequest) {
		MeterDataMessage meterDataMessage = new MeterDataMessage();
		
		int meterId = meterDataRequest.getMeterId();
		AbstractMeter meter = simulationEngine.getMeter(meterId);
		if (meter==null) return;
		MeterDTO meterDTO = new MeterDTO(meter);
		meterDataMessage.setMeterData(meterDTO);
		
		outQueue.add(meterDataMessage);
	}

	public void init(TrafficModelDefinition def) {
		this.simulationEngine.init(def);
	}

}
