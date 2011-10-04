package ru.cos.sim.visualizer.trace.item;

import ru.cos.sim.meters.impl.MeterType;
import ru.cos.sim.visualizer.trace.item.base.LocatableItem;

public class Meter extends LocatableItem {

	public String name;
	public MeterType type;
	
	public Meter(ru.cos.sim.visualizer.traffic.parser.trace.staff.Meter m) {
		super(m.id(), m.getLocation(), null,false,false);
		
		this.name = m.getMeterName();
		this.type = MeterType.valueOf(m.getType());
	}
	
	public Meter(String name,int id ,MeterType type)
	{
		super(id, null);
        this.type = type;
        this.name = name;
	}
	
	public String toString()
	{
		return name;
	}
}
