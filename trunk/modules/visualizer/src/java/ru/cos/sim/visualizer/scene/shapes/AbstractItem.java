package ru.cos.sim.visualizer.scene.shapes;

import java.util.logging.Logger;

import org.lwjgl.opengl.GL11;

import ru.cos.sim.visualizer.color.Color;
import ru.cos.sim.visualizer.renderer.impl.IRenderable.FrustrumState;
import ru.cos.sim.visualizer.scene.impl.IPlaceable;

public abstract class AbstractItem implements  IPlaceable {
	protected static Logger logger = Logger.getLogger(AbstractItem.class.getName());
	
	protected float angle = 0;
	protected float shiftx = 0;
	protected float shifty = 0;
	protected float shiftz = 0;
	protected Color color = Color.white;
	protected boolean render = true;
	protected FrustrumState frustrumState = FrustrumState.OutOfView;
	
	@Override
	public void postRender() {
		GL11.glPopMatrix();
	}
	
	public void setTranslation(float x , float y, float z){
		shiftx = x;
		shifty = y;
		shiftz = z;
	}

	@Override
	public void preRender() {
		GL11.glPushMatrix();
		if (shiftx != 0 || shifty != 0 || shiftz != 0) GL11.glTranslatef(shiftx, shifty, shiftz);
		rotate();
	}
	
	protected void rotate()
	{
		if (angle == 0) return;
		GL11.glRotatef(angle, 0, 0, 1);
	}

	@Override
	public void applyRotation(float x, float y) {
		if (x == 0 && y == 0 ) {
			angle = 0 ;
			return;
		}
		
		if (x  == 0) {
			if (y > 0 ) angle = 90; else angle = -90;
			return;
		}
		
		if (x >0) {
			angle = (float)(Math.atan(y/x)*180.0f/Math.PI);
			return;
		} else {
			x = -x;
			y = -y;
			angle = (float)(Math.atan(y/x)*180.0/Math.PI + Math.PI);
			return;
		}
	}
	
	@Override
	public void flushModifications() {
		this.angle = 0;
		this.setTranslation(0, 0, 0);
	}

	@Override
	public void setRotation(float angle) {
		this.angle = angle;
	}

	@Override
	public void setColor(Color c) {
		this.color = c;
	}

	public FrustrumState getLastFrustrumState() {
		return this.frustrumState;
	}

	@Override
	public void checkFrustum() {
		this.frustrumState = FrustrumState.InView;
	}
	
	
	
}
