package ru.cos.sim.visualizer.traffic.graphs;

import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.impl.data.VehicleAppearanceHeadwayHistogram;
import ru.cos.sim.visualizer.traffic.graphs.classes.Bars;
import ru.cos.sim.visualizer.traffic.graphs.classes.HistogramData;
import ru.cos.sim.visualizer.traffic.graphs.converter.VehicleAppearanceHeadwayConverter;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public class VehicleAppearanceHeadwayGraph extends AbstractGraph<Bars, HistogramData, VehicleAppearanceHeadwayHistogram>
        implements IGraph<HistogramData, VehicleAppearanceHeadwayHistogram>{

    private static String thisGraphName = "Headway Histogram";
    private static String thisAxisXName = "Headway";
    private static String thisAxisYName = "Appearance frequency [pcu]";

    public static VehicleAppearanceHeadwayGraph getInstance(String fileName) throws JAXBException, FileNotFoundException {
        VehicleAppearanceHeadwayGraph graph = new VehicleAppearanceHeadwayGraph(13123);
        graph.createHistoryGraph(parseGraph(fileName).getHistogram());
        return graph;
    }

    public static VehicleAppearanceHeadwayGraph getInstance(long id){
        VehicleAppearanceHeadwayGraph graph = new VehicleAppearanceHeadwayGraph(id);
        graph.createEmptyGraph();
        return graph;
    }

    public static VehicleAppearanceHeadwayGraph getInstance(MeterDTO<VehicleAppearanceHeadwayHistogram> dto, long id){
        VehicleAppearanceHeadwayGraph graph = new VehicleAppearanceHeadwayGraph(id);
        HistogramData data = ((VehicleAppearanceHeadwayConverter)graph.converter).convertInstantMeasuredData(dto);
        graph.createInstantGraph(data);
        return graph;
    }

    private VehicleAppearanceHeadwayGraph(long id){
        super(id, thisGraphName, thisAxisXName, thisAxisYName);
        converter = new VehicleAppearanceHeadwayConverter();
    }

    @Override
    protected Bars getPointCollection(HistogramData graphData) {
        return graphData.getBars();
    }

    @Override
    protected List<Bars> getPointCollections(HistogramData graphData) {
        return null;
    }

    @Override
    protected void createHistoryGraph(HistogramData graphData) {
    }

    @Override
    protected void createScheduledHistoryGraph(HistogramData graphData) {
    }

    protected void createInstantGraph(HistogramData graphData){
        chart.createBarChart(getPointCollection(graphData));
    }

    public HistogramData refreshInstant(MeterDTO<VehicleAppearanceHeadwayHistogram> dto){
        HistogramData data = ((VehicleAppearanceHeadwayConverter)converter).convertInstantMeasuredData(dto);
        createInstantGraph(data);
        return data;
    }
}
