package ru.cos.trace.item;

import ru.cos.math.Vector2f;
import ru.cos.nissan.core.SimulationSystemManager;
import ru.cos.nissan.parser.trace.location.LaneLocation;
import ru.cos.renderer.Renderer.RenderType;
import ru.cos.scene.shapes.LaneClosureForm;
import ru.cos.trace.item.base.StaticEntity;

public class LaneClosure extends StaticEntity{

	private float duration = 0;
	private float startTime = 0;
	
	public LaneClosure(ru.cos.nissan.parser.trace.staff.LaneClosure lc) {
		super(lc.id());
		
		LaneLocation loc = (LaneLocation) lc.getLocation();
		Lane lane = SimulationSystemManager.getInstance().getTraceHandler().getLane(loc);
		
		this.startTime = lc.getStartTime();
		this.duration = lc.getDuration();
		
		float position = 0;
		LaneClosureForm form = new LaneClosureForm();
		this.setForm(form);
		if (lane.getLength() > loc.getPosition() + lc.getLength() ) {
			Vector2f end = lane.normalVector.mult(lc.getLength() + loc.getPosition());
			end.addLocal(lane.bw);
			Vector2f begin = lane.normalVector.mult(loc.getPosition());
			begin.addLocal(lane.bw);
			Vector2f ort = lane.normalVector.clone();
			ort.rotate90();
			ort.multLocal(lane.getWidth()*0.7f/2.0f);
			
			form.addPart(begin.add(ort),
					end.add(ort),
					end.subtract(ort), 
					begin.subtract(ort));
			
			return;
		} else {
			Vector2f ort = lane.normalVector.clone();
			ort.rotate90();
			ort.multLocal(lane.getWidth()/2.0f);
			
			form.addPart(lane.bw.add(ort),
					new Vector2f(lane.rightSide.endX,lane.rightSide.endY),
					new Vector2f(lane.leftSide.endX,lane.leftSide.endY), 
					lane.ew.subtract(ort));
		}
		
		while (position < lc.getLength()) {
			if (position + lane.length < lc.getLength()) {
				form.addPart(new Vector2f(lane.rightSide.startX,lane.rightSide.startY),
						new Vector2f(lane.rightSide.endX,lane.rightSide.endY),
						new Vector2f(lane.leftSide.endX,lane.leftSide.endY), 
						new Vector2f(lane.leftSide.startX,lane.leftSide.startY));
			} else {
				Vector2f pos = lane.normalVector.mult(lc.getLength() - position);
				Vector2f ort = lane.normalVector.clone();
				ort.rotate90();
				ort.multLocal(lane.getWidth()/2.0f);
				
				form.addPart(new Vector2f(lane.rightSide.startX,lane.rightSide.startY),
						pos.add(ort),
						pos.subtract(ort), 
						new Vector2f(lane.leftSide.startX,lane.leftSide.startY));
			}
		}
	}

	public void draw(RenderType mode) {
		double time = SimulationSystemManager.getInstance().getCurrentTime();
		if ((time > startTime) && (time < startTime + duration)) {
			super.draw(mode);
		}
		
	}
	
}
