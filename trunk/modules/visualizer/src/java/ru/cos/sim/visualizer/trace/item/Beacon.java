package ru.cos.sim.visualizer.trace.item;

import ru.cos.sim.visualizer.trace.item.base.LocatableItem;

public class Beacon extends LocatableItem {

	public Beacon(ru.cos.sim.visualizer.traffic.parser.trace.staff.Beacon beacon)
	{
		super(beacon.id(),beacon.getLocation(),beacon.getSide());
	}
}
