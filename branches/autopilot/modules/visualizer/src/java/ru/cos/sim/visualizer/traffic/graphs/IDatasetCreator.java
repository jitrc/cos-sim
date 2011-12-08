package ru.cos.sim.visualizer.traffic.graphs;

import org.jfree.data.xy.XYDataset;

import ru.cos.sim.visualizer.traffic.graphs.classes.PointCollection;

public interface IDatasetCreator {
    XYDataset createDataset(PointCollection pointCollection);
}
