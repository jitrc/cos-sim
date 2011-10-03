package ru.cos.nissan.graphs;

import ru.cos.nissan.graphs.classes.TravelTimeData;
import ru.cos.nissan.graphs.classes.Line;
import ru.cos.nissan.graphs.converter.TimeConverter;
import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.impl.data.Time;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public class AverageTravelTimeMeterGraph extends AbstractGraph<Line, TravelTimeData, Time> implements IGraph<TravelTimeData, Time>{

    private static String thisGraphName = "Average Travel Time Meter";
    private static String thisAxisXName = "Time [s]";
    private static String thisAxisYName = "Travel time [s]";


    public static AverageTravelTimeMeterGraph getInstance(String fileName) throws JAXBException, FileNotFoundException {
        AverageTravelTimeMeterGraph graph = new AverageTravelTimeMeterGraph(79842);
        graph.createHistoryGraph(parseGraph(fileName).getTime());
        return graph;
    }

    public static AverageTravelTimeMeterGraph getInstance(long id){
        AverageTravelTimeMeterGraph graph = new AverageTravelTimeMeterGraph(id);
        graph.createEmptyGraph();
        return graph;
    }

    public static AverageTravelTimeMeterGraph getInstance(MeterDTO<Time> dto, long id){
        AverageTravelTimeMeterGraph graph = new AverageTravelTimeMeterGraph(id);
        TravelTimeData travelTimeData = graph.converter.convertHistory(dto);
        graph.createHistoryGraph(travelTimeData);
        return graph;
    }

    private AverageTravelTimeMeterGraph(long id){
        super(id, thisGraphName, thisAxisXName, thisAxisYName);
        converter = new TimeConverter();
    }

    @Override
    protected Line getPointCollection(TravelTimeData graphData) {
        return graphData.getLines().get(0);
    }

    @Override
    protected List<Line> getPointCollections(TravelTimeData graphData) {
        return graphData.getLines();
    }

    @Override
    protected void createHistoryGraph(TravelTimeData graphData) {
        chart.createSinglelineChart(getPointCollection(graphData));
    }

    @Override
    protected void createScheduledHistoryGraph(TravelTimeData graphData) {
        chart.createMultilineChart(getPointCollections(graphData));
    }

    @Override
    public TravelTimeData refreshInstant(MeterDTO<Time> timeMeterDTO) {
        return null;
    }
}
