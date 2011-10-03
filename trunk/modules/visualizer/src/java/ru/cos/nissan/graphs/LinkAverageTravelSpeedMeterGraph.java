package ru.cos.nissan.graphs;
import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.impl.data.Speed;
import ru.cos.nissan.graphs.classes.SpeedData;
import ru.cos.nissan.graphs.classes.Line;
import ru.cos.nissan.graphs.converter.SpeedConverter;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public class LinkAverageTravelSpeedMeterGraph extends AbstractGraph<Line, SpeedData, Speed> implements IGraph<SpeedData, Speed>{
    private static String thisGraphName = "Link Average Travel Speed Meter";
    private static String thisAxisXName = "Time [s]";
    private static String thisAxisYName = "Average speed [m/s]";

    public static LinkAverageTravelSpeedMeterGraph getInstance(String fileName) throws JAXBException, FileNotFoundException{
        LinkAverageTravelSpeedMeterGraph graph = new LinkAverageTravelSpeedMeterGraph(1629386);
        graph.createHistoryGraph(parseGraph(fileName).getSpeed());
        return graph;
    }

    public static LinkAverageTravelSpeedMeterGraph getInstance(long id){
        LinkAverageTravelSpeedMeterGraph graph = new LinkAverageTravelSpeedMeterGraph(id);
        graph.createEmptyGraph();
        return graph;
    }

    public static LinkAverageTravelSpeedMeterGraph getInstance(MeterDTO<Speed> dto, long id){
        LinkAverageTravelSpeedMeterGraph graph = new LinkAverageTravelSpeedMeterGraph(id);
        SpeedData data = graph.converter.convertHistory(dto);
        graph.createHistoryGraph(data);
        return graph;
    }

    public LinkAverageTravelSpeedMeterGraph(long id) {
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
