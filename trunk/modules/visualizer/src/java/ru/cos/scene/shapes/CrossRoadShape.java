package ru.cos.scene.shapes;

import java.util.ArrayList;

import ru.cos.color.Color;
import ru.cos.math.Vector2f;
import ru.cos.nissan.parser.geometry.Point;
import ru.cos.renderer.Renderer.RenderType;
import static org.lwjgl.opengl.GL11.*;

public class CrossRoadShape extends AbstractItem {

	protected Vector2f[] points;
	
	public CrossRoadShape(ArrayList<Point> points)
	{
		super();
		this.setColor(Color.asphalt);
		this.points = new Vector2f[points.size()];
		for (int i = 0; i < points.size(); i++)
		{
			this.points[i] = new Vector2f(points.get(i).x,points.get(i).y);
		}
	}
	
	@Override
	public void render(RenderType mode) {
		glBegin(GL_TRIANGLES);
		//glColor4f(color.r, color.g, color.b, color.a);
		glColor4f(this.color.r,color.g,color.b,color.a);
//		glVertex2f(0,0);
//		glVertex2f(500,500);
//		glVertex2f(0,500);
			for (int i = 1 ; i < points.length - 1 ; i++)
			{
				glVertex2f(points[0].x, points[0].y);
				glVertex2f(points[i].x, points[i].y);
				glVertex2f(points[i+1].x, points[i+1].y);
			}
		glEnd();
	}

}
