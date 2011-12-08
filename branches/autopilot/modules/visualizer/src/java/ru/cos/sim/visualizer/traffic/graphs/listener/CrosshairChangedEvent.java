package ru.cos.sim.visualizer.traffic.graphs.listener;

import ru.cos.sim.visualizer.traffic.graphs.classes.IntervalData;

public class CrosshairChangedEvent extends ChartEvent{
    private IntervalData interval;

    public CrosshairChangedEvent(IntervalData data){
        this.interval = data;
    }

    public IntervalData getInterval(){ return interval;}
}
