package ru.cos.sim.visualizer.renderer;

import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.opengl.GL11;

import ru.cos.sim.visualizer.color.Color;


public class FontManager {

	private Font font;
	private ru.cos.sim.visualizer.font.TrueTypeFont trueTypeFont;

	
	public FontManager() {
		try {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			InputStream is = getClass().getResourceAsStream("/fonts/garamond/AppleGaramond.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, is);
			font.deriveFont(36);
			Font awtFont = new Font("Times New Roman", Font.PLAIN, 36);
			trueTypeFont = new ru.cos.sim.visualizer.font.TrueTypeFont(awtFont, false);
			//;
			//trueTypeFont = new TrueTypeFont(font, true);
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			
		} catch(Throwable e) {
			Error error = new Error("Can not initialize fonts");
			error.setStackTrace(e.getStackTrace());
			throw error;
		}
		
	}
	
	public void drawString(String text,float x,float y, Color color){
		//GL11.glPushMatrix();
		//GL11.glScalef(1, 1, 0.0001f);
		color.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		trueTypeFont.setBounds(10, 10);
		trueTypeFont.drawString(x, y, text);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		//GL11.glPopMatrix();
	}
	
}
