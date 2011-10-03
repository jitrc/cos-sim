package ru.cos.scene.shapes;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.opengl.GL11;

import ru.cos.nissan.parser.trace.staff.Background;
import ru.cos.renderer.Renderer.RenderType;
import ru.cos.renderer.impl.IRenderable;
import ru.cos.renderer.impl.IRenderable.FrustrumState;
import ru.cos.texture.Texture;
import ru.cos.texture.TextureLoader;
import ru.cos.texture.TexturesManager;

public class BackgroundForm implements IRenderable {

	private static Logger logger = Logger.getLogger(BackgroundForm.class.getName());
	private static String txtName = "background";
	
	protected float x1;
	protected float y1;
	protected float x2;
	protected float y2;
	protected float x3;
	protected float y3;
	protected float x4;
	protected float y4;
	
	protected boolean loaded = false;
	protected Texture texture;
	protected TextureLoader loader;
	protected InputStream imageData;
	protected FrustrumState frustrumState = FrustrumState.InView;
	
	public BackgroundForm(Background b)
	{
		x1 = b.start.x;
		y1 = b.start.y;
		
		x2 = b.start.x;
		y2 = b.end.y;
			
		x3 = b.end.x;
		y3 = b.end.y;
		
		x4 = b.end.x;
		y4 = b.start.y;
		
		this.loaded = false;
		this.loader = TexturesManager.getInstance().getLoader();
		this.imageData = b.stream;
	}
	
	@Override
	public void render(RenderType mode) {
		if (!loaded) {
			try {
				this.texture = loader.getTextureFromStream(txtName, imageData,!loaded);
			} catch (Exception e)
			{
				logger.log(Level.WARNING, "Can't load texture " + e.toString());
				e.printStackTrace();
			}
			loaded = true;
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		this.texture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glTexCoord2f(0, 1-this.texture.getHeight());
		GL11.glVertex2f( x1, y1);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f( x2, y2);
		GL11.glTexCoord2f(this.texture.getWidth(), 1);
		GL11.glVertex2f( x3, y3);
		GL11.glTexCoord2f(this.texture.getWidth(), 1-this.texture.getHeight());
		GL11.glVertex2f( x4, y4);

//		GL11.glTexCoord2f(0, 0);
//		GL11.glVertex2f( x1, y1);
//		GL11.glTexCoord2f(0, 1);
//		GL11.glVertex2f( x2, y2);
//		GL11.glTexCoord2f(1, 1);
//		GL11.glVertex2f( x3, y3);
//		GL11.glTexCoord2f(1, 0);
//		GL11.glVertex2f( x4, y4);
		
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public FrustrumState getLastFrustrumState() {
		return this.frustrumState;
	}

}
