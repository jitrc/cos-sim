/**
 * 
 */
package ru.cos.sim.communication.dto;

import ru.cos.sim.meters.framework.MeasuredData;
import ru.cos.sim.meters.framework.MeasurementHistory;
import ru.cos.sim.meters.framework.ScheduledData;
import ru.cos.sim.meters.impl.AbstractMeter;
import ru.cos.sim.meters.impl.MeterType;
import ru.cos.sim.road.init.data.LocationData;

/**
 *
 * @author zroslaw
 */
public class MeterDTO<T extends MeasuredData<T>> extends AbstractDTO{
	
	private int id;
	private MeterType type;
	private String name;
	private LocationData location;
	private T instantMeasuredData;
	private T averageData;
	private MeasurementHistory<T> history;
	private ScheduledData<T> scheduledAverageData;
	private ScheduledData<MeasurementHistory<T>> scheduledHistoryData;
	
	/**
	 * Construct DTO on the base of abstract meter
	 * @param meter
	 */
	public MeterDTO(AbstractMeter<T> meter) {
		this.id = meter.getMeterId();
		this.type = meter.getType();
		this.name = meter.getName();
		// copy instant measured data, if any
		if (meter.getInstantMeasuredData()!=null){
			this.instantMeasuredData = (T) meter.getInstantMeasuredData().clone();
		}
		// copy period average data, if any
		if (meter.getAverageData()!=null){
			this.averageData = meter.getAverageData().clone();
		}
		// copy history data, if any
		if (meter.getMeasurementHistory()!=null){
			this.history = meter.getMeasurementHistory().clone();
		}
		// copy scheduled periods average data, if any
		if (meter.getScheduledAverageData()!=null){
			this.scheduledAverageData = meter.getScheduledAverageData().clone();
		}
		// copy scheduled periods history data, if any
		if (meter.getScheduledHistoryData()!=null){
			this.scheduledHistoryData = meter.getScheduledHistoryData();
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public T getInstantMeasuredData() {
		return instantMeasuredData;
	}
	public void setInstantMeasuredData(T instantMeasuredData) {
		this.instantMeasuredData = instantMeasuredData;
	}
	public T getAverageData() {
		return averageData;
	}
	public void setAverageData(T averageData) {
		this.averageData = averageData;
	}
	public MeasurementHistory<T> getHistory() {
		return history;
	}
	public void setHistory(MeasurementHistory<T> history) {
		this.history = history;
	}
	public ScheduledData<T> getScheduledAverageData() {
		return scheduledAverageData;
	}
	public void setScheduledAverageData(ScheduledData<T> scheduledAverageData) {
		this.scheduledAverageData = scheduledAverageData;
	}
	public ScheduledData<MeasurementHistory<T>> getScheduledHistoryData() {
		return scheduledHistoryData;
	}
	public void setScheduledHistoryData(
			ScheduledData<MeasurementHistory<T>> scheduledHistoryData) {
		this.scheduledHistoryData = scheduledHistoryData;
	}
	public void setLocation(LocationData location) {
		this.location = location;
	}
	public LocationData getLocation() {
		return location;
	}
	public void setType(MeterType type) {
		this.type = type;
	}
	public MeterType getType() {
		return type;
	}

	@Override
	public DTOType getDynamicObjectType(){
		return DTOType.MeterDTO;
	}
	
}
