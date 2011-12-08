package ru.cos.sim.visualizer.trace.item.base;

import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.renderer.Renderer.RenderType;
import ru.cos.sim.visualizer.scene.impl.IPlaceable;

public class MovableEntity extends BasicEntity{
	
	public MovableEntity(int uid, IPlaceable form, float x, float y) {
		super(uid, form, x, y);
	}

	public MovableEntity(int uid, IPlaceable form, Vector2f pos) {
		super(uid, form, pos);
	}

	public MovableEntity(int uid, IPlaceable form) {
		super(uid, form);
	}

	public MovableEntity(int uid) {
		super(uid);
	}

	public void setDirection(float angle)
	{
		this.rotationAngle = angle;
	}
	
	protected void predraw()
	{
		if (formChanged) {
			refreshFormProperties();
			formChanged = false;
		}
		form.preRender();
	}
	
	protected void draw(RenderType mode)
	{
		form.render(mode);
	}
	
	protected void refreshFormProperties()
	{
		if (this.form == null) return;
		//this.form.setRotation(rotationAngle);
		//this.form.setTranslation(0, 0, 0);
	}
	
	protected void postdraw()
	{
		form.postRender();
	}
	
	public void setRotationAngle(float x ,float y)
	{
		if (x == 0 && y == 0 ) {
			rotationAngle = 0 ;
			return;
		}
		
		if (x  == 0) {
			if (y > 0 ) rotationAngle = 90; else rotationAngle = -90;
			return;
		}
		
		if (x >0) {
			rotationAngle = (float)(Math.atan(y/x)*180.0f/Math.PI);
			return;
		} else {
			x = -x;
			y = -y;
			rotationAngle = (float)(Math.atan(y/x)*180.0f/Math.PI + Math.PI);
			return;
		}
	}
}
