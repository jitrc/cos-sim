package ru.cos.sim.visualizer.traffic.graphs.report;

import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.impl.data.*;
import ru.cos.sim.visualizer.traffic.graphs.*;

import java.awt.image.BufferedImage;

public class ThumbnailFactory {

    @SuppressWarnings("unchecked")
    public static BufferedImage getThumbnail(MeterDTO dto){
        BufferedImage image = null;
        int width = 400;
        int height = 200;
        switch(dto.getType()){
            case AverageTravelTimeMeter:
                return AverageTravelTimeMeterGraph.getInstance((MeterDTO<Time>)dto, dto.getId()).getThumbnail(width, height);
            case DensityFlowMeter:
                return DensityFlowGraph.getInstance((MeterDTO<DensityFlow>)dto, dto.getId()).getThumbnail(width, height);
            case DensityMeter:
                return DensityMeterGraph.getInstance((MeterDTO<Density>)dto, dto.getId()).getThumbnail(width, height);
            case FlowMeter:
                return FlowMeterGraph.getInstance((MeterDTO<Flow>)dto, dto.getId()).getThumbnail(width, height);
            case InstantAverageSpeedMeter:
                return InstantAverageSpeedMeterGraph.getInstance((MeterDTO<Speed>)dto, dto.getId()).getThumbnail(width, height);
            case LinkAverageTravelSpeedMeter:
                return LinkAverageTravelSpeedMeterGraph.getInstance((MeterDTO<Speed>)dto, dto.getId()).getThumbnail(width, height);
            case NetworkAverageTravelSpeedMeter:
                return NetworkAverageTravelSpeedMeterGraph.getInstance((MeterDTO<Speed>)dto, dto.getId()).getThumbnail(width, height);
            case SectionAverageTravelSpeedMeter:
                return SectionAverageTravelSpeedMeterGraph.getInstance((MeterDTO<Speed>)dto, dto.getId()).getThumbnail(width, height);
            case TotalTravelTimeMeter:
                return TotalTravelTimeMeterGraph.getInstance((MeterDTO<Time>)dto, dto.getId()).getThumbnail(width, height);
            case TrafficVolumeMeter:
                return TrafficVolumeMeterGraph.getInstance((MeterDTO<TrafficVolume>)dto, dto.getId()).getThumbnail(width, height);
            case VehiclesAppearanceHeadwayMeter:
                return VehicleAppearanceHeadwayGraph.getInstance((MeterDTO<VehicleAppearanceHeadwayHistogram>)dto, dto.getId()).getThumbnail(width, height);
        }
        return image;
    }
}
