package ru.cos.sim.visualizer.traffic.graphs.converter;

import ru.cos.sim.meters.impl.data.Speed;
import ru.cos.sim.visualizer.traffic.graphs.classes.Line;
import ru.cos.sim.visualizer.traffic.graphs.classes.Point;
import ru.cos.sim.visualizer.traffic.graphs.classes.PointCollection;
import ru.cos.sim.visualizer.traffic.graphs.classes.SpeedData;

public class SpeedConverter extends AbstractConverter<Line, SpeedData,
        Speed> implements DataConverter<SpeedData, Speed> {
    @Override
    protected SpeedData getGraphDataInstance() {
        return new SpeedData();
    }

    @Override
    protected Line getPointCollection() {
        return new Line();
    }

    @Override
    protected Point convertMeasuredData(float time, Speed speed) {
        Point p = new Point();
        p.setX(time);
        p.setY(speed.floatValue());
        return p;
    }

    @Override
    protected void addDatasetToGraphData(SpeedData graphData, PointCollection pointCollection) {
        graphData.getLines().add((Line)pointCollection);
    }

    @Override
    public float[] getValue(Speed measuredData) {
        float[] data = new float[2];
        data[1] = measuredData.floatValue();
        return data;
    }
}
