package ru.cos.sim.visualizer.traffic.graphs.converter;

import ru.cos.sim.meters.impl.data.Density;
import ru.cos.sim.visualizer.traffic.graphs.classes.DensityData;
import ru.cos.sim.visualizer.traffic.graphs.classes.Line;
import ru.cos.sim.visualizer.traffic.graphs.classes.Point;
import ru.cos.sim.visualizer.traffic.graphs.classes.PointCollection;

public class DensityConverter extends AbstractConverter<Line, DensityData,
        Density> implements DataConverter<DensityData, Density>{
    @Override
    protected DensityData getGraphDataInstance() {
        return new DensityData();
    }

    @Override
    protected Line getPointCollection() {
        return new Line();
    }

    @Override
    protected Point convertMeasuredData(float time, Density density) {
        Point p = new Point();
        p.setX(time);
        p.setY(density.floatValue());
        return p;
    }

    @Override
    protected void addDatasetToGraphData(DensityData graphData, PointCollection pointCollection) {
        graphData.getLines().add((Line)pointCollection);
    }

    @Override
    public float[] getValue(Density measuredData) {
        float[] data = new float[2];
        data[1] = measuredData.floatValue();
        return data;
    }
}
