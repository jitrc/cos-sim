package ru.cos.nissan.graphs.listener;

import ru.cos.nissan.graphs.classes.IntervalData;

public class CrosshairChangedEvent extends ChartEvent{
    private IntervalData interval;

    public CrosshairChangedEvent(IntervalData data){
        this.interval = data;
    }

    public IntervalData getInterval(){ return interval;}
}
