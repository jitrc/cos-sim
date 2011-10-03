package ru.cos.trace.item;

import ru.cos.agents.car.CarPosition;
import ru.cos.trace.item.base.MovableEntity;

public class Vehicle extends MovableEntity {
	
	public Vehicle(int uid) {
		super(uid);
	}
	
	public void move(CarPosition p)
	{
		this.form.setTranslation(p.position.x, p.position.y, 0);
		this.form.applyRotation(p.direction.x, p.direction.y);
	}
	
	
}
