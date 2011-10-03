package ru.cos.nissan.graphs;
import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.impl.data.Speed;
import ru.cos.nissan.graphs.classes.SpeedData;
import ru.cos.nissan.graphs.classes.Line;
import ru.cos.nissan.graphs.converter.SpeedConverter;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.io.FileNotFoundException;

public class SectionAverageTravelSpeedMeterGraph extends AbstractGraph<Line, SpeedData, Speed> implements IGraph<SpeedData, Speed>{
    private static String thisGraphName = "Section Average Travel Speed Meter";
    private static String thisAxisXName = "Time [s]";
    private static String thisAxisYName = "Average speed [m/s]";

    public static SectionAverageTravelSpeedMeterGraph getInstance(String fileName) throws JAXBException, FileNotFoundException{
        SectionAverageTravelSpeedMeterGraph graph = new SectionAverageTravelSpeedMeterGraph(376);
        graph.createEmptyGraph();
        return graph;
    }

    public static SectionAverageTravelSpeedMeterGraph getInstance(long id){
        SectionAverageTravelSpeedMeterGraph graph = new SectionAverageTravelSpeedMeterGraph(id);
        graph.createEmptyGraph();
        return graph;
    }

    public static SectionAverageTravelSpeedMeterGraph getInstance(MeterDTO<Speed> dto, long id){
        SectionAverageTravelSpeedMeterGraph graph = new SectionAverageTravelSpeedMeterGraph(id);
        SpeedData data = graph.converter.convertHistory(dto);
        graph.createHistoryGraph(data);
        return graph;
    }

    public SectionAverageTravelSpeedMeterGraph(long id) {
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
