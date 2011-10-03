package ru.cos.nissan.graphs.report;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import ru.cos.sim.communication.dto.MeterDTO;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class TrafficSimulationReport {
    @XStreamAlias("Name")
    private final String name;
    @XStreamAlias("ModelName")
    private final String modelName;
    @XStreamAlias("Date")
    private final Date date;
    @XStreamAlias("VirtualTime")
    private final float virtualTime;
    @XStreamAlias("RealTime")
    private final float realTime;
    @XStreamOmitField()
    private final float timeCoefficient;
    @XStreamAlias("TotalVehiclesNumber")
    private final long totalVehiclesNumber;
    @XStreamAlias("TotalTravelTime")
    private final float totalTravelTime;
    @XStreamAlias("AverageTravelTime")
    private final float averageTravelTime;
    @XStreamAlias("AverageTravelSpeed")
    private final float averageTravelSpeed;

    @XStreamAlias("Meters")
    private final List<MeterDTO> meterList;

    public String getName(){ return name;}
    public String getModelName(){ return modelName;}
    public Date getDate(){ return date;}
    public float getVirtualTime(){ return virtualTime;}
    public float getRealTime(){ return realTime;}
    public float getTimeCoefficient(){ return timeCoefficient;}
    public long getTotalVehiclesNumber(){ return totalVehiclesNumber;}
    public float getTotalTravelTime(){ return totalTravelTime;}
    public float getAverageTravelTime(){ return averageTravelTime;}
    public float getAverageTravelSpeed(){ return averageTravelSpeed;}
    public List<MeterDTO> getMeterList(){ return new ArrayList<MeterDTO>(meterList);}

    private TrafficSimulationReport(TrafficSimulationReportBuilder builder){
        name = builder.name;
        modelName = builder.modelName;
        date = builder.date;
        virtualTime = builder.virtualTime;
        realTime = builder.realTime;
        if(realTime != 0 ) timeCoefficient = virtualTime / realTime;
        else timeCoefficient = 0.0f;
        totalVehiclesNumber = builder.totalVehiclesNumber;
        totalTravelTime = builder.totalTravelTime;
        averageTravelTime = builder.averageTravelTime;
        averageTravelSpeed = builder.averageTravelSpeed;
        meterList = new ArrayList<MeterDTO>();
        for(MeterDTO m : builder.meterList)
            meterList.add(m);
    }

    public static class TrafficSimulationReportBuilder{
        private final String name;
        private String modelName;
        private Date date;
        private float virtualTime;
        private float realTime;
        private long totalVehiclesNumber;
        private float totalTravelTime;
        private float averageTravelTime;
        private float averageTravelSpeed;

        private List<MeterDTO> meterList;

        public TrafficSimulationReport build(){
            return new TrafficSimulationReport(this);
        }

        public TrafficSimulationReportBuilder(String name){
            this.name = name;
            meterList = new ArrayList<MeterDTO>();
        }

        public TrafficSimulationReportBuilder modelName(String modelName){
            this.modelName = modelName;
            return this;
        }

        public TrafficSimulationReportBuilder date(Date date){
            this.date = date;
            return this;
        }

        public TrafficSimulationReportBuilder virtualTime(float virtualTime){
            this.virtualTime = virtualTime;
            return this;
        }

        public TrafficSimulationReportBuilder realTime(float realTime){
            this.realTime = realTime;
            return this;
        }

        public TrafficSimulationReportBuilder totalVehiclesNumber(long totalVehiclesNumber){
            this.totalVehiclesNumber = totalVehiclesNumber;
            return this;
        }

        public TrafficSimulationReportBuilder totalTravelTime(float totalTravelTime){
            this.totalTravelTime = totalTravelTime;
            return this;
        }

        public TrafficSimulationReportBuilder averageTravelTime(float averageTravelTime){
            this.averageTravelTime = averageTravelTime;
            return this;
        }

        public TrafficSimulationReportBuilder averageTravelSpeed(float averageTravelSpeed){
            this.averageTravelSpeed = averageTravelSpeed;
            return this;
        }

        public TrafficSimulationReportBuilder addMeter(MeterDTO meterDTO){
            this.meterList.add(meterDTO);
            return this;
        }
    }
}
