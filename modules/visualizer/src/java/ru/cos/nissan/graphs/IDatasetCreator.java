package ru.cos.nissan.graphs;

import org.jfree.data.xy.XYDataset;
import ru.cos.nissan.graphs.classes.PointCollection;

public interface IDatasetCreator {
    XYDataset createDataset(PointCollection pointCollection);
}
