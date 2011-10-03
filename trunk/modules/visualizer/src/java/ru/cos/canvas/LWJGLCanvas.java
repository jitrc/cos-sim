package ru.cos.canvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ARBMultisample;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

import ru.cos.camera.Camera;
import ru.cos.display.DisplaySystem;
import ru.cos.nissan.core.SimulationSystemManager;
import ru.cos.nissan.view.input.ActionHandler;
import ru.cos.nissan.view.input.MouseInputHandler;
import ru.cos.renderer.Renderer;
import ru.cos.renderer.Renderer.RenderType;


public class LWJGLCanvas extends AWTGLCanvas{
	 private static final Logger logger = Logger.getLogger(LWJGLCanvas.class
	            .getName());

	    private static final long serialVersionUID = 1L;
	    private static final int MAX_UPDTAE_COUNT = 100; 
	    
	    protected Renderer renderer;
	    protected Camera camera;
	    protected ActionHandler listener;
	    protected MouseInputHandler inputHandler;
	    private boolean inited = false;
	    private int newWidth;
	    private int newHeight;
	    private boolean resized = false;
	    private int updateCount = 0;

	    public LWJGLCanvas() throws LWJGLException {
	        super();
	    }
	    
	    public LWJGLCanvas(PixelFormat f) throws LWJGLException {
	        super(f);
	    }

	    private static PixelFormat generatePixelFormat() {
	        return (DisplaySystem.getDisplaySystem())
	                .getFormat();
	    }

	    @Override
	    public void initGL() {
	    	this.renderer = SimulationSystemManager.getInstance().getRenderer();
	    	camera = this.renderer.getCamera();
	    	inputHandler = new  MouseInputHandler();
	    	listener = new ActionHandler(inputHandler);
	    	this.addMouseListener(listener);
	    	this.addMouseWheelListener(listener);
	    	this.addMouseMotionListener(listener);

	       // GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, mat_one);
	       // GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, mat_one);
	       // GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, mat_diffuse_yellow);
	       // GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 50.0f);
	       // GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, light_position);
	       // GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, lm_ambient);

	       // GL11.glEnable(GL11.GL_LIGHTING);
	       // GL11.glEnable(GL11.GL_LIGHT0);
	       // GL11.glEnable(GL11.GL_DEPTH_TEST);
	        //GL11.glShadeModel(GL11.GL_SMOOTH);
	       // GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

	       
	        //GL11.glTranslatef(0, 0, 10);
	    	//new Mouse();
	    	
	    	//handler = new ActionHandler(camera);
	        inited = true;
	        
			 // Set up the accumulation buffer
	        glClearColor(0.933334f,0.933334f,0.933334f,1);
	        //GL11.glClear(GL11.GL_ACCUM_BUFFER_BIT);

	        // View
	        glViewport(0, 0, 800, 800);
	        //glOrtho(0, 800f, 800f, 0, 0, 1000);
	        glEnable(GL_LINE_SMOOTH);
	        glEnable(GL_BLEND);
	        glDisable(GL_COLOR_MATERIAL);
	        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	        //glEnable(GL_CULL_FACE);
	        //glEnable(GL_TEXTURE_2D);
	       // GL11.glScalef(0.1f, 0.1f, 1);
	    	//GL11.glDepthFunc(GL11.GL_LEQUAL);
	        glShadeModel(GL_SMOOTH);
	        glMatrixMode(GL_PROJECTION);
	        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	        GLU.gluPerspective(60.0f, 1, 0.0f, 1000.0f);
	        glMatrixMode(GL_MODELVIEW);
	        glLoadIdentity();
	        //glHint(GL_POLYGON_SMOOTH_HINT,GL_NICEST);
	        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
	    }


	    
	@Override
	public void paintGL() {
		
		//renderer.getCamera().update();
		if (SimulationSystemManager.getInstance().getPickingHandler().needProcess()) {
			SimulationSystemManager.getInstance().getPickingHandler().processPicking(this.getHeight());
			return;
		}
		glClear(GL_COLOR_BUFFER_BIT);
        //processPicking(this.getHeight());
		if (renderer == null) return;
		{
			if (resized) {
				resized = false;
				int res = Math.max(newWidth, newHeight);
				glViewport(0, 0, res,res);
				//glViewport(0, 0, newWidth,newHeight);
			}
			camera.update();
			renderer.render(RenderType.None);
		}
		
	}
	
	public void processPicking(int canvasHeight)
	{
		int mouseX = 10;
		int mouseY = 10;
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
        
        GLU.gluPickMatrix(mouseX, (viewport.get(3) - mouseY), 5.0f, 5.0f, viewport);
        GLU.gluOrtho2D(0.0f, 1000.0f, 0.0f, 1000.0f);
        
       // glMatrixMode(GL_MODELVIEW);
        glPushName(1);
        glRectf(-1000, -1000, 1000, 1000);
        glPopName();
        //renderer.render(RenderType.Picking);

        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glFlush();
        
        hits = glRenderMode(GL_RENDER);
        System.out.println(" Detected hits" + hits);
        System.out.println(" Mouse " + mouseX + " " + mouseY);
        glMatrixMode(GL_MODELVIEW);
	}
	
	    @Override
	    public void removeNotify() {
	      /*  if ( shouldAutoKillContext ) {
	            glInitialized = false;
	            super.removeNotify();
	}*/
	    }
	    
	public void render()
	{
		if (renderer == null) return;
		//if ( updateCount < MAX_UPDTAE_COUNT || renderer.isDirty()) 
		{
			if (resized) {
				resized = false;
				glViewport(0, 0, newWidth, newHeight);
			}
			glClear(GL_COLOR_BUFFER_BIT);
			camera.update();
			renderer.render(RenderType.None);
			//updateCount = (renderer.isDirty()) ? 0 : updateCount +1;
		}
		
		//repaint();
	}

	public void resize(int width , int height)
	{
		if (inited) {
			resized = true;
			newWidth = width;
			newHeight = height;
		}
	}
}
