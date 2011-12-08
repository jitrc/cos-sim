package ru.cos.sim.visualizer.trace.item;

import java.util.ArrayList;

import ru.cos.sim.visualizer.agents.car.CarPosition;
import ru.cos.sim.visualizer.math.BezierLane;
import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.scene.impl.ICurveForm;
import ru.cos.sim.visualizer.scene.impl.ITrafficLight;
import ru.cos.sim.visualizer.scene.shapes.TransitionForm;
import ru.cos.sim.visualizer.trace.item.base.TrafficLight;
import ru.cos.sim.visualizer.traffic.utils.RuleCutter;

public class BezierRule extends BezierLane implements ICurveForm {
	
	public BezierRule(Vector2f[] points, float width) {
		super(points, width);
	}

	@Override
	public CarPosition getPosition(float position) {
		return getPosition(position, null);
	}

	@Override
	public CarPosition getPosition(float position, CarPosition pos) {
		//if (pos == null) pos = new CarPosition(new Vector2f(), new Vector2f()); 
		/*Vector2f tang = this.getTangent(position);
		Vector2f p = this.getPoint(position);
		pos.direction = tang.normalizeLocal();
		pos.position = p;*/
		
		return this.getPoint(position);
	}

	@Override
	public ITrafficLight[] completeLights(ArrayList<TrafficLight> lights) {
		BezierLane lane = new BezierLane(base_points, width);
		RuleCutter cutter = new RuleCutter(0);
		TrafficLight[] trs = new TrafficLight[lights.size()];
		trs = lights.toArray(trs);
		ArrayList<TransitionForm> forms = cutter.generateTrafficLightsSequence(lane, trs);
		ITrafficLight[] result = new ITrafficLight[forms.size()]; 
		return forms.toArray(result);
	}
	
	
	
}
