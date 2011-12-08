package ru.cos.sim.visualizer.traffic.graphs;

import org.jfree.data.xy.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYZToolTipGenerator;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;

import ru.cos.sim.visualizer.traffic.graphs.classes.*;
import ru.cos.sim.visualizer.traffic.graphs.integrator.FloatComparator;
import ru.cos.sim.visualizer.traffic.graphs.listener.ChartEventNotifier;
import ru.cos.sim.visualizer.traffic.graphs.listener.CrosshairChangedEvent;

import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

class Chart {

    private static enum Colors{
        BLUE(Color.BLUE), RED(Color.RED), CYAN(Color.CYAN), ORANGE(Color.ORANGE),
        GREEN(Color.GREEN), GRAY(Color.GRAY), BLACK(Color.BLACK), PINK(Color.PINK), YELLOW(Color.YELLOW),
        MAGENTA(Color.MAGENTA), LIGHT_RED(new Color(255, 215, 215)), LIGHT_YELLOW(new Color(255, 255, 180)),
        LIGHT_GREEN(new Color(205, 255, 205)), LIGHT_GRAY(Color.LIGHT_GRAY), DARK_GRAY(Color.DARK_GRAY);

        private Color color;
        private Colors(Color color) {this.color = color;}
        public Color getColor(){return color;}
    }

    private class Data{
        public java.util.List<PointCollection> collections;
        public java.util.List<XYItemRenderer> renderers;
        public IDatasetCreator datasetCreator;

        public Data(){
            collections = new ArrayList<PointCollection>();
            renderers = new ArrayList<XYItemRenderer>();
        }
    }

    private XYPlot plot;
    private String chartName;
    ChartPanel chartPanel;
    private ChartEventNotifier<CrosshairChangedEvent> notifier;
    private List<Float> limits = new ArrayList<Float>();

    public void setCrosshairEventNotifier(ChartEventNotifier<CrosshairChangedEvent> notifier){
        this.notifier = notifier;
    }

    private static class XYDatasetCreator implements IDatasetCreator{
        @Override
        public XYDataset createDataset(PointCollection pointCollection) {
            XYSeries series = new XYSeries(pointCollection.getDescription());
            for(ru.cos.sim.visualizer.traffic.graphs.classes.Point p : pointCollection.getPoints()){
                series.add(p.getX(), p.getY());
            }
            return new XYSeriesCollection(series);
        }
    }

    private static class XYZDatasetCreator implements IDatasetCreator{
        @Override
        public XYDataset createDataset(PointCollection pointCollection) {
            DefaultXYZDataset dataset = new DefaultXYZDataset();
            int pointCount = pointCollection.getPoints().size();
            double[][] data = new double[3][pointCount];
            for(int i = 0; i < pointCount; i++){
                ru.cos.sim.visualizer.traffic.graphs.classes.Point p = pointCollection.getPoints().get(i);
                data[0][i] = p.getX();
                data[1][i] = p.getY();
                data[2][i] = p.getZ();
            }
            dataset.addSeries(pointCollection.getDescription(), data);
            return dataset;
        }
    }

    private static class XYBarDatasetCreator implements IDatasetCreator{
        @Override
        public XYDataset createDataset(PointCollection pointCollection) {
            XYDatasetCreator creator = new XYDatasetCreator();
            XYDataset dataset = creator.createDataset(pointCollection);
            return new XYBarDataset(dataset, getXStep(pointCollection));
        }
    }
    
    private static final XYDatasetCreator XY_DATASET_CREATOR = new XYDatasetCreator();
    private static final XYZDatasetCreator XYZ_DATASET_CREATOR = new XYZDatasetCreator();
    private static final XYBarDatasetCreator XY_BAR_DATASET_CREATOR = new XYBarDatasetCreator();

    Chart(String chartName){
        this.chartName = chartName;
    }

    void createEmptyChart(){
        createChartPanel(buildChart(chartName));
    }

    void createSinglelineChart(Line line){
        createSingleSeriesChart(line, getXYItemRenderer(), XY_DATASET_CREATOR);
    }

    void createMultilineChart(java.util.List<Line> lines){
        createMultiSeriesChart(lines, getXYItemRenderer(), XY_DATASET_CREATOR);
    }

    void createBarChart(Bars bars){
        XYItemRenderer renderer = getXYBarRenderer();
        renderer.setSeriesPaint(0, Colors.YELLOW.getColor());
        createSingleSeriesChart(bars, renderer, XY_BAR_DATASET_CREATOR);
    }

