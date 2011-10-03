package ru.cos.agents.car;

import ru.cos.nissan.parser.trace.location.Location;
import ru.cos.trace.item.Car.CarType;

public class CarInformation {
	public Location location;
	public CarType carType;
	public float speed;
	public CarPosition position;
	
	public CarInformation(Location location, CarType carType, float speed,
			CarPosition position) {
		super();
		this.location = location;
		this.carType = carType;
		this.speed = speed;
		this.position = position;
	}
	
	
}
