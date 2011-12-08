package ru.cos.sim.visualizer.scene.shapes;

import static org.lwjgl.opengl.GL11.*;

import ru.cos.sim.visualizer.renderer.Renderer.RenderType;
import ru.cos.sim.visualizer.renderer.impl.IRenderable;

public class TriangleItem extends AbstractItem implements IRenderable {

	public float x1;
	public float y1;
	public float x2;
	public float y2;
	public float x3;
	public float y3;
	
	protected TriangleItem()
	{
		
	}
	
	protected void set(float x1, float y1, float x2, float y2, float x3,
			float y3)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
	}
	
	public TriangleItem(float x1, float y1, float x2, float y2, float x3,
			float y3) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
	}



	@Override
	public void render(RenderType mode) {
		glBegin(GL_TRIANGLES);
		glColor3f(color.r, color.g, color.b);
		glVertex2f(x1, y1);
		glVertex2f(x2, y2);
		glVertex2f(x3, y3);
		glEnd();
	}
	
}
