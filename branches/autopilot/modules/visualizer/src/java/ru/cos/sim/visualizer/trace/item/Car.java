package ru.cos.sim.visualizer.trace.item;

import ru.cos.sim.communication.dto.VehicleDTO;
import ru.cos.sim.visualizer.agents.car.CarPosition;
import ru.cos.sim.visualizer.scene.shapes.CarForm;
import ru.cos.sim.visualizer.scene.shapes.TruckForm;
import ru.cos.sim.visualizer.traffic.parser.trace.location.Location;

public class Car extends Vehicle {
	public static enum CarType{
		LightCar,
		Truck
	}
	
	protected VehicleDTO information;
	protected CarPosition lastPosition;
	protected int mark = -1;
	
	public Car(int uid, CarType type) {
		super(uid);
		
		if (type == CarType.LightCar) this.setForm(new CarForm(this));
		if (type == CarType.Truck) this.setForm(new TruckForm(this));
	}
	
	@Override
	public void move(CarPosition p) {
		// TODO Auto-generated method stub
		super.move(p);
		
		this.lastPosition = p;
	}



	public CarType getType()
	{
		if (this.form instanceof CarForm) return CarType.LightCar; 
			else return CarType.Truck; 
	}

	public CarPosition getLastPosition() {
		return lastPosition;
	}
	
	public void mark(int m)
	{
		this.mark = m;
	}
	
	public int getMark()
	{
		return this.mark;
	}

	public VehicleDTO getInformation() {
		return information;
	}

	public void setInformation(VehicleDTO information) {
		this.information = information;
		if (this.form instanceof CarForm) {
			((CarForm) this.form).setDimensions(information.getWidth(),information.getLength());
		} else {
			((TruckForm) this.form).setDimensions(information.getWidth(),information.getLength());
		}
	}
	
}
