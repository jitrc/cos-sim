package ru.cos.sim.visualizer.traffic.graphs;

import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.impl.data.Density;
import ru.cos.sim.visualizer.traffic.graphs.classes.DensityData;
import ru.cos.sim.visualizer.traffic.graphs.classes.Line;
import ru.cos.sim.visualizer.traffic.graphs.converter.DensityConverter;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public class DensityMeterGraph extends AbstractGraph<Line, DensityData, Density> implements IGraph<DensityData, Density>{

    private static String thisGraphName = "Density Meter";
    private static String thisAxisXName = "Time [s]";
    private static String thisAxisYName = "Density [pcu/km]";

    public static DensityMeterGraph getInstance(String fileName) throws JAXBException, FileNotFoundException{
        DensityMeterGraph graph = new DensityMeterGraph(126378);
        graph.createHistoryGraph(parseGraph(fileName).getDensity());
        return graph;
    }

    public static DensityMeterGraph getInstance(long id){
        DensityMeterGraph graph = new DensityMeterGraph(id);
        graph.createEmptyGraph();
        return graph;
    }

    public static DensityMeterGraph getInstance(MeterDTO<Density> dto, long id){
        DensityMeterGraph graph = new DensityMeterGraph(id);
        DensityData data = graph.converter.convertHistory(dto);
        graph.createHistoryGraph(data);
        return graph;
    }

    public DensityMeterGraph(long id) {
        super(id, thisGraphName, thisAxisXName, thisAxisYName);
        converter = new DensityConverter();
    }

    @Override
    protected Line getPointCollection(DensityData graphData) {
        return graphData.getLines().get(0);
    }

    @Override
    protected List<Line> getPointCollections(DensityData graphData) {
        return graphData.getLines();
    }

    @Override
    protected void createHistoryGraph(DensityData graphData) {
        chart.createSinglelineChart(getPointCollection(graphData));
    }

    @Override
    protected void createScheduledHistoryGraph(DensityData graphData) {
        chart.createMultilineChart(getPointCollections(graphData));
    }

    @Override
    public DensityData refreshInstant(MeterDTO<Density> densityMeterDTO) {
        return null;
    }
}
