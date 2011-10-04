package ru.cos.sim.visualizer.scene.shapes;

import static org.lwjgl.opengl.GL11.glColor4f;

import java.util.logging.Level;

import org.lwjgl.opengl.GL11;

import ru.cos.sim.visualizer.color.Color;
import ru.cos.sim.visualizer.renderer.Renderer.RenderType;
import ru.cos.sim.visualizer.texture.Texture;
import ru.cos.sim.visualizer.texture.TextureLoader;
import ru.cos.sim.visualizer.texture.TexturesManager;

public class TexturedRectangleShape extends RectangleItem {


	protected Texture texture;
	protected boolean textured = false;
	protected boolean textureLoaded = false;
	protected String textureLocation;
	
	protected TexturedRectangleShape()
	{
		super();
		this.color = new Color(0.933334f,0.933334f,0.933334f,1);
	}
	
	protected void texturedVertex(float tx, float ty,
			float x,float y)
	{
		if (textured) GL11.glTexCoord2f(tx, ty);
		GL11.glVertex2f(x, y);
	}
	
	public TexturedRectangleShape(float x, float y, float dirx, float diry,
			float ortx, float orty, float width, float height) {
		super(x, y, dirx, diry, ortx, orty, width, height);

	}

	public TexturedRectangleShape(float x, float y, float dirx, float diry,
			float ortx, float orty) {
		super(x, y, dirx, diry, ortx, orty);

	}

	@Override
	public void render(RenderType mode) {
		if (texture != null) texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
		glColor4f(1, 1, 1, 1);
		//GL11.glColor3f(color.r, color.g, color.b);
		this.texturedVertex(1, 0, x1, y1);
		this.texturedVertex(1, 1, x2, y2);
		this.texturedVertex(0, 1, x3, y3);
		this.texturedVertex(0, 0, x4, y4);
		GL11.glEnd();
	}

	@Override
	public void postRender() {
		super.postRender();
		
		if (textured) GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public void setTexture(String location) {
		this.textureLocation = location;
		textured = (location != null);
	}

	@Override
	public void preRender() {
		if (textured && !textureLoaded) {
			TextureLoader loader  = TexturesManager.getInstance().getLoader();
			try {
				this.texture = loader.getTexture(textureLocation);
			} catch (Exception e)
			{
				logger.log(Level.WARNING, "Can't load texture " + e.toString());
				e.printStackTrace();
			}
			textureLoaded = true;
		}
		if (textured) GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		super.preRender();
	}
	
	

	
}
