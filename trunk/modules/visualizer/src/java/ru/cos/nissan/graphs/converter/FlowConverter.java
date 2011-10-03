package ru.cos.nissan.graphs.converter;

import ru.cos.sim.meters.impl.data.Flow;
import ru.cos.nissan.graphs.classes.FlowData;
import ru.cos.nissan.graphs.classes.Line;
import ru.cos.nissan.graphs.classes.Point;
import ru.cos.nissan.graphs.classes.PointCollection;

public class FlowConverter extends AbstractConverter<Line, FlowData,
        Flow> implements DataConverter<FlowData, Flow>{
    @Override
    protected FlowData getGraphDataInstance() {
        return new FlowData();
    }

    @Override
    protected Line getPointCollection() {
        return new Line();
    }

    @Override
    protected Point convertMeasuredData(float time, Flow flow) {
        Point p = new Point();
        p.setX(time);
        p.setY(flow.floatValue());
        return p;
    }

    @Override
    protected void addDatasetToGraphData(FlowData graphData, PointCollection pointCollection) {
        graphData.getLines().add((Line)pointCollection);
    }

    @Override
    public float[] getValue(Flow measuredData) {
        float[] data = new float[2];
        data[1] = measuredData.floatValue();
        return data;
    }
}
