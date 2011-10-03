package ru.cos.trace.item;

import ru.cos.trace.item.base.LocatableItem;

public class Beacon extends LocatableItem {

	public Beacon(ru.cos.nissan.parser.trace.staff.Beacon beacon)
	{
		super(beacon.id(),beacon.getLocation(),beacon.getSide());
	}
}
