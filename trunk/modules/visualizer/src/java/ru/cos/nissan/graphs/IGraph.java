package ru.cos.nissan.graphs;

import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.framework.MeasuredData;
import ru.cos.sim.meters.framework.TimePeriod;
import ru.cos.nissan.graphs.converter.DataConverter;
import ru.cos.nissan.graphs.classes.GraphData;
import ru.cos.nissan.graphs.classes.IntervalData;
import ru.cos.nissan.graphs.listener.ChartEventNotifier;
import ru.cos.nissan.graphs.listener.CrosshairChangedEvent;

import java.awt.image.BufferedImage;

public interface IGraph<GD extends GraphData, MD extends MeasuredData<MD>> {
    String getGraphName();
    GD refreshHistory(MeterDTO<MD> meterDTO);
    GD refreshScheduledHistory(MeterDTO<MD> meterDTO);
    GD refreshScheduledHistoryByPeriod(MeterDTO<MD> meterDTO, TimePeriod period);
    GD refreshInstant(MeterDTO<MD> meterDTO);
    DataConverter<GD, MD> getConverter();
    long getId();
    BufferedImage getThumbnail(int width, int height);
    void setCrosshairEventNotifier(ChartEventNotifier<CrosshairChangedEvent> listener);
    float getAverageValue(GD gd, IntervalData d);
}
