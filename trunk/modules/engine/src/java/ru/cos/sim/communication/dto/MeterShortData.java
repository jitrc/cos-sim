/**
 * 
 */
package ru.cos.sim.communication.dto;

import ru.cos.sim.meters.impl.AbstractMeter;
import ru.cos.sim.meters.impl.MeterType;
import ru.cos.sim.road.init.data.LocationData;

/**
 * Short data about meter.
 * @author zroslaw
 */
public class MeterShortData{
	
	private int id;
	
	private MeterType type;
	
	private String name;
	
	private LocationData location;

	public MeterShortData(AbstractMeter meter) {
		this.id = meter.getMeterId();
		this.type = meter.getType();
		this.name = meter.getName();
		this.location = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MeterType getType() {
		return type;
	}

	public void setType(MeterType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocationData getLocation() {
		return location;
	}
	
	public void setLocation(LocationData location) {
		this.location = location;
	}
	
}
