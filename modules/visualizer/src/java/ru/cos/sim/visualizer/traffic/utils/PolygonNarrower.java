package ru.cos.sim.visualizer.traffic.utils;

import ru.cos.sim.visualizer.math.Vector2f;

public class PolygonNarrower {

	public static enum NarrowMode {
		Procent,
		Fix
	}
	
	protected NarrowMode mode = NarrowMode.Procent;
	protected float scale = 1;
	
	public Vector2f tb ;
	public Vector2f te ;
	public Vector2f bb ;
	public Vector2f be ;
	
	
	public PolygonNarrower(Vector2f tb, Vector2f te, Vector2f bb, Vector2f be) {
		super();
		this.tb = tb;
		this.te = te;
		this.bb = bb;
		this.be = be;
	}
	
	public PolygonNarrower(NarrowMode mode , float value)
	{
		this.mode = mode;
		this.scale = value;
	}
	
	public void narrow(){
		Vector2f lmid = tb.add(bb).multLocal(0.5f);
		Vector2f ls = tb.subtract(lmid);
		float newlength = this.scale(ls.length());
		ls.normalizeLocal().multLocal(newlength);
		tb = ls.add(lmid);
		bb = ls.negateLocal().add(lmid);
		
		lmid = te.add(be).multLocal(0.5f);
		ls = te.subtract(lmid);
		newlength = this.scale(ls.length());
		ls.normalizeLocal().multLocal(newlength);
		te = ls.add(lmid);
		be = ls.negateLocal().add(lmid);
	}
	
	public Vector2f[] narrowLine(Vector2f a , Vector2f b)
	{
		Vector2f[] result =new Vector2f[2];
		Vector2f p = a.add(b).multLocal(0.5f);
		Vector2f v = a.subtract(p);
		float newLength = this.scale(v.length());
		v.normalizeLocal().multLocal(newLength);
		result[0] = v.add(p);
		result[1] = v.negateLocal().add(p);
		return result;
	}
	
	public void setNarrow(NarrowMode mode , float value)
	{
		this.mode = mode;
		this.scale = value;
	}
	
	private float scale(float value)
	{
		return (mode == NarrowMode.Procent) ? value*(1-scale) : value-scale;   
	}
	
}
