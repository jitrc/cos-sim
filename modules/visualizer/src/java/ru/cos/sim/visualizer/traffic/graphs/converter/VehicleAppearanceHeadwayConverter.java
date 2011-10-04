package ru.cos.sim.visualizer.traffic.graphs.converter;

import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.impl.data.TrafficVolume;
import ru.cos.sim.meters.impl.data.VehicleAppearanceHeadwayHistogram;
import ru.cos.sim.visualizer.traffic.graphs.classes.*;

public class VehicleAppearanceHeadwayConverter extends AbstractConverter<Bars, HistogramData,
        VehicleAppearanceHeadwayHistogram>{
    @Override
    protected HistogramData getGraphDataInstance() {
        return new HistogramData();
    }

    @Override
    protected Bars getPointCollection() {
        return new Bars();
    }

    @Override
    protected Point convertMeasuredData(float time, VehicleAppearanceHeadwayHistogram vehicleAppearanceHeadwayHistogram) {
        return null;
    }

    @Override
    protected void addDatasetToGraphData(HistogramData graphData, PointCollection pointCollection) {
        graphData.setBars((Bars)pointCollection);
    }

    @Override
    public float[] getValue(VehicleAppearanceHeadwayHistogram histogram) {
        return new float[0];
    }

    @Override
    public float[][] getInstantData(MeterDTO<VehicleAppearanceHeadwayHistogram> dto){
        VehicleAppearanceHeadwayHistogram histogram = dto.getInstantMeasuredData();
        float headwayCount = 0.0f;
        float[][] data = new float[histogram.getBars().size()][];
        for(int i = 0; i < histogram.getBars().size(); i++){
            TrafficVolume v = histogram.getBars().get(i); 
            float[] datum = new float[2];
            datum[0] = headwayCount;
            datum[1] = v.floatValue();
            headwayCount = headwayCount + histogram.getTimeStep();
            data[i] = datum;
        }
        return data;
    }

    public HistogramData convertInstantMeasuredData(MeterDTO<VehicleAppearanceHeadwayHistogram> dto){
        HistogramData graphData = getGraphDataInstance();
        VehicleAppearanceHeadwayHistogram histogram = dto.getInstantMeasuredData();
        PointCollection pointCollection = convertHistogram(histogram);
        pointCollection.setDescription(dto.getName());
        addDatasetToGraphData(graphData, pointCollection);
        return graphData;
    }

    private PointCollection convertHistogram(VehicleAppearanceHeadwayHistogram histogram){
        Bars bars = getPointCollection();
        if(histogram == null) return bars;
        float headwayCount = 0.0f;
        for(TrafficVolume v : histogram.getBars()){
            Point p = new Point();
            p.setX(headwayCount);
            p.setY(v.floatValue());
            headwayCount = headwayCount + histogram.getTimeStep();
            bars.getPoints().add(p);
        }
        return bars;
    }
}
