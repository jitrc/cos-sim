package ru.cos.sim.visualizer.scene.shapes;

import org.lwjgl.opengl.GL11;

import ru.cos.sim.visualizer.renderer.Renderer.RenderType;
import ru.cos.sim.visualizer.renderer.impl.IRenderable;
import ru.cos.sim.visualizer.scene.impl.IPlaceable;

public class RectangleItem  extends AbstractItem implements IRenderable,IPlaceable {
	
	protected float x1;
	protected float y1;
	protected float x2;
	protected float y2;
	protected float x3;
	protected float y3;
	protected float x4;
	protected float y4;
	
	protected RectangleItem()
	{
		
	}
	
	protected void set(float x, float y, float dirx, float diry, float ortx, float orty)
	{
		x1 = x + dirx + ortx;
		y1 = y + diry + orty;
		
		x2 = x + dirx - ortx;
		y2 = y + diry - orty;
		
		x3 = x - dirx - ortx;
		y3 = y - diry - orty;
		
		x4 = x - dirx + ortx;
		y4 = y - diry + orty;
	}
	
	public RectangleItem(float x, float y, float dirx, float diry, float ortx, float orty)
	{
		this.set(x, y, dirx, diry, ortx, orty);
	}
	
	public RectangleItem(float x, float y, float dirx, float diry, float ortx, float orty, float width, float height)
	{
		double dlength = Math.sqrt(dirx*dirx + diry*diry);
		double olength = Math.sqrt(ortx*ortx + orty*orty);
		dirx = (float) ((dirx*width)/ dlength);
		diry = (float) ((diry*width)/ dlength);
		
		ortx = (float) ((ortx*height)/ olength);
		orty = (float) ((orty*height)/ olength);
		
		x1 = x + dirx + ortx;
		y1 = y + diry + orty;
		
		x2 = x + dirx - ortx;
		y2 = y + diry - orty;
		
		x3 = x - dirx - ortx;
		y3 = y - diry - orty;
		
		x4 = x - dirx + ortx;
		y4 = y - diry + orty;
	}
	
	@Override
	public void render(RenderType mode) {
		GL11.glPushMatrix();
		rotate();
			GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glColor3f(color.r, color.g, color.b);
			GL11.glVertex2f(x1, y1);
			GL11.glVertex2f( x2, y2);
			GL11.glVertex2f( x3, y3);
			
			GL11.glVertex2f( x3, y3);
			GL11.glVertex2f( x4, y4);
			GL11.glVertex2f( x1, y1);
			GL11.glEnd();
			
		GL11.glPopMatrix();
	}	
}
