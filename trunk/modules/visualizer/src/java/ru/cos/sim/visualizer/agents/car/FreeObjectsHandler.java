package ru.cos.sim.visualizer.agents.car;

import java.util.Stack;

import ru.cos.sim.visualizer.scene.shapes.AbstractItem;
import ru.cos.sim.visualizer.scene.shapes.CarForm;
import ru.cos.sim.visualizer.scene.shapes.TruckForm;
import ru.cos.sim.visualizer.trace.item.Car;
import ru.cos.sim.visualizer.trace.item.Car.CarType;

public class FreeObjectsHandler {

	private static FreeObjectsHandler instance;
	
	public Stack<CarForm> carStorage;
	public Stack<TruckForm> truckStorage;
	
	private FreeObjectsHandler()
	{
		carStorage = new Stack<CarForm>();
		truckStorage = new Stack<TruckForm>();
	}
	
	public void dispose()
	{
		instance = null;
	}
	
	public static FreeObjectsHandler getInstance()
	{
		if (instance == null) {
			instance = new FreeObjectsHandler();
		}
		return instance;
	}
	
	public boolean isStorageEmpty(CarType type)
	{
		return (type == CarType.LightCar) ? carStorage.isEmpty() : truckStorage.isEmpty();
	}
	
	public AbstractItem getFreeObject(CarType type)
	{
		return (type == CarType.LightCar) ? carStorage.pop() : truckStorage.pop();
	}
	
	public void push(Car car)
	{
		if (car.getType() == CarType.LightCar) carStorage.push((CarForm)car.getForm()); else
			truckStorage.push((TruckForm)car.getForm());
	}
}
