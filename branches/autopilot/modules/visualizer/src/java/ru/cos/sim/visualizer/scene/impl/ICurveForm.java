package ru.cos.sim.visualizer.scene.impl;

import java.util.ArrayList;

import ru.cos.sim.visualizer.trace.item.base.TrafficLight;

public interface ICurveForm extends ICarKeeper {
	
	ITrafficLight[] completeLights(ArrayList<TrafficLight> lights);
}