    void createSingleMarkedSeriesChart(MarkedPoints points){
        createSingleSeriesChart(points, getXYLineAndShapeRenderer(), XYZ_DATASET_CREATOR);
    }

    void createMultiMarkedSeriesChart(java.util.List<MarkedPoints> collection){
        createMultiSeriesChart(collection, getXYLineAndShapeRenderer(), XYZ_DATASET_CREATOR);
    }

    void createPlot(String axisXName, String axisYName){
        plot = new XYPlot();
        plot.setDomainAxis(0, getAxis(axisXName));
        plot.setRangeAxis(0, getAxis(axisYName));
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
    }

    BufferedImage getThumbnail(int width, int height){
        JFreeChart chart = new JFreeChart(plot);
        chart.removeLegend();
        return chart.createBufferedImage(width, height);
    }
    
    private void createSingleSeriesChart(PointCollection collection, XYItemRenderer renderer,
                                         IDatasetCreator datasetCreator){
        Data d = new Data();
        d.collections.add(collection);
        d.renderers.add(renderer);
        d.datasetCreator = datasetCreator;
        render(d);
    }

    private void createMultiSeriesChart(java.util.List<? extends PointCollection> collections, XYItemRenderer renderer,
                                        IDatasetCreator datasetCreator){
        Data d = new Data();
        d.collections.addAll(collections);
        d.renderers.addAll(clone(renderer, collections.size()));
        d.datasetCreator = datasetCreator;
        render(d);
    }

    private static double getXStep(PointCollection pointCollection){
        int size = pointCollection.getPoints().size();
        if(size <= 2) return 4;
        else return pointCollection.getPoints().get(0).getX() - pointCollection.getPoints().get(1).getX();
    }
    
    private void render(Data d){
        removeAllDatasets();
        for(int i = 0; i < d.collections.size(); i++){
            XYDataset data = d.datasetCreator.createDataset(d.collections.get(i));
            plot.setDataset(i, data);
            int colorSize = Colors.values().length;
            int currentColor = i%colorSize;

            XYItemRenderer renderer = d.renderers.get(i);
            if(renderer.getSeriesPaint(0) == null)
                renderer.setSeriesPaint(0, Colors.values()[currentColor].getColor());
            applyRenderer(i, plot, d.renderers.get(i));
        }
        createChartPanel(buildChart(chartName));
    }

    private void removeAllDatasets(){
        int datasetCount = getDatasetNumber();
        for(int i = 0; i < datasetCount; i++){
            plot.setDataset(i, null);
        }
    }

    private int getDatasetNumber(){
        return plot.getDatasetCount();
    }

    private void createCombinedPlot(XYPlot plot1, XYPlot plot2){
        CombinedDomainXYPlot combinedPlot = new CombinedDomainXYPlot();
        combinedPlot.setGap(20.0);
        combinedPlot.setDomainAxis(0, getAxis(plot1.getDomainAxis().getLabel()));
        combinedPlot.add(plot1, 1);
        combinedPlot.add(plot2, 1);
        combinedPlot.setOrientation(PlotOrientation.VERTICAL);
        plot = combinedPlot;
    }

    private XYSeriesCollection createEmptyDataset(String datasetName){
        XYSeries series = new XYSeries(datasetName);
        return new XYSeriesCollection(series);
    }

    private NumberAxis getAxis(String axisName){
        final NumberAxis axis = new NumberAxis(axisName);
        axis.setAutoRangeIncludesZero(true);
        axis.setAutoRange(true);
        return axis;
    }

    private java.util.List<XYItemRenderer> clone(XYItemRenderer renderer, int size){
        java.util.List<XYItemRenderer> renderers = new ArrayList<XYItemRenderer>();
        if(renderer instanceof StandardXYItemRenderer)
            for(int i = 0; i < size; i++){
                renderers.add(getXYItemRenderer());
            }
        else if(renderer instanceof XYLineAndShapeRenderer)
            for(int i = 0; i < size; i++){
                renderers.add(getXYLineAndShapeRenderer());
            }
        return renderers;
    }
    
    private XYItemRenderer getXYItemRenderer(){
        XYItemRenderer renderer = new StandardXYItemRenderer(StandardXYItemRenderer.LINES, getToolTipGenerator());
        renderer.setSeriesStroke(0, new BasicStroke(1));
        return renderer;
    }

