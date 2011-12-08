package ru.cos.sim.visualizer.traffic.graphs.listener;

public interface ChartEventNotifier<E extends ChartEvent> {
    void update(E e);
}
