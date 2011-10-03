/**
 * 
 */
package ru.cos.sim.meters;

import java.util.Map;

import ru.cos.sim.engine.RoadNetworkUniverse;
import ru.cos.sim.meters.data.DensityFlowMeterInitData;
import ru.cos.sim.meters.data.DensityMeterInitData;
import ru.cos.sim.meters.data.FlowMeterInitData;
import ru.cos.sim.meters.data.LinkAverageTravelSpeedMeterInitData;
import ru.cos.sim.meters.data.MeterData;
import ru.cos.sim.meters.data.SATSMeterInitData;
import ru.cos.sim.meters.data.TrafficVoulmeMeterInitData;
import ru.cos.sim.meters.data.VAHMeterInitData;
import ru.cos.sim.meters.impl.AbstractMeter;
import ru.cos.sim.meters.impl.AverageTravelTimeMeter;
import ru.cos.sim.meters.impl.DensityFlowMeter;
import ru.cos.sim.meters.impl.DensityMeter;
import ru.cos.sim.meters.impl.FlowMeter;
import ru.cos.sim.meters.impl.InstantAverageSpeedMeter;
import ru.cos.sim.meters.impl.LinkAverageTravelSpeedMeter;
import ru.cos.sim.meters.impl.NetworkAverageTravelSpeedMeter;
import ru.cos.sim.meters.impl.SATSMeter;
import ru.cos.sim.meters.impl.TotalTravelTimeMeter;
import ru.cos.sim.meters.impl.TrafficVolumeMeter;
import ru.cos.sim.meters.impl.VAHMeter;
import ru.cos.sim.road.link.Link;
import ru.cos.sim.road.link.Segment;
import ru.cos.sim.road.node.Node;
import ru.cos.sim.utils.Pair;

/**
 * 
 * @author zroslaw
 */
public class MeterFactory {

