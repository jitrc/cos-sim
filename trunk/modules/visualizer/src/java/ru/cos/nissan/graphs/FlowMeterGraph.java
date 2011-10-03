package ru.cos.nissan.graphs;
import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.impl.data.Flow;
import ru.cos.nissan.graphs.classes.FlowData;
import ru.cos.nissan.graphs.classes.Line;
import ru.cos.nissan.graphs.converter.FlowConverter;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public class FlowMeterGraph extends AbstractGraph<Line, FlowData, Flow> implements IGraph<FlowData, Flow>{
    private static String thisGraphName = "Flow Meter";
    private static String thisAxisXName = "Time [s]";
    private static String thisAxisYName = "Flow [pcu/hr]";

    public static FlowMeterGraph getInstance(String fileName) throws JAXBException, FileNotFoundException{
        FlowMeterGraph graph = new FlowMeterGraph(17931);
        graph.createHistoryGraph(parseGraph(fileName).getFlow());
        return graph;
    }

    public static FlowMeterGraph getInstance(long id){
        FlowMeterGraph graph = new FlowMeterGraph(id);
        graph.createEmptyGraph();
        return graph;
    }

    public static FlowMeterGraph getInstance(MeterDTO<Flow> dto, long id){
        FlowMeterGraph graph = new FlowMeterGraph(id);
        FlowData data = graph.converter.convertHistory(dto);
        graph.createHistoryGraph(data);
        return graph;
    }

    public FlowMeterGraph(long id) {
        super(id, thisGraphName, thisAxisXName, thisAxisYName);
        converter = new FlowConverter();
    }

    @Override
    protected Line getPointCollection(FlowData graphData) {
        return graphData.getLines().get(0);
    }

    @Override
    protected List<Line> getPointCollections(FlowData graphData) {
        return graphData.getLines();
    }

    @Override
    protected void createHistoryGraph(FlowData graphData) {
        chart.createSinglelineChart(getPointCollection(graphData));
    }

    @Override
    protected void createScheduledHistoryGraph(FlowData graphData) {
        chart.createMultilineChart(getPointCollections(graphData));
    }

    @Override
    public FlowData refreshInstant(MeterDTO<Flow> flowMeterDTO) {
        return null;
    }
}
