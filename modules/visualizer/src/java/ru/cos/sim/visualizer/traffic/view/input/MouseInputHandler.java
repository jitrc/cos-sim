package ru.cos.sim.visualizer.traffic.view.input;

import java.util.Timer;
import java.util.TimerTask;

import javax.print.attribute.standard.SheetCollate;

import ru.cos.sim.visualizer.camera.Camera;
import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;

public class MouseInputHandler {
	
	protected static float moveFactor = 0.005f;
	protected static float speedFactor = 0.0000007f;
	protected Camera camera;
	protected Timer timer;
	protected Vector2f speed;
	
	public MouseInputHandler()
	{
		this.camera = SimulationSystemManager.getInstance().getRenderer().getCamera();
	}
	
	/**
	 * Called when position of the camera must be changed;
	 * @param dx - delta in x coordinate in units;
	 * @param dy - delta in y coordinates in units;
	 */
	public void mapMove(float dx, float dy)
	{
		camera.moveCamera(dx*moveFactor, dy*moveFactor, 0);
	}
	
	/**
	 * Called when height of the camera above scene must be changed.
	 * Usually this method handle mouse wheel action
	 * if dw > 0 - height increase. otherwise height decrease;
	 * @param dw - value of change;
	 */
	public void scaleChanged(int dw, int clicks)
	{
		if (dw == 0) return;
		float sign = Math.signum(-dw);
		camera.moveAway(sign,clicks);
	}
	
	public void startSpeedMove() {
		if (this.timer != null) timer.cancel();
		this.timer = new Timer();
		speed = new Vector2f();
		this.timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				camera.moveCamera(speed.x, speed.y, 0);
			}
		}, 100, 10);
	}
	
	public void stopSpeedMove() {
		if (this.timer == null) return;
		this.timer.cancel();
	}
	
	public void continueingMapMove(int startX, int startY , int x , int y){
		speed.x =  -(x - startX);
		speed.y =  -(y - startY);
		float ls = speed.lengthSquared();
		speed.normalizeLocal();
		speed.multLocal(ls*speedFactor);
	}
}
