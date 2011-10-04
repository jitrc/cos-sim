package ru.cos.sim.visualizer.traffic.graphs.integrator;

import ru.cos.sim.visualizer.traffic.graphs.classes.Point;

import java.util.Comparator;

public class PointComparator implements Comparator<Point> {
    @Override
    public int compare(Point o1, Point o2) {
        return Float.compare(o1.getX(), o2.getX());
    }
}
