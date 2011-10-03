package ru.cos.scene.shapes;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import ru.cos.color.Color;
import ru.cos.math.Vector2f;
import ru.cos.renderer.Renderer.RenderType;
import ru.cos.scene.impl.ITrafficLight;

public class TransitionForm extends AbstractItem implements ITrafficLight{
	protected ArrayList<Vector2f> points;
	protected ArrayList<Integer> indexes;
	protected ru.cos.trace.item.base.TrafficLight.Color currentColor;
	protected Float position = new Float(0);
	
	public TransitionForm() {
		super();
		this.points = new ArrayList<Vector2f>();
		this.indexes = new ArrayList<Integer>();
		this.color = Color.transitioRule;
		this.currentColor = ru.cos.trace.item.base.TrafficLight.Color.None;
	}
	
	

	public Float getPosition() {
		return position;
	}



	public void setPosition(Float position) {
		this.position = position;
	}



	public void addIndex(Integer i)
	{
		this.indexes.add(i);
	}
	
	public void addPoint(Vector2f point)
	{
		this.points.add(point);
	}
	
	public int getVertexCount()
	{
		return points.size();
	}
	
	@Override
	public void render(RenderType mode) {
		
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor3f(color.r, color.g, color.b);
		for (int i = 0; i < indexes.size(); i++)
		{
			GL11.glVertex2f(points.get(indexes.get(i)).x, points.get(indexes.get(i)).y);
		}
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3f(color.r, color.g, color.b);
		for (int i = 0; i < indexes.size(); i++)
		{
			GL11.glVertex2f(points.get(indexes.get(i)).x, points.get(indexes.get(i)).y);
		}
		GL11.glEnd();
	}

	@Override
	public void switchLight(ru.cos.trace.item.base.TrafficLight.Color color) {
		this.currentColor = color;
		switch (color) {
		case Green:
			this.setColor(Color.greenLight);
			break;
		case Yellow:
			this.setColor(Color.yellow);
			break;
		case Red:
			this.setColor(Color.red);
			break;

		}
	}

	@Override
	public ru.cos.trace.item.base.TrafficLight.Color getLightColor() {
		return this.currentColor;
	}

}
