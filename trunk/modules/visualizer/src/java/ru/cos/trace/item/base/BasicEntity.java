package ru.cos.trace.item.base;

import ru.cos.math.Vector2f;
import ru.cos.scene.BasicRenderable;
import ru.cos.scene.impl.IPlaceable;

public class BasicEntity extends BasicRenderable {

	protected float posx = 0;
	protected float posy = 0;

	protected float rotationAngle = 0;

	protected IPlaceable form;
	protected boolean formChanged = false;
	
	public BasicEntity(int uid)
	{
		super(uid);
	}
	
	public BasicEntity(int uid , IPlaceable form)
	{
		this(uid);
		this.form = form;
	}
	
	public BasicEntity(int uid,IPlaceable form,Vector2f pos)
	{
		this(uid,form);
		posx = pos.x;
		posy = pos.y;
	}
	
	public BasicEntity(int uid,IPlaceable form,float x , float y)
	{
		this(uid);
		posx = x;
		posy = y;
	}
	
	public void move(float x, float y)
	{
		posx = x;
		posy = y;
	}
	
	public void move(Vector2f v)
	{
		posx = v.x;
		posy = v.y;
	}
	
	public void shift(Vector2f v)
	{
		posx += v.x;
		posy += v.y;
	}
	
	public void shift(float x, float y)
	{
		posx += x;
		posy += y;
	}
	
	protected void predraw(){}
	
	protected void postdraw(){}
	
	protected void setRotationAngle(float angle)
	{
		this.rotationAngle = angle;
	}
	
	public IPlaceable getForm() {
		return form;
	}

	public void setForm(IPlaceable form) {
		this.form = form;
		this.formChanged = true;
	}
	
}
