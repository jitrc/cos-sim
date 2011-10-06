package ru.cos.sim.visualizer.scene.shapes;

import org.lwjgl.opengl.GL11;

import ru.cos.sim.visualizer.renderer.Renderer.RenderType;
import ru.cos.sim.visualizer.trace.item.Car;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;

public class TruckForm extends TexturedRectangleShape {

	//Values to normalize car width and length. We must do it due to
	//texture is a square but vehicle has rectangle form. So real car picture 
	//is only a part of the texture
	private final float length_normalizer = 0.890625f;
	private final float width_normalizer = 0.421875f;
	
	public float length = 6.0f*0.4f;
	public float width = 3.8f*0.4f;
	public static final String textureLocation = "/textures/vehicles/truckBasic.png";
	
	protected Car car;
	
	public TruckForm(Car car) {
		super();
		this.car = car;
		this.set(0, 0, length, 0, 0, width);
		this.setTexture(textureLocation);
	}
	
	@Override
	public void applyRotation(float x, float y) {
		this.set(0, 0, x * length, y*length, - y*width, x*width);
	}
	
	public void setDimensions(float w , float l) {
		length = l/(2.0f*length_normalizer);
		width = w/(2.0f*width_normalizer);
		this.set(0, 0, length, 0, 0, width);
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
