package ru.cos.sim.visualizer.traffic.graphs.converter;
/*package ru.cos.nissan.graphs.converter;

import ru.cos.nissan.graphs.classes.Point;
import ru.cos.nissan.graphs.classes.Line;
import ru.cos.nissan.graphs.classes.PointCollection;
import ru.cos.nissan.graphs.classes.CumulateVolumeData;
import ru.cos.traffic.model.meters.output.CumulateVolumeMarkedOutput;
import ru.cos.traffic.model.meters.output.dataset.VehicleCount_TimeDataset;
import ru.cos.traffic.model.meters.output.data.VehicleCount_Time;

import java.util.List;
import java.util.ArrayList;

public class CumulateVolumeMarkedConverter extends AbstractConverter<VehicleCount_Time, Line,
        VehicleCount_TimeDataset, CumulateVolumeData, CumulateVolumeMarkedOutput>{
    //todo replace cumulateVolume with cumulate volumemarked

    @Override
    protected CumulateVolumeData getGraphData() {
        return new CumulateVolumeData();
    }

    @Override
    protected Line getPointCollection() {
        return new Line();
    }

    @Override
    protected void addDatasetToGraphData(CumulateVolumeData graphData, PointCollection pointCollection) {
        graphData.getLines().add((Line)pointCollection);
    }

    @Override
    protected List<Point> transform(List<VehicleCount_Time> vehicleCount_times) {
        List<Point> points = new ArrayList<Point>();
        for(VehicleCount_Time v : vehicleCount_times){
            Point p = new Point();
            p.setX(v.getTime());
            p.setY(v.getVehicleCount());
            points.add(p);
        }
        return points;
    }

    @Override
    protected void addGraphSpecificDatasets(CumulateVolumeData cumulativeVolumeData, CumulateVolumeMarkedOutput cumulateVolumeMarkedOutput) {
        if(dataCalculator != null){
        }
    }

    @Override
    protected void highlightPoints(CumulateVolumeMarkedOutput cumulateVolumeOutputMarked) {
    }
}
*/