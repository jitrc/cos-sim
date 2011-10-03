package ru.cos.nissan.utils;

import ru.cos.math.Vector2f;
import ru.cos.nissan.parser.geometry.Side;

public class ViewFieldController {
	
	protected  float maxx =  - Float.MAX_VALUE;
	protected  float minx = Float.MAX_VALUE;
	protected  float maxy = - Float.MAX_VALUE;
	protected  float miny = Float.MAX_VALUE;
	
	protected Vector2f autoFocusPoint;
	protected boolean autoFocusPointset;
	
	public ViewFieldController()
	{
		dispose();
	}
	
	public  void addPoint(float x , float y)
	{
		maxx = (maxx < x) ? x : maxx;
		minx = (minx > x) ? x : minx;
		maxy = (maxy < y) ? y : maxy;
		miny = (miny > y) ? y : miny;
		if (!autoFocusPointset) {
			autoFocusPoint = new Vector2f();
			autoFocusPoint.x = x;
			autoFocusPoint.y = y;
			autoFocusPointset = true;
		}
	}
	
	public void addSide(Side side)
	{
		addPoint(side.startX,side.startY);
		addPoint(side.endX, side.endY);
	}
	
	public  void addPoint(double x , double y)
	{
		addPoint((float)x, (float)y);
	}

	public  float getMaxx() {
		return maxx;
	}
	
	public  void dispose()
	{
		maxx = - Float.MAX_VALUE;
		minx = Float.MAX_VALUE;
		maxy = - Float.MAX_VALUE;
		miny = Float.MAX_VALUE;
		autoFocusPointset = false;
	}
	
	public  float checkX(float x)
	{
		return (maxx < x) ? maxx : (( minx > x) ? minx : x);
	}
	
	public  float checkY(float y)
	{
		return (maxy < y) ? maxy : (( miny > y) ? miny : y);
	}

	public  void setMaxx(float maxx) {
		this.maxx = maxx;
	}

	public  float getMinx() {
		return minx;
	}

	public  void setMinx(float minx) {
		this.minx = minx;
	}

	public  float getMaxy() {
		return maxy;
	}

	public  void setMaxy(float maxy) {
		this.maxy = maxy;
	}

	public  float getMiny() {
		return miny;
	}

	public  void setMiny(float miny) {
		this.miny = miny;
	}
	
	public Vector2f getFocusPoint(){
		return autoFocusPoint;
	}
	
	
}
