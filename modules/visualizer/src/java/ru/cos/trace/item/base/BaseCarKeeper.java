package ru.cos.trace.item.base;

import ru.cos.agents.car.CarPosition;
import ru.cos.math.Vector2f;
import ru.cos.scene.BasicRenderable;
import ru.cos.scene.impl.ICarKeeper;

public abstract class BaseCarKeeper extends BasicRenderable implements ICarKeeper {
	protected float posx = 0;
	protected float posy = 0;

	protected float rotationAngle = 0;
	
	public BaseCarKeeper(int uid) {
		super(uid);
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

	@Override
	public CarPosition getPosition(float position) {
		return getPosition(position, new CarPosition(new Vector2f(), new Vector2f()));
	}
	
	
}
