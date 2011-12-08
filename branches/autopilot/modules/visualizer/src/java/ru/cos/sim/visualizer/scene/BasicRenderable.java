package ru.cos.sim.visualizer.scene;

import org.lwjgl.opengl.GL11;

import ru.cos.sim.visualizer.exception.OglException;
import ru.cos.sim.visualizer.renderer.Renderer.RenderType;
import ru.cos.sim.visualizer.renderer.impl.IRenderable;
import ru.cos.sim.visualizer.trace.item.base.Entity;

public abstract class BasicRenderable extends Entity implements IRenderable {

	protected static enum RenderMode {
		Simple,
		DisplayList
	}
	
	public static int lastListNumber = -1;
	protected boolean constructed = false;
	protected boolean listOpened = false;
	protected int listNumber = -1;
	protected FrustrumState frustrumState = FrustrumState.OutOfView;
	
	protected RenderMode renderMode = RenderMode.Simple;
	
	public BasicRenderable(int uid) {
		super(uid);
	}
	
	public BasicRenderable(int uid,RenderMode mode) {
		super(uid);
		this.renderMode = RenderMode.Simple;
	}

	private void begin() {
		if (this.renderMode == RenderMode.Simple) return;
		if (constructed) throw new OglException("Display List already constructed");
		listNumber = ++lastListNumber;
		GL11.glNewList(lastListNumber, GL11.GL_COMPILE);
		listOpened = true;
		constructed = true;
	}

	private void end() {
		if (this.renderMode == RenderMode.Simple) return;
		if (!listOpened) throw new OglException("Display List already constructed");
		GL11.glEndList();
	}

	protected void load() {
		
	}
	
	protected void draw(RenderType mode)
	{
		
	}
	
	protected abstract void predraw();
	protected abstract void postdraw();

	public final void render(RenderType mode) {
		if (mode == RenderType.Picking) {
			//GL11.glPushName(PickingHandler.addObject(this));
		}
		if (this.renderMode == RenderMode.Simple) {
			predraw();
			draw(mode);
			postdraw();
			if (mode == RenderType.Picking) {
				//GL11.glPopName();
			}
			return;
		}
		if (!constructed) {
			this.begin();
			draw(mode);
			this.end();
		}
		predraw();
		GL11.glCallList(listNumber);
		postdraw();
		if (mode == RenderType.Picking) {
			//GL11.glPopName();
		}
	}

	public boolean isConstructed() {
		return constructed;
	}



	public RenderMode getRenderMode() {
		return renderMode;
	}



	public void setRenderMode(RenderMode renderMode) {
		this.renderMode = renderMode;
	}
	
	@Override
	public FrustrumState getLastFrustrumState() {
		return this.frustrumState;
	}
}
