package ru.cos.sim.visualizer.traffic.core;

import java.lang.Thread.UncaughtExceptionHandler;

import ru.cos.sim.visualizer.renderer.Renderer;
import ru.cos.sim.visualizer.traffic.gui.ExceptionsDialog;

public class ExceptionsHandler implements UncaughtExceptionHandler{

	private static ExceptionsHandler instance;
	private ExceptionsDialog dialog;
	
	private ExceptionsHandler(){
		
	}
	
	public static ExceptionsHandler getInstance() {
		if (instance == null) {
			instance = new ExceptionsHandler();
		}
		
		return instance;
	}
	
	public void registerThread(Thread t) {
		t.setUncaughtExceptionHandler(this);
	}
	
	private String generateMessage(Thread arg0, Throwable arg1) {
		return "Error in thread: " + arg0.toString() + "\n"+ arg1.toString();
	}

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		final Thread thread = arg0;
		final Throwable exc = arg1;
		if (dialog != null && dialog.isVisible()) {
			String[] messages = new String[1];
			messages[0] = generateMessage(thread, exc);
			dialog.addException(messages, arg1.getStackTrace());
		} else {
			java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	            	dialog = new ExceptionsDialog(SimulationSystemManager.getInstance().getSystemProperties().currentFrame, true);
	                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
	                    public void windowClosing(java.awt.event.WindowEvent e) {
	                        dialog = null;
	                    }
	                });
	                
	                String[] messages = new String[1];
	    			messages[0] = generateMessage(thread, exc);
	    			
	    			
	                if (dialog != null) {
	                	dialog.addException(messages, exc.getStackTrace());
	                	dialog.setVisible(true);
	                }
	            }
	        });
			
		}
	}
	
}
