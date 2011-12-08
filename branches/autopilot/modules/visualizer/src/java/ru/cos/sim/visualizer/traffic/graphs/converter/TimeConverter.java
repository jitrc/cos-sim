package ru.cos.sim.visualizer.traffic.graphs.converter;

import ru.cos.sim.meters.impl.data.Time;
import ru.cos.sim.visualizer.traffic.graphs.classes.Line;
import ru.cos.sim.visualizer.traffic.graphs.classes.Point;
import ru.cos.sim.visualizer.traffic.graphs.classes.PointCollection;
import ru.cos.sim.visualizer.traffic.graphs.classes.TravelTimeData;

public class TimeConverter extends AbstractConverter<Line, TravelTimeData, 
        Time> implements DataConverter<TravelTimeData, Time>{
    @Override
    protected TravelTimeData getGraphDataInstance() {
        return new TravelTimeData();
    }

    @Override
    protected Line getPointCollection() {
        return new Line();
    }

    @Override
    protected Point convertMeasuredData(float time, Time t) {
        Point p = new Point();
        p.setX(time);
        p.setY(t.floatValue());
        return p;
    }

    @Override
    protected void addDatasetToGraphData(TravelTimeData graphData, PointCollection pointCollection) {
        graphData.getLines().add((Line)pointCollection);
    }

    @Override
    public float[] getValue(Time measuredData) {
        float[] data = new float[2];
        data[1] = measuredData.floatValue();
        return data;
    }
}
