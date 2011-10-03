package ru.cos.scene.impl;

import java.util.ArrayList;

import ru.cos.trace.item.base.TrafficLight;

public interface ICurveForm extends ICarKeeper {
	
	ITrafficLight[] completeLights(ArrayList<TrafficLight> lights);
}
