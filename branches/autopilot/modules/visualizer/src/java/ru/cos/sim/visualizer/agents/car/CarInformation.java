package ru.cos.sim.visualizer.agents.car;

import ru.cos.sim.visualizer.trace.item.Car.CarType;
import ru.cos.sim.visualizer.traffic.parser.trace.location.Location;

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
