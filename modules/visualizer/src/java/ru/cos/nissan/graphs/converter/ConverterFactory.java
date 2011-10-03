package ru.cos.nissan.graphs.converter;

import ru.cos.sim.communication.dto.MeterDTO;

public class ConverterFactory {
    public static DataConverter getConverter(MeterDTO dto){
        switch(dto.getType()){
            case AverageTravelTimeMeter:
                return new TimeConverter();
            case DensityFlowMeter:
                return new DensityFlowConverter();
            case DensityMeter:
                return new DensityConverter();
            case FlowMeter:
                return new FlowConverter();
            case InstantAverageSpeedMeter:
                return new SpeedConverter();
            case LinkAverageTravelSpeedMeter:
                return new SpeedConverter();
            case NetworkAverageTravelSpeedMeter:
                return new SpeedConverter();
            case SectionAverageTravelSpeedMeter:
                return new SpeedConverter();
            case TotalTravelTimeMeter:
                return new TimeConverter();
            case TrafficVolumeMeter:
                return new TrafficVolumeConverter();
            case VehiclesAppearanceHeadwayMeter:
                return new VehicleAppearanceHeadwayConverter();
        }
        return null;
    }
}