    private XYItemRenderer getXYBarRenderer(){
        XYBarRenderer renderer = new XYBarRenderer(0.30);
        renderer.setShadowVisible(false);
        renderer.setBaseOutlinePaint(Colors.BLACK.getColor());
        renderer.setBaseOutlineStroke(new BasicStroke(1));
        renderer.setDrawBarOutline(true);

        XYBarPainter barPainter = new StandardXYBarPainter();
        renderer.setBarPainter(barPainter);
        return renderer;
    }

    private XYItemRenderer getXYLineAndShapeRenderer(){
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesToolTipGenerator(0, getXYZToolTipGenerator());
        renderer.setSeriesShape(0, new Ellipse2D.Float(-3, -3, 6, 6));
        renderer.setSeriesOutlinePaint(0, new Color(0, 0, 0));
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1));
        renderer.setSeriesLinesVisible(0, false);
        return renderer;
    }

    private IntervalMarker getIntervalMarker(float start, float end, Color color){
        return new IntervalMarker(start, end, color);
    }

    private void applyRenderer(int datasetNumber, XYPlot currentPlot, XYItemRenderer renderer){
        currentPlot.setRenderer(datasetNumber, renderer);
    }

    private XYToolTipGenerator getToolTipGenerator(){
        return new StandardXYToolTipGenerator(
                StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
                new DecimalFormat("0.0"), new DecimalFormat("0.0")
        );
    }

    private XYZToolTipGenerator getXYZToolTipGenerator(){
/*        return new StandardXYZToolTipGenerator(
                StandardXYZToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
                new DecimalFormat("0.0"), new DecimalFormat("0.0"), new DecimalFormat("time = 0.0[s]")
        );
*/
        return new XYZToolTipGenerator(){
            private static final String DEFAULT_TOOL_TIP_FORMAT = "{0}: ({1})";
            private final NumberFormat zFormat = new DecimalFormat("time = 0.0s");
            @Override
            public String generateToolTip(XYZDataset xyzDataset, int i, int i1) {
                return generateLabelString(xyzDataset, i, i1);
            }
            @Override
            public String generateToolTip(XYDataset xyDataset, int i, int i1) {
                return generateLabelString((XYZDataset)xyDataset, i, i1);
            }

            private String generateLabelString(XYZDataset dataset, int series, int item){
                String result = null;
                Object[] items = createItemArray(dataset, series, item);
                result = MessageFormat.format(DEFAULT_TOOL_TIP_FORMAT, items);
                return result;
            }

            private Object[] createItemArray(XYZDataset dataset, int series, int item){
                Object[] result = new Object[2];
                result[0] = dataset.getSeriesKey(series).toString();

                Number z = dataset.getZ(series, item);
                result[1] = zFormat.format(z);
                return result;
            }
        };
    }

    private void createChartPanel(JFreeChart chart){
        chart.setAntiAlias(true);
        chartPanel = new ChartPanel(chart);
        chartPanel.setZoomAroundAnchor(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setSize(500, 300);
        chart.getXYPlot().setDomainCrosshairVisible(true);
        chart.getXYPlot().setDomainCrosshairLockedOnData(false);
        chart.getXYPlot().setRangeCrosshairVisible(false);
        chartPanel.addChartMouseListener(new ChartMouseListener(){
            @Override
            public void chartMouseClicked(ChartMouseEvent event) {
//                chartPanel.getChart().fireChartChanged();
//                System.out.println(chartPanel.getChart().getXYPlot().getDomainCrosshairValue());
//                event.getChart().getXYPlot().getDomainCrosshairValue();
                Float val = new Float(event.getChart().getXYPlot().getDomainCrosshairValue());
                limits.add(val);
                if(limits.size() == 2 && notifier != null){
                    Collections.sort(limits, new FloatComparator());

                    IntervalData d = new IntervalData();
                    d.setPeriodStart(limits.get(0));
                    d.setPeriodEnd(limits.get(1));

//                    System.out.println("start period = " + limits.get(0));
//                    System.out.println("end period = " + limits.get(1));

                    CrosshairChangedEvent ce = new CrosshairChangedEvent(d);
                    notifier.update(ce);
                    limits = new ArrayList<Float>();
                }
            }
            @Override
            public void chartMouseMoved(ChartMouseEvent event) {
            }
        });
    }

    private JFreeChart buildChart(String graphName){
        return new JFreeChart(graphName, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
    }
}
