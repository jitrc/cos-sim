package ru.cos.nissan.graphs.converter;

import ru.cos.nissan.graphs.classes.*;
import ru.cos.sim.meters.impl.data.DensityFlow;

public class DensityFlowConverter extends AbstractConverter<MarkedPoints, DensityFlowData,
        DensityFlow> implements DataConverter<DensityFlowData, DensityFlow>{

    @Override
    protected DensityFlowData getGraphDataInstance() {
        return new DensityFlowData();
    }

    @Override
    protected MarkedPoints getPointCollection() {
        return new MarkedPoints();
    }

    @Override
    protected Point convertMeasuredData(float time, DensityFlow densityFlow) {
        Point p = new Point();
        p.setX(densityFlow.getDensity().floatValue());
        p.setY(densityFlow.getFlow().floatValue());
        p.setZ(time);
        return p;
    }

    @Override
    protected void addDatasetToGraphData(DensityFlowData graphData, PointCollection pointCollection) {
        graphData.getPointSeries().add((MarkedPoints)pointCollection);
    }

    @Override
    public float[] getValue(DensityFlow measuredData) {
        float[] data = new float[3];
        data[1] = measuredData.getDensity().floatValue();
        data[2] = measuredData.getFlow().floatValue();
        return data;
    }
}
