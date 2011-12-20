package ru.cos.sim.visualizer.traffic.graphs.converter;
public class CumulateVolumeLightedConverter {
}
/*package ru.cos.nissan.graphs.converter;

import ru.cos.traffic.model.meters.output.CumulateVolumeLightedOutput;
import ru.cos.traffic.model.meters.output.dataset.VehicleCount_TimeDataset;
import ru.cos.traffic.model.meters.output.dataset.LightRangeDataset;
import ru.cos.traffic.model.meters.output.data.VehicleCount_Time;
import ru.cos.traffic.model.meters.output.data.LightRange;
import ru.cos.nissan.graphs.classes.*;

import java.util.List;
import java.util.ArrayList;

public class CumulateVolumeLightedConverter extends AbstractConverter<VehicleCount_Time, Line,
        VehicleCount_TimeDataset, CumulateVolumeLightedData, CumulateVolumeLightedOutput>{

    @Override
    protected CumulateVolumeLightedData getGraphData() {
        return new CumulateVolumeLightedData();
    }

    @Override
    protected Line getPointCollection() {
        return new Line();
    }

    @Override
    protected void addDatasetToGraphData(CumulateVolumeLightedData graphData, PointCollection pointCollection) {
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
    protected void addGraphSpecificDatasets(CumulateVolumeLightedData cumulativeVolumeData,
             CumulateVolumeLightedOutput cumulateVolumeLightedOutput) {
        LightRangeDataset dataset = cumulateVolumeLightedOutput.getLightRangeDataset();
        for(LightRange range : dataset.getData()){
            Interval interval = new Interval();
            interval.setStart(range.getTimeStart());
            interval.setEnd(range.getTimeEnd());
            interval.setAxis(Axis.X);
            switch(range.getSignal()){
                case RED:
                    interval.setColor(Color.RED);
                    break;
                case YELLOW:
                    interval.setColor(Color.YELLOW);
                    break;
                case GREEN:
                    interval.setColor(Color.GREEN);
                    break;
            }
            cumulativeVolumeData.getIntervals().add(interval);
        }
        if(dataCalculator != null){
        }
    }

    @Override
    protected void highlightPoints(CumulateVolumeLightedOutput cumulateVolumeOutputLighted) {
    }
    
}
*/
