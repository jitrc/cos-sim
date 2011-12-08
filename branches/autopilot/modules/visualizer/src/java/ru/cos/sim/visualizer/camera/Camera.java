package ru.cos.sim.visualizer.camera;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLUConstants;
import org.lwjgl.util.glu.GLU;

import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.math.Vector3f;
import ru.cos.sim.visualizer.renderer.Renderer;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;
import ru.cos.sim.visualizer.traffic.utils.ViewFieldController;

public class Camera {

	protected static final float[] zoomfactor = {
		20f,1f,3f,7f,9f,14f,25f,50f,100f
	};
	
	protected Vector3f translation;
	protected float scale = -50f;
	protected ArrayList<ICameraListener> listeners;
	protected boolean moved = true;
	
	public Camera(Vector3f translation)
	{
		this.translation = translation;
		listeners = new ArrayList<ICameraListener>();
	}
	
	public Camera()
	{
		this.translation = new Vector3f(0,0,0);
		listeners = new ArrayList<ICameraListener>();
	}
	
	public void moveCamera(Vector3f vector)
	{
		
		this.translation.x += vector.x;
		this.translation.y += vector.y;
		this.translation.z += vector.z;
		Renderer.getRenderer().makeDirty();
		check();
		moved = true;
	}
	
	public void check()
	{
		ViewFieldController c = SimulationSystemManager.getInstance().getViewController();
		translation.x = c.checkX(translation.x);
		translation.y = c.checkY(translation.y);
	}
	
	public void moveAway(float sign, int amount )
	{
		/*if (Math.abs(dz) < 0.00001f) return;
		if (dz < 0 ) this.scale /= dz; else {
			this.scale *= dz;
		}*/
		if (amount < 0) amount = 3;
		if (amount > zoomfactor.length -1) amount = zoomfactor.length -1;
		this.scale += sign*zoomfactor[amount];
		if (scale > -1.0f) scale = -1.0f;
		Renderer.getRenderer().makeDirty();
		moved = true;
	}
	
	public void moveCamera(float x, float y , float z)
	{
		//this.translation.x += 0.5f*x/scale;
		//this.translation.y += 0.5f*y/scale;
		this.translation.x += 0.5f*x/0.02f;
		this.translation.y -= 0.5f*y/0.02f;
		this.translation.z += z;
		Renderer.getRenderer().makeDirty();
		check();
		moved = true;
	}
	
	public void update()
	{
		GL11.glLoadIdentity();
		GL11.glTranslatef(-translation.x, -translation.y,scale);
		if (moved) {
			processListeners();
			moved = false;
		}
	}
	
	private void processListeners(){
		for (ICameraListener l : listeners) {
			l.cameraMoved();
		}
	}

	public Vector3f getTranslation() {
		return translation;
	}

	public void setTranslation(Vector3f translation) {
		this.translation = translation;
		Renderer.getRenderer().makeDirty();
		check();
	}
	
	public float getScale()
	{
		return this.scale;
	}
	
	public void addListener(ICameraListener listener) {
		this.listeners.add(listener);
	}
}
