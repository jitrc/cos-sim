package ru.cos.sim.visualizer.traffic.graphs;

import org.jfree.chart.ChartPanel;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.image.BufferedImage;

import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.framework.MeasuredData;
import ru.cos.sim.meters.framework.TimePeriod;
import ru.cos.sim.visualizer.traffic.graphs.classes.*;
import ru.cos.sim.visualizer.traffic.graphs.converter.DataConverter;
import ru.cos.sim.visualizer.traffic.graphs.integrator.Integrator;
import ru.cos.sim.visualizer.traffic.graphs.listener.ChartEventNotifier;
import ru.cos.sim.visualizer.traffic.graphs.listener.CrosshairChangedEvent;

public abstract class AbstractGraph<PC extends PointCollection, GD extends GraphData, MD extends MeasuredData<MD>>
        implements IGraph<GD, MD>{
    public static enum GraphType{
        HistoryGraph, ScheduledHistoryGraph
    }

    protected long id;
    protected String graphName;
    protected String axisXName;
    protected String axisYName;
    protected Chart chart;
    protected DataConverter<GD, MD> converter;

    abstract protected PC getPointCollection(GD graphData);
    abstract protected java.util.List<PC> getPointCollections(GD graphData);
    abstract protected void createHistoryGraph(GD graphData);
    abstract protected void createScheduledHistoryGraph(GD graphData);

    @Override
    public String getGraphName(){
        return this.graphName;
    }

    @Override
    public DataConverter<GD, MD> getConverter(){return converter;}

    @Override
    public GD refreshHistory(MeterDTO<MD> dto){
        GD data = converter.convertHistory(dto);
        if(getPointCollections(data) == null || getPointCollections(data).size() == 0)
            createEmptyGraph();
        else createHistoryGraph(data);
        return data;
    }

    @Override
    public GD refreshScheduledHistory(MeterDTO<MD> dto){
        GD data = converter.convertScheduledHistory(dto);
        if(getPointCollections(data) == null || getPointCollections(data).size() == 0)
            createEmptyGraph();
        else createScheduledHistoryGraph(data);
        return data;
    }

    @Override
    public GD refreshScheduledHistoryByPeriod(MeterDTO<MD> dto, TimePeriod period){
        GD data = converter.convertScheduledHistoryByPeriod(dto, period);
        if(getPointCollections(data) == null || getPointCollections(data).size() == 0)
            createEmptyGraph();
        else createScheduledHistoryGraph(data);
        return data;

    }

    public ChartPanel getChartPanel(){return chart.chartPanel;}
    @Override
    public long getId(){return id;}

    @Override
    public BufferedImage getThumbnail(int width, int height){
        return chart.getThumbnail(width, height);
    }

    @Override
    public void setCrosshairEventNotifier(ChartEventNotifier<CrosshairChangedEvent> notifier){
        chart.setCrosshairEventNotifier(notifier);
    }

    @Override
    public float getAverageValue(GD gd, IntervalData data){
        Point start = new Point();
        start.setX(data.getPeriodStart());
        Point end = new Point();
        end.setX(data.getPeriodEnd());
        return Integrator.getAverageValue(getPointCollection(gd), start, end);
    }

    public AbstractGraph(long id, String graphName, String axisXName, String axisYName){
        this.id = id;
        this.graphName = graphName;
        this.axisXName = axisXName;
        this.axisYName = axisYName;
        chart = new Chart(graphName);
        chart.createPlot(axisXName, axisYName);
    }

 
    public void createEmptyGraph(){
        chart.createEmptyChart();
    }

    protected static AllGraphs parseGraph(String filename) throws FileNotFoundException, JAXBException {
        InputStream is = new FileInputStream(new File(filename));
        JAXBContext jc = JAXBContext.newInstance("ru.cos.nissan.graphs.classes");
        Unmarshaller u = jc.createUnmarshaller();
        return (AllGraphs)u.unmarshal(is);
    }
}
