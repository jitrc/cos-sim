package ru.cos.nissan.graphs;

import ru.cos.nissan.graphs.classes.DensityFlowData;
import ru.cos.nissan.graphs.classes.MarkedPoints;
import ru.cos.nissan.graphs.converter.DensityFlowConverter;
import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.impl.data.DensityFlow;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public class DensityFlowGraph extends AbstractGraph<MarkedPoints, DensityFlowData, DensityFlow>{

    private static String thisGraphName = "Density-Flow Meter";
    private static String thisAxisXName = "Density [pcu/km]";
    private static String thisAxisYName = "Flow [pcu/hr]";

    /**
     * Gets instance of volume density graph
     * @param fileName - filename with statistics data
     * @return graph
     * @throws java.io.FileNotFoundException - if simulation statistics not found
     * @throws javax.xml.bind.JAXBException - if sumulation statistics xml not valid
     */
    public static DensityFlowGraph getInstance(String fileName) throws JAXBException, FileNotFoundException {
        DensityFlowGraph graph = new DensityFlowGraph(141113123);
        graph.createHistoryGraph(parseGraph(fileName).getDensityFlow());
        return graph;
    }

    /**
     * Gets instance of volume density graph
     * @param id
     * @return graph
     */
    public static DensityFlowGraph getInstance(long id){
        DensityFlowGraph graph = new DensityFlowGraph(id);
        graph.createEmptyGraph();
        return graph;
    }

    public static DensityFlowGraph getInstance(MeterDTO<DensityFlow> dto, long id){
        DensityFlowGraph graph = new DensityFlowGraph(id);
        DensityFlowData densityFlowData = graph.converter.convertHistory(dto);
        graph.createHistoryGraph(densityFlowData);
        return graph; 
    }

    private DensityFlowGraph(long id){
        super(id, thisGraphName, thisAxisXName, thisAxisYName);
        converter = new DensityFlowConverter();
    }

    @Override
    protected MarkedPoints getPointCollection(DensityFlowData graphData) {
        return graphData.getPointSeries().get(0);
    }

    @Override
    protected List<MarkedPoints> getPointCollections(DensityFlowData graphData) {
        return graphData.getPointSeries();
    }

    @Override
    protected void createHistoryGraph(DensityFlowData graphData) {
        chart.createSingleMarkedSeriesChart(getPointCollection(graphData));
    }

    @Override
    protected void createScheduledHistoryGraph(DensityFlowData graphData) {
        chart.createMultiMarkedSeriesChart(getPointCollections(graphData));
    }

    @Override
    public DensityFlowData refreshInstant(MeterDTO<DensityFlow> densityFlowMeterDTO) {
        return null;
    }
}
