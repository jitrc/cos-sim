package ru.cos.nissan.graphs.impl;

import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.framework.MeasuredData;

public interface IRequest<MD extends MeasuredData<MD>> {
    long getId();
    void receiveData(MeterDTO<MD> meterDTO);
}
