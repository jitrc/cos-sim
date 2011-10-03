package ru.cos.nissan.graphs;
import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.impl.data.Time;
import ru.cos.nissan.graphs.classes.TravelTimeData;
import ru.cos.nissan.graphs.classes.Line;
import ru.cos.nissan.graphs.converter.TimeConverter;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public class TotalTravelTimeMeterGraph extends AbstractGraph<Line, TravelTimeData, Time> implements IGraph<TravelTimeData, Time>{
    private static String thisGraphName = "Total Travel Time Meter";
    private static String thisAxisXName = "Time [s]";
    private static String thisAxisYName = "Total travel time [s]";

    public static TotalTravelTimeMeterGraph getInstance(String fileName) throws JAXBException, FileNotFoundException {
        TotalTravelTimeMeterGraph graph = new TotalTravelTimeMeterGraph(1237);
        graph.createHistoryGraph(parseGraph(fileName).getTime());
        return graph;
    }

    public static TotalTravelTimeMeterGraph getInstance(long id){
        TotalTravelTimeMeterGraph graph = new TotalTravelTimeMeterGraph(id);
        graph.createEmptyGraph();
        return graph;
    }

    public static TotalTravelTimeMeterGraph getInstance(MeterDTO<Time> dto, long id){
        TotalTravelTimeMeterGraph graph = new TotalTravelTimeMeterGraph(id);
        TravelTimeData data = graph.converter.convertHistory(dto);
        graph.createHistoryGraph(data);
        return graph;
    }

    public TotalTravelTimeMeterGraph(long id) {
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
