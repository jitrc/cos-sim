package ru.cos.scene.shapes;

import org.lwjgl.opengl.GL11;

import ru.cos.nissan.core.SimulationSystemManager;
import ru.cos.renderer.Renderer.RenderType;
import ru.cos.trace.item.Car;

public class CarForm extends TexturedRectangleShape {

	public float length = 3.5f*1.3f*0.4f;
	public float width = 7.0f*0.5f*0.4f;
	public static final String textureLocation = "/textures/vehicles/carBasic.png"; 
	
	protected Car car;
	
	public CarForm(Car car) {
		super();
		this.car = car;
		this.set(0, 0, length, 0, 0, width);
		this.setTexture(textureLocation);
	}
	
	public void setDimensions(float w , float l) {
		length = l;///0.89f;
		width = w;///0.582f;
		this.set(0, 0, length, 0, 0, width);
	}

	@Override
	public void applyRotation(float x, float y) {
		this.set(0, 0, x * length, y*length, - y*width, x*width);
	}
	
	@Override
	public void render(RenderType mode) {
		if (mode == RenderType.Picking) {
			GL11.glPushName(SimulationSystemManager.getInstance().getPickingHandler().
					addObject(car));
		}
		
		if (texture != null) texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
		//GL11.glColor3f(color.r, color.g, color.b);
		this.texturedVertex(1, 0, x1, y1);
		this.texturedVertex(1, 1, x2, y2);
		this.texturedVertex(0, 1, x3, y3);
		this.texturedVertex(0, 0, x4, y4);
		GL11.glEnd();
		
		if (mode == RenderType.Picking) {
			GL11.glPopName();
		}
	}
	
	
	
}
