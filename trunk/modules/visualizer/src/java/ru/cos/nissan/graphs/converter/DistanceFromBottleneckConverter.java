/*package ru.cos.nissan.graphs.converter;

import ru.cos.nissan.graphs.classes.Point;
import ru.cos.nissan.graphs.classes.Line;
import ru.cos.nissan.graphs.classes.PointCollection;
import ru.cos.nissan.graphs.classes.DistanceFromBottleneckData;
import ru.cos.traffic.model.meters.output.DistanceFromBottleneckOutput;
import ru.cos.traffic.model.meters.output.dataset.Distance_TimeDataset;
import ru.cos.traffic.model.meters.output.data.Distance_Time;

import java.util.List;
import java.util.ArrayList;

public class DistanceFromBottleneckConverter extends AbstractConverter<Distance_Time, Line,
        Distance_TimeDataset, DistanceFromBottleneckData, DistanceFromBottleneckOutput> {

    @Override
    protected DistanceFromBottleneckData getGraphData() {
        return new DistanceFromBottleneckData();
    }

    @Override
    protected Line getPointCollection() {
        return new Line();
    }

    @Override
    protected void addDatasetToGraphData(DistanceFromBottleneckData graphData, PointCollection pointCollection) {
        graphData.getLines().add((Line)pointCollection);
    }

    @Override
    protected List<Point> transform(List<Distance_Time> distance_times) {
        List<Point> points = new ArrayList<Point>();
        for(Distance_Time d : distance_times){
            Point p = new Point();
            p.setX(d.getTime());
            p.setY(d.getDistance());
            points.add(p);
        }
        return points;
    }

    @Override
    protected void addGraphSpecificDatasets(DistanceFromBottleneckData distanceFromBottleneckData, DistanceFromBottleneckOutput distanceFromBottleneckOutput) {
        if(dataCalculator != null){
        }
    }

    @Override
    protected void highlightPoints(DistanceFromBottleneckOutput distanceFromBottleneckOutput) {
    }
}
*/