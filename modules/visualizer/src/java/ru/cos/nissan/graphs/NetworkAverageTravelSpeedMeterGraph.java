package ru.cos.nissan.graphs;
import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.impl.data.Speed;
import ru.cos.nissan.graphs.classes.SpeedData;
import ru.cos.nissan.graphs.classes.Line;
import ru.cos.nissan.graphs.converter.SpeedConverter;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public class NetworkAverageTravelSpeedMeterGraph extends AbstractGraph<Line, SpeedData, Speed> implements IGraph<SpeedData, Speed>{
    private static String thisGraphName = "Network Average Travel Speed Meter";
    private static String thisAxisXName = "Time [s]";
    private static String thisAxisYName = "Average speed [m/s]";

    public static NetworkAverageTravelSpeedMeterGraph getInstance(String fileName) throws JAXBException, FileNotFoundException{
        NetworkAverageTravelSpeedMeterGraph graph = new NetworkAverageTravelSpeedMeterGraph(278);
        graph.createHistoryGraph(parseGraph(fileName).getSpeed());
        return graph;
    }

    public static NetworkAverageTravelSpeedMeterGraph getInstance(long id){
        NetworkAverageTravelSpeedMeterGraph graph = new NetworkAverageTravelSpeedMeterGraph(id);
        graph.createEmptyGraph();
        return graph;
    }

    public static NetworkAverageTravelSpeedMeterGraph getInstance(MeterDTO<Speed> dto, long id){
        NetworkAverageTravelSpeedMeterGraph graph = new NetworkAverageTravelSpeedMeterGraph(id);
        SpeedData data = graph.converter.convertHistory(dto);
        graph.createHistoryGraph(data);
        return graph;
    }

    public NetworkAverageTravelSpeedMeterGraph(long id) {
        super(id, thisGraphName, thisAxisXName, thisAxisYName);
        converter = new SpeedConverter();
    }

    @Override
    protected Line getPointCollection(SpeedData graphData) {
        return graphData.getLines().get(0);
    }

    @Override
    protected List<Line> getPointCollections(SpeedData graphData) {
        return graphData.getLines();
    }

    @Override
    protected void createHistoryGraph(SpeedData graphData) {
        chart.createSinglelineChart(getPointCollection(graphData));
    }

    @Override
    protected void createScheduledHistoryGraph(SpeedData graphData) {
        chart.createMultilineChart(getPointCollections(graphData));
    }

    @Override
    public SpeedData refreshInstant(MeterDTO<Speed> speedMeterDTO) {
        return null;
    }
}
