package ru.cos.sim.visualizer.scene.shapes;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.opengl.GL11;

import ru.cos.sim.visualizer.color.Color;
import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.renderer.Renderer.RenderType;
import ru.cos.sim.visualizer.trace.item.Meter;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;

public class MeterForm extends IsoscelesTriangle {

	public static float distance = 10f;
	public static float width = 3f;
	public static float height = 5f;
	
	protected Vector2f position;
	protected Meter meter;
	
	public MeterForm(Meter m) {
		this.meter = m;
		
		this.color = Color.lightGray;
		this.position = m.getDirection().normalizeLocal().multLocal(distance).addLocal(m.getLanePosition());
		this.set(this.position.x, this.position.y, width , height);
	}

	@Override
	public void render(RenderType mode) {
		if (mode == RenderType.Picking) {
			//GL11.glPopName();
			GL11.glPushName(SimulationSystemManager.getInstance().getPickingHandler().
					addObject(meter));
		}
		glBegin(GL_TRIANGLES);
		glColor3f(color.r, color.g, color.b);
		glVertex2f(x1, y1);
		glVertex2f(x2, y2);
		glVertex2f(x3, y3);
		glEnd();
		if (mode == RenderType.Picking) {
			GL11.glPopName();
			//GL11.glPushName(SimulationSystemManager.getInstance().getPickingHandler().
			//		addObject(meter));
		}
	}
	
	
}
