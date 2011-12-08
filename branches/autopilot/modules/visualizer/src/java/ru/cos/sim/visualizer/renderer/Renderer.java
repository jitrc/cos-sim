package ru.cos.sim.visualizer.renderer;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor4f;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

import ru.cos.sim.visualizer.agents.car.CarsHandler;
import ru.cos.sim.visualizer.camera.Camera;
import ru.cos.sim.visualizer.camera.ICameraListener;
import ru.cos.sim.visualizer.frame.FrameDataHandler;
import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.math.Vector3f;
import ru.cos.sim.visualizer.renderer.impl.IRenderable;
import ru.cos.sim.visualizer.scene.impl.INode;
import ru.cos.sim.visualizer.scene.shapes.BackgroundForm;
import ru.cos.sim.visualizer.trace.TraceHandler;
import ru.cos.sim.visualizer.trace.item.BoundaryNode;
import ru.cos.sim.visualizer.trace.item.CrossRoad;
import ru.cos.sim.visualizer.trace.item.LaneClosure;
import ru.cos.sim.visualizer.trace.item.Segment;
import ru.cos.sim.visualizer.trace.item.TransitionRule;
import ru.cos.sim.visualizer.trace.item.base.TrafficLight.Color;
import ru.cos.sim.visualizer.traffic.core.ConditionManager;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;

public class Renderer implements IRenderable{
	public static enum RenderType {
		None,
		Picking
	}
	
	public static final long MAX_UPDATE_TIME = 10000;
	protected Camera camera;
	protected static Renderer renderer;
	public ArrayList<Segment> segments;
	public ArrayList<TransitionRule> rules;
	public ArrayList<INode> css;
	public ArrayList<BackgroundForm> backgrounds;
	public FrustrumState frustrumState = FrustrumState.InView; 
	public Point viewport;
	private AWTGLCanvas canvas;
	private long startTime = 0;
	private Frustum frustum;
	protected float lineWidth = 1f;
	protected ArrayList<Segment> segmentsInCamera;
	protected CameraListener listener;
	protected boolean firstAfterInit = true;
	
	private Renderer() {
		super();
		camera = new Camera();;
		this.startTime = System.currentTimeMillis();
		this.segmentsInCamera = new ArrayList<Segment>();
		frustum = new Frustum();
		this.listener  = new CameraListener();
		camera.addListener(this.listener);
	}

	public static Renderer getRenderer()
	{
		if (renderer == null) renderer = new Renderer();
		return renderer;
	}
	
	public void init()
	{
		camera.check();
		Vector2f af = SimulationSystemManager.getInstance().getViewController().getFocusPoint();
		camera.setTranslation(new Vector3f(af.x,af.y,0));
		this.makeDirty();
		this.firstAfterInit = true;
	}
	
	public static Renderer createRenderer()
	{
		if (renderer != null) {
			AWTGLCanvas  c = renderer.canvas;
			renderer = new Renderer();
			renderer.canvas = c;
		} else {
			renderer = new Renderer();
		}
		
		return renderer;
	}

	@Override
	public void render(RenderType mode) {
		long time = System.currentTimeMillis();
		glClear(GL_COLOR_BUFFER_BIT);
		GL11.glLineWidth(camera.getScale()*lineWidth);
		if (segments == null) return;
		if (frustum != null ) frustum.extractFrustum();
		TraceHandler handler = SimulationSystemManager.getInstance().getTraceHandler();
		glColor4f(1, 1, 1, 1);
		for (BackgroundForm form : backgrounds) {
			form.render(mode);
		}
		if (this.firstAfterInit) this.checkFrustum();
		this.firstAfterInit = false;
			SimulationSystemManager.getInstance().update();
		
		for (INode cs : css){
			if (cs instanceof CrossRoad)cs.render(mode);
		}
		
		for (TransitionRule tr : rules)
		{
			tr.drawRule(mode);
		}
		
		for (TransitionRule tr : rules)
		{
			tr.draw(mode);
		}
		
//		for (TransitionRule tr : rules)
//		{
//			if (tr.getMainLight() != null && 
//					tr.getMainLight().getLightColor() == Color.Green)  tr.drawMainLight(mode);
//			if (tr.getWaitLight() != null && 
//					tr.getWaitLight().getLightColor() == Color.Green)  tr.drawWaitLight(mode);
//			tr.drawWaitPosition(mode);
//		}
		
//		for (Segment s : segments)
//			{
//				s.checkFrustum();
//				s.render(mode);
//			}
		for (Segment s : segmentsInCamera)
		{
			//s.checkFrustum();
			s.render(mode);
		}
		
//		if (segmentsInCamera.size() == 0) {
//			if ((segments != null) && (segments.size() >= 5))
//				for (int i = 0; i < 5; i++ ){
//					segments.get(i).render(mode);
//				}
//				
//		}
		
		
		for (INode cs : css){
			 if (cs instanceof BoundaryNode )cs.render(mode);
		}
		glColor4f(1, 1, 1, 1);
		
		for (LaneClosure lc : SimulationSystemManager.getInstance().getTraceHandler().laneClosures){
			lc.draw(mode);
		}
		glColor4f(1, 1, 1, 1);
		
		SimulationSystemManager.getInstance().getCarsHandler().render(mode);
		
		
		try {
            this.canvas.swapBuffers();
        } catch (LWJGLException e) {
        }
		
        if (time - this.startTime > MAX_UPDATE_TIME ) this.canvas.repaint();
		/*GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor3f(1, 0, 0);resources/roadNetwork/basicDestNode.png
		GL11.glVertex3f(0, 0, 0);
		GL11.glVertex3f(0, 1, 0);
		GL11.glVertex3f(1, 1,0);
		
		GL11.glVertex3f(1, 1, 0);
		GL11.glVertex3f(1, 0, 0);
		GL11.glVertex3f(0, 0,0);
		GL11.glEnd();*/
	}

	public Camera getCamera() {
		return camera;
	}
	
	public float getLineWidth(){
		return lineWidth*camera.getScale();
	}

	@Override
	public FrustrumState getLastFrustrumState() {
		return this.frustrumState;
	}
	
	public int getViewportX()
	{
		return this.viewport.getX();
	}
	
	public int getViewportY()
	{
		return this.viewport.getY();
	}
	
	public void setVewport(int x , int y)
	{
		this.viewport = new Point(x,y);
	}
	
	public void makeDirty()
	{
		this.startTime = System.currentTimeMillis();
		this.canvas.repaint();
	}

	public AWTGLCanvas getCanvas() {
		return canvas;
	}

	public void setCanvas(AWTGLCanvas canvas) {
		this.canvas = canvas;
	}

	public Frustum getFrustum() {
		return frustum;
	}
	
	class CameraListener implements ICameraListener {

		@Override
		public void cameraMoved() {
			if (segments == null) return;
			frustum.extractFrustum();
			segmentsInCamera = new ArrayList<Segment>();
			for (Segment s : segments)
			{
				s.checkFrustum();
				if (s.getLastFrustrumState() != FrustrumState.OutOfView) {
					segmentsInCamera.add(s);
				}
				FrameDataHandler.getInstance().setDataInvalid();
			}
		}
		
	}
	
	
	public void checkFrustum(){
		if (frustum != null) {
			frustum.extractFrustum();
			this.listener.cameraMoved();
		}
	}
	
}
