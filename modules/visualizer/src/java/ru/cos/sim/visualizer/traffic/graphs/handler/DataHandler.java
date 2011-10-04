package ru.cos.sim.visualizer.traffic.graphs.handler;

import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.framework.MeasuredData;
import ru.cos.sim.visualizer.traffic.graphs.gui.IGraphGUI;
import ru.cos.sim.visualizer.traffic.graphs.impl.IDataRequestable;
import ru.cos.sim.visualizer.traffic.graphs.impl.IRequest;

public class DataHandler<MD extends MeasuredData<MD>> {
    public static enum RequestAction{
        History, ScheduledHistory, InstantData
    }

    private IDataRequestable<MD> iDataRequestable;
    private IGraphGUI<MD> graphGUI;
    private RequestAction requestAction;

    public DataHandler(IDataRequestable<MD> iDataRequestable, IGraphGUI<MD> graphGUI){
        this.iDataRequestable = iDataRequestable;
        this.graphGUI = graphGUI;
    }

    private class Request implements IRequest<MD> {
        private long id;

        public Request(long id){this.id = id;}
        
        @Override
        public long getId(){
            return id;
        }
        
        @Override
        public void receiveData(MeterDTO<MD> meterDTO) {
            switch (requestAction){
                case History:
                    DataHandler.this.graphGUI.refreshHistory(meterDTO);
                    break;
                case ScheduledHistory:
                    DataHandler.this.graphGUI.refreshScheduledHistory(meterDTO);
                    break;
                case InstantData:
                    DataHandler.this.graphGUI.refreshInstant(meterDTO);
            }
        }
    }

    public void sendRequest(RequestAction requestAction){
        Request req = new Request(graphGUI.getId());
        this.requestAction = requestAction;
        iDataRequestable.requestData(req);
    }
}
