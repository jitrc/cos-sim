package ru.cos.sim.visualizer.traffic.view.input;

import static org.lwjgl.opengl.GL11.*; 

import java.nio.IntBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import ru.cos.sim.visualizer.agents.car.CarInformation;
import ru.cos.sim.visualizer.renderer.Renderer;
import ru.cos.sim.visualizer.renderer.Renderer.RenderType;
import ru.cos.sim.visualizer.trace.item.Car;
import ru.cos.sim.visualizer.trace.item.Meter;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;
import ru.cos.sim.visualizer.traffic.parser.trace.location.LaneLocation;
import ru.cos.sim.visualizer.traffic.parser.trace.location.RuleLocation;
import ru.cos.sim.visualizer.traffic.parser.trace.location.Location.Type;
public class PickingHandler {

	private static HashMap<Long , Object> objectsForPicking;
	private static int lastId = 1;
	
	private boolean picked = false; 
	private int mouseX = 0;
	private int mouseY = 0;
	private int selectedVehicleId;
	
	public void handlePick(int mouseX, int mouseY)
	{
		this.picked = true;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		Renderer.getRenderer().makeDirty();
	}
	
	public void processPicking(int canvasHeight)
	{
		if (!picked) return; else picked = false;

		IntBuffer selectBuf = BufferUtils.createIntBuffer(512);
        int hits;
        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        Renderer renderer = Renderer.getRenderer();

        glGetInteger(GL_VIEWPORT, viewport);

        glSelectBuffer(selectBuf);
        glRenderMode(GL_SELECT);

        glInitNames();
        glPushName(0);

        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        
        GLU.gluPickMatrix(mouseX, (canvasHeight - mouseY), 1.0f, 1.0f, viewport);
        GLU.gluPerspective(60.0f, 1, 0.0f, 1000.0f);
        
        objectsForPicking = new HashMap<Long, Object>();
        lastId = 1;
        
        glMatrixMode(GL_MODELVIEW);
///*        glPushName(1);
//        GL11.glTranslatef(0,0,-50f);
//        glRectf(-1000, -1000, 1000, 1000);
//        glPopName();*/
        renderer.render(RenderType.Picking);

        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glFlush();
        
        hits = glRenderMode(GL_RENDER);
        processHits(hits, selectBuf);
        glMatrixMode(GL_MODELVIEW);
	}
	
	public static int addObject(Object o)
	{
		lastId++;
		objectsForPicking.put(new Long(lastId), o);
		return lastId;
	}
	
	private void processHits(int hits, IntBuffer sb)
	{
		Long name ;
		Object object = null;
		//System.out.println("Hits : " + hits);
		for (int j = 0; j < hits; j++) { 
			int names = sb.get();
			sb.get();
			sb.get();
			for (int i = 0; i < names; i++) {
				name = new Long(sb.get());
				object = objectsForPicking.get(name);
				//System.out.println("Name : "+ name + " Object :"+object);
				this.handleResult(object);
			} 
		}
		
	}
	
	protected void handleResult(Object o)
	{
		if (o instanceof Car) {
			Car car = (Car) o;
			this.selectedVehicleId = car.id();
			SimulationSystemManager.getInstance().informationHandler.
			update(car.getInformation());
			
			return;
		}
		
		if (o instanceof Meter){
			SimulationSystemManager.getInstance().getTraceHandler().
			getMetersManager().addGraph((Meter) o);
			return;
		}
	}
	
	public boolean needProcess() {
		return picked;
	}

	public int getSelectedCar() {
		return selectedVehicleId;
	}
}
