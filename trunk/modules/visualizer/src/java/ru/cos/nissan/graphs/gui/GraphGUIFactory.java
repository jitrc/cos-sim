package ru.cos.nissan.graphs.gui;

import ru.cos.nissan.graphs.*;
import ru.cos.nissan.graphs.impl.IDataRequestable;
import ru.cos.nissan.graphs.classes.*;
import ru.cos.sim.meters.impl.data.*;
import ru.cos.trace.item.Meter;

import javax.swing.JFrame;

public class GraphGUIFactory {
    public static JFrame newInstance(Meter m, IDataRequestable manager){
        if(m.type == null) return null;//System.out.println("meter.type == null");
        switch(m.type){
            case TrafficVolumeMeter:
                return new GraphGUI<VolumeMeterData, TrafficVolume>(TrafficVolumeMeterGraph.getInstance(m.id()), manager).getFrame();
            case AverageTravelTimeMeter:
                return new GraphGUI<TravelTimeData, Time>(AverageTravelTimeMeterGraph.getInstance(m.id()), manager).getFrame();
            case DensityMeter:
                return new GraphGUI<DensityData, Density>(DensityMeterGraph.getInstance(m.id()), manager).getFrame();
            case FlowMeter:
                return new GraphGUI<FlowData, Flow>(FlowMeterGraph.getInstance(m.id()), manager).getFrame();
            case InstantAverageSpeedMeter:
                return new GraphGUI<SpeedData, Speed>(InstantAverageSpeedMeterGraph.getInstance(m.id()), manager).getFrame();
            case LinkAverageTravelSpeedMeter:
                return new GraphGUI<SpeedData, Speed>(LinkAverageTravelSpeedMeterGraph.getInstance(m.id()), manager).getFrame();
            case NetworkAverageTravelSpeedMeter:
                return new GraphGUI<SpeedData, Speed>(NetworkAverageTravelSpeedMeterGraph.getInstance(m.id()), manager).getFrame();
            case TotalTravelTimeMeter:
                return new GraphGUI<TravelTimeData, Time>(TotalTravelTimeMeterGraph.getInstance(m.id()), manager).getFrame();
            case DensityFlowMeter:
                return new GraphGUI<DensityFlowData, DensityFlow>(DensityFlowGraph.getInstance(m.id()), manager).getFrame();
            case VehiclesAppearanceHeadwayMeter:
                return new HistogramGraphGUI(VehicleAppearanceHeadwayGraph.getInstance(m.id()), manager).getFrame();
            case SectionAverageTravelSpeedMeter:
                return new GraphGUI<SpeedData, Speed>(SectionAverageTravelSpeedMeterGraph.getInstance(m.id()), manager).getFrame();
        }
        return null;
    }
}
