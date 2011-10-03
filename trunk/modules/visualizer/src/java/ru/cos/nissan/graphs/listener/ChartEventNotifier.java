package ru.cos.nissan.graphs.listener;

public interface ChartEventNotifier<E extends ChartEvent> {
    void update(E e);
}
