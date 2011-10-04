package ru.cos.sim.visualizer.traffic.graphs.report;

import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.visualizer.trace.item.Meter;
import ru.cos.sim.visualizer.traffic.graphs.gui.GraphGUIFactory;
import ru.cos.sim.visualizer.traffic.graphs.impl.IDataRequestable;
import ru.cos.sim.visualizer.traffic.graphs.impl.IRequest;

import java.util.ArrayList;

public class ReportDataManager implements IDataRequestable{
    private final TrafficSimulationReport report;

    protected static ReportDataManager instance;

    protected ArrayList<Meter> meters;

    public ReportDataManager(TrafficSimulationReport report){
        this.report = report;
        this.meters = new ArrayList<Meter>();
        for(MeterDTO dto : report.getMeterList()){
            Meter m = new Meter(dto.getName(), dto.getId(), dto.getType());
            this.addMeter(m);
        }
    }

    public void addMeter(Meter m){
        this.meters.add(m);
    }

    public Object[] getMeters(){
        return meters.toArray();
    }

    public void addGraph(ru.cos.sim.visualizer.trace.item.Meter m){
        GraphGUIFactory.newInstance(m, this);
    }

    @Override
    public void requestData(IRequest r) {
        for(MeterDTO dto : report.getMeterList()){
            if(dto.getId() == r.getId())
                r.receiveData(dto);
        }
    }

    @Override
    public void checkRequests() {
    }
}
