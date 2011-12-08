/**
 * 
 */
package ru.cos.sim.meters.data;

import ru.cos.sim.meters.framework.ModesInitData;
import ru.cos.sim.meters.impl.MeterType;

/**
 * Meter Data
 * @author zroslaw
 */
public class MeterData {
	
	private int id;
	
	private MeterType type;
	
	private String name;
	
	private ModesInitData modesInitData;
	
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
	
	public void setModesInitData(ModesInitData modesInitData) {
		this.modesInitData = modesInitData;
	}
	
	public ModesInitData getModesInitData() {
		return modesInitData;
	}
	
}
