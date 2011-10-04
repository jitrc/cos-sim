package ru.cos.sim.visualizer.scene;

import java.util.ArrayList;

import ru.cos.sim.visualizer.exception.OglException;
import ru.cos.sim.visualizer.renderer.impl.IRenderable;
import ru.cos.sim.visualizer.scene.BasicRenderable.RenderMode;

public class ComplexDisplayList extends BasicRenderable{

	protected ArrayList<IRenderable> objects;
	
//	public ComplexDisplayList()
//	{
//		super(RenderMode.DisplayList);
//		
//		objects = new ArrayList<IRenderable>();
//	}
//	
//	public void construct()
//	{
//		super.begin();
//		for (IRenderable r : objects) {
//			r.render();
//		}
//		super.end();
//	}
	
	
	public void addObject(IRenderable r)
	{
		if (isConstructed()) throw new OglException("LayerDisplayList is already constructed"); 
		objects.add(r);
	}
	
	public ComplexDisplayList(int uid, RenderMode mode) {
		super(uid, mode);
		// TODO Auto-generated constructor stub
	}

	public ComplexDisplayList(int uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}

	public void render()
	{
//		if (!isConstructed()) {
//			construct();
//		}
//		super.render(); 
	}
	
	public void disposeTempResources()
	{
		objects = null;
	}

	@Override
	protected void postdraw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void predraw() {
		// TODO Auto-generated method stub
		
	}
	
	
}
