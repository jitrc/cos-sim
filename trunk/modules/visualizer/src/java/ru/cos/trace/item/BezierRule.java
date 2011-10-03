package ru.cos.trace.item;

import java.util.ArrayList;

import ru.cos.agents.car.CarPosition;
import ru.cos.math.BezierLane;
import ru.cos.math.Vector2f;
import ru.cos.nissan.utils.RuleCutter;
import ru.cos.scene.impl.ICurveForm;
import ru.cos.scene.impl.ITrafficLight;
import ru.cos.scene.shapes.TransitionForm;
import ru.cos.trace.item.base.TrafficLight;

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
