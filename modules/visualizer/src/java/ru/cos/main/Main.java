package ru.cos.main;

import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import ru.cos.canvas.LWJGLCanvas;
import ru.cos.exception.VisualizerEngineException;
import ru.cos.nissan.gui.GraphicInterface;


public class Main  {

	LWJGLCanvas canvas;
	int width = 640, height = 480;
    private GraphicInterface frame;
	public Main(){
		super();
		init();
	}
	
	public void init(){	        
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        	throw new VisualizerEngineException("Unexpected exception",e);
        }
        
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        frame = new GraphicInterface(width, height);
        // center the frame
        frame.setLocationRelativeTo(null);
        // show frame
        frame.setVisible(true);
	}
	

    public static void main(String[] args) {
    	new Main();
    }
}
