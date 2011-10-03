package ru.cos.nissan.graphs.gui;

import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.framework.MeasuredData;

public interface IGraphGUI<MD extends MeasuredData<MD>> {
    long getId();
    void refreshHistory(MeterDTO<MD> meterDTO);
    void refreshScheduledHistory(MeterDTO<MD> meterDTO);
    void refreshInstant(MeterDTO<MD> meterDTO);
}
