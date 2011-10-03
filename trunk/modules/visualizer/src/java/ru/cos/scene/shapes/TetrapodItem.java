package ru.cos.scene.shapes;

import org.lwjgl.opengl.GL11;

import ru.cos.exception.OglException;
import ru.cos.math.Vector2f;
import ru.cos.renderer.Renderer.RenderType;
import ru.cos.renderer.impl.IRenderable;

public class TetrapodItem extends AbstractItem implements IRenderable {

	protected float[] points;
	
	protected TetrapodItem()
	{
	}
	
	public TetrapodItem(Vector2f v1, Vector2f v2, Vector2f v3, Vector2f v4)
	{
		this(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y);
	}
	
	public TetrapodItem(float x1,float y1,float x2,float y2,float x3,float y3,float x4,float y4)
	{
		points = new float[8];
		points[0] = x1;
		points[1] = y1;
		points[2] = x2;
		points[3] = y2;
		points[4] = x3;
		points[5] = y3;
		points[6] = x4;
		points[7] = y4;
	}
	
	public TetrapodItem(float[] points)
	{
		if (points == null || points.length != 8) throw new OglException("Points in Tetrapod constructor must be not null and have length equals to 8");
		this.points = points;
	}
	
	@Override
	public void render(RenderType mode) {
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex2f(points[0], points[1]);
		GL11.glVertex2f(points[2], points[3]);
		GL11.glVertex2f(points[4], points[5]);

		GL11.glVertex2f(points[4], points[5]);
		GL11.glVertex2f(points[6], points[7]);
		GL11.glVertex2f(points[0], points[1]);
		GL11.glEnd();
	}

}