	public static AbstractMeter createMeter(MeterData meterData,
			RoadNetworkUniverse universe) {
		Map<Integer, Link> links = universe.getRoadNetwork().getLinks();
		Map<Integer, Node> nodes = universe.getRoadNetwork().getNodes();
		AbstractMeter abstractMeter = null;
		switch (meterData.getType()){
			case InstantAverageSpeedMeter:{
				InstantAverageSpeedMeter instantAverageSpeedMeter =
					new InstantAverageSpeedMeter(meterData.getId(), meterData.getModesInitData());
				abstractMeter = instantAverageSpeedMeter;
				break;
			}
			case TotalTravelTimeMeter:{
				TotalTravelTimeMeter totalTravelTimeMeter =
					new TotalTravelTimeMeter(meterData.getId(), meterData.getModesInitData());
				abstractMeter = totalTravelTimeMeter;
				break;
			}
			case AverageTravelTimeMeter:{
				AverageTravelTimeMeter averageTravelTimeMeter = 
					new AverageTravelTimeMeter(meterData.getId(), meterData.getModesInitData());
				abstractMeter = averageTravelTimeMeter;
				break;
			}
			case NetworkAverageTravelSpeedMeter:{
				NetworkAverageTravelSpeedMeter networkAverageTravelSpeedMeter = 
					new NetworkAverageTravelSpeedMeter(meterData.getId(), meterData.getModesInitData());
				abstractMeter = networkAverageTravelSpeedMeter;
				break;
			}
			case TrafficVolumeMeter:{
				TrafficVoulmeMeterInitData tvmInitData = (TrafficVoulmeMeterInitData)meterData;
				Link link = links.get(tvmInitData.getLinkId());
				Segment segment = link.getSegment(tvmInitData.getSegmentId());
				float position = tvmInitData.getPosition();
				TrafficVolumeMeter tvm =
					new TrafficVolumeMeter(meterData.getId(), meterData.getModesInitData(), segment, position);
				abstractMeter = tvm;
				break;
			}
			case LinkAverageTravelSpeedMeter:{
				LinkAverageTravelSpeedMeterInitData md = (LinkAverageTravelSpeedMeterInitData)meterData;
				int linkId = md.getLinkId();
				Link link = links.get(linkId);
				LinkAverageTravelSpeedMeter latsMeter = 
					new LinkAverageTravelSpeedMeter(
							meterData.getId(),
							meterData.getModesInitData(),
							link);
				abstractMeter = latsMeter;
				break;
			}
			case FlowMeter:{
				FlowMeterInitData fmid = (FlowMeterInitData)meterData;
				Link link = links.get(fmid.getLinkId());
				Segment segment = link.getSegment(fmid.getSegmentId());
				float position = fmid.getPosition();
				float measuringTime = fmid.getMeasuringTime();
				FlowMeter flowMeter = new FlowMeter(
						fmid.getId(), 
						fmid.getModesInitData(), 
						segment,position,measuringTime);
				abstractMeter = flowMeter;
				break;
			}
			case DensityMeter:{
				DensityMeterInitData dmid = (DensityMeterInitData)meterData;
				Link link = links.get(dmid.getLinkId());
				Segment endSegment = link.getSegment(dmid.getSegmentId());
				float endPosition = dmid.getPosition();
				if (endPosition>endSegment.getLength()) endPosition=endSegment.getLength(); // in case of incorrect data..
				float length = dmid.getLength();
				Pair<Segment,Float> startLocation = getBackwardLocationOnLength(endSegment, endPosition, length);
				Segment startSegment = startLocation.getFirst();
				float startPosition = startLocation.getSecond();
				DensityMeter densityMeter = new DensityMeter(
						meterData.getId(), 
						meterData.getModesInitData(), 
						startSegment, startPosition, 
						endSegment, endPosition, length);
				abstractMeter = densityMeter;
				break;
			}
			case DensityFlowMeter:{
				DensityFlowMeterInitData dfmid = (DensityFlowMeterInitData)meterData;
				DensityMeterInitData dmid = dfmid.getDensityMeterInitData();
				FlowMeterInitData fmid = dfmid.getFlowMeterInitData();
				Link link = links.get(dmid.getLinkId());
				Segment endSegment = link.getSegment(dmid.getSegmentId());
				float endPosition = dmid.getPosition();
				if (endPosition>endSegment.getLength()) endPosition=endSegment.getLength(); // in case of incorrect data..
				float length = dmid.getLength();
				Pair<Segment,Float> startLocation = getBackwardLocationOnLength(endSegment, endPosition, length);
				Segment startSegment = startLocation.getFirst();
				float startPosition = startLocation.getSecond();
				float measuringTime = fmid.getMeasuringTime();
				DensityFlowMeter dfm = 
					new DensityFlowMeter(
							meterData.getId(), 
							meterData.getModesInitData(),
							startSegment, startPosition, endSegment, endPosition,
							length, measuringTime);
				abstractMeter = dfm;
				break;
			}
			case VehiclesAppearanceHeadwayMeter:{
				VAHMeterInitData hid = (VAHMeterInitData)meterData;
				Link link = links.get(hid.getLinkId());
				Segment segment = link.getSegment(hid.getSegmentId());
				float position = hid.getPosition();
				float timeBin = hid.getTimeBin();
				VAHMeter vahMeter = 
					new VAHMeter(
							meterData.getId(), 
							meterData.getModesInitData(),
							segment, position, timeBin);
				abstractMeter = vahMeter;
				break;
			}
			case SectionAverageTravelSpeedMeter:{
				SATSMeterInitData smid = (SATSMeterInitData)meterData;
				Link link = links.get(smid.getLinkId());
				Segment endSegment = link.getSegment(smid.getSegmentId());
				float endPosition = smid.getPosition();
				if (endPosition>endSegment.getLength()) endPosition=endSegment.getLength(); // in case of incorrect data..
				float length = smid.getLength();
				Pair<Segment,Float> startLocation = getBackwardLocationOnLength(endSegment, endPosition, length);
				Segment startSegment = startLocation.getFirst();
				float startPosition = startLocation.getSecond();
				SATSMeter satsMeter = new SATSMeter(
						meterData.getId(), 
						meterData.getModesInitData(), 
						startSegment, startPosition, 
						endSegment, endPosition, length);
				abstractMeter = satsMeter;
				break;
			}
		}
		abstractMeter.setName(meterData.getName());
		return abstractMeter;
	}

	
	private static Pair<Segment,Float> getBackwardLocationOnLength(Segment segment, float position, float length){
		float newPosition = position-length;
		if (newPosition>=0){
			return new Pair<Segment, Float>(segment, newPosition);
		}
		Segment prevSegment = segment.getPrevSegment();
		if (prevSegment==null){
			return new Pair<Segment, Float>(segment, 0f);
		}
		return getBackwardLocationOnLength(prevSegment, prevSegment.getLength(), -newPosition);
	}
}
