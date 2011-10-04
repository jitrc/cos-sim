package ru.cos.sim.visualizer.scene.shapes;

import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.trace.TraceHandler;
import ru.cos.sim.visualizer.trace.item.Lane;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;
import ru.cos.sim.visualizer.traffic.parser.trace.location.LaneLocation;
import ru.cos.sim.visualizer.traffic.parser.trace.staff.LineDirection;

public class LineDirectionShape extends TexturedRectangleShape {

	private static String[] texturesLocations = {
		"/textures/staff/directions/right.png",
		"/textures/staff/directions/left.png",
		"/textures/staff/directions/middle2.png",
		"/textures/staff/directions/lm2.png",
		"/textures/staff/directions/rm2.png","" +
		"/textures/staff/directions/rl.png",
		"/textures/staff/directions/rml2.png"
	};
	private static float length = 2.7f;
	private static float width = 2.4f;
	private static float distance = 0.2f;
	
	public LineDirectionShape(LineDirection ld) {
		super();
		
		TraceHandler handler = SimulationSystemManager.getInstance().getTraceHandler();
		LaneLocation loc = (LaneLocation)ld.getLocation();
		Lane lane = handler.getLane(loc);
		Vector2f direction = lane.getBegin().subtract(lane.getEnd()).
		normalizeLocal();
		Vector2f end = direction.mult(distance).addLocal(lane.getEnd());
		direction.normalizeLocal();
		Vector2f ort = direction.clone();
		ort.rotate90();
		direction.multLocal(length/2.0f);
		Vector2f pos = direction.add(end);
		ort.normalizeLocal().multLocal(width/2.0f);
		direction.negateLocal();
		String path = "";
		switch (ld.directionType) {
		case Left : path = texturesLocations[1];
			break;
		case Right : path= texturesLocations[0];
			break;
		case LeftMiddle : path = texturesLocations[3];
			break;
		case RightLeft : path = texturesLocations[5];
			break;
		case RightMiddle : path = texturesLocations[4];
			break;
		case Middle : path = texturesLocations[2];
			break;
		case RightMiddleLeft : path = texturesLocations[6];
			break;
		}
		this.setTexture(path);
		//ort.normalizeLocal().multLocal(length/2.0f);
		//direction.normalizeLocal().multLocal(width/2.0f);
		this.set(pos.x, pos.y, ort.x, ort.y, -direction.x, -direction.y);
	}
	
}
