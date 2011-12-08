package ru.cos.sim.visualizer.traffic.graphs.converter;

import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.framework.MeasuredData;
import ru.cos.sim.meters.framework.TimePeriod;
import ru.cos.sim.visualizer.traffic.graphs.classes.GraphData;

import java.util.List;

public interface DataConverter<S extends GraphData, MD extends MeasuredData<MD>> {
    float[] getValue(MD md);
    String buildValueString(MD md);
    S convertHistory(MeterDTO<MD> meterDTO);
    S convertScheduledHistory(MeterDTO<MD> meterDTO);
    S convertScheduledHistoryByPeriod(MeterDTO<MD> meterDTO, TimePeriod timePeriod); 
    float[][] getHistoryData(MeterDTO<MD> meterDTO);
    List<float[][]> getScheduledHistoryData(MeterDTO<MD> meterDTO);
    float[][] getScheduledHistoryDataByPeriod(MeterDTO<MD> meterDTO, TimePeriod period);
    float[][] getInstantData(MeterDTO<MD> meterDTO);
    List<TimePeriod> getScheduledHistoryPeriods(MeterDTO<MD> meterDTO);
}
