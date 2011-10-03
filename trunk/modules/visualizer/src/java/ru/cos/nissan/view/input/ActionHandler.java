package ru.cos.nissan.view.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import ru.cos.nissan.core.SimulationSystemManager;
import ru.cos.renderer.Renderer;


public class ActionHandler implements MouseMotionListener,MouseWheelListener,MouseInputListener {
 
	protected MouseInputHandler handler;
	protected int x;
	protected int y;
	
	protected int pressedX;
	protected int pressedY;
	
	protected boolean isPressed = false;
	protected enum ButtonType {
		Left,
		Right
	}
	protected ButtonType pressedButton;
	
	public ActionHandler(MouseInputHandler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (isPressed) {
			if (pressedButton == ButtonType.Left) {
				handler.continueingMapMove(pressedX, pressedY, e.getX(), e.getY());
			} else {
				handler.mapMove(x - e.getX(), y - e.getY());
			}
			
			x = e.getX();
			y = e.getY();
		}
		//System.out.println("Mouse dragged");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		SimulationSystemManager.getInstance().getPickingHandler().handlePick(e.getX(),e.getY());
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!isPressed) {
			isPressed = true;
			x = e.getX();
			y = e.getY();
			pressedX = x;
			pressedY = y;
			this.pressedButton = determineButton(e.getButton());
			if (pressedButton == ButtonType.Left) handler.startSpeedMove();
		}
	}
	
	protected ButtonType determineButton(int b) {
		switch (b) {
		case MouseEvent.BUTTON1 : return ButtonType.Left;
		case MouseEvent.BUTTON3 : return ButtonType.Right;
		default : return ButtonType.Right;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isPressed) {
			x = 0;
			y = 0;
			isPressed = false;
			handler.stopSpeedMove();
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		handler.scaleChanged(e.getWheelRotation(),e.getClickCount());
	}
	
}
