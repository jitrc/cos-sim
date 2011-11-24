package ru.cos.sim.visualizer.scene.shapes;

import static org.lwjgl.opengl.GL11.glColor4f;

import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.opengl.GL11;

import ru.cos.sim.road.objects.Sign.SignType;
import ru.cos.sim.visualizer.color.Color;
import ru.cos.sim.visualizer.font.TrueTypeFont;
import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.renderer.Renderer;
import ru.cos.sim.visualizer.renderer.Renderer.RenderType;
import ru.cos.sim.visualizer.trace.item.Segment;
import ru.cos.sim.visualizer.trace.item.Sign;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;
import ru.cos.sim.visualizer.traffic.parser.trace.staff.Sign.Type;

public class SignForm extends TexturedRectangleShape{

	public static String noLimitResourceLocation = "/textures/staff/signs/noLimit.png";
	public static String limitBaseResourceLocation = "/textures/staff/signs/sign";
	public static String noLimitResourceExtension = ".png";
	/**
	 * Distance from Sign Center to nearest lane of the segment;
	 */
	public static float distance = 10f;
	public static float width = 3f;
	public static float height = 3f;
	
	protected Sign sign;
	protected Vector2f position;
	
	private int value = -1;
	private static Logger logger = Logger.getLogger(SignForm.class.getName());
	private TrueTypeFont font;
	private boolean fontInited = false;
	
	public SignForm(Sign sign) {
		super();
		this.sign = sign;
		
		this.setTexture(noLimitResourceLocation);
		
		if (sign.getSignType() == Type.SpeedLimitSign) {
			value = (int)(sign.getSpeedLimit()*3.6f);
		}
		
		if (sign.getDirection() == null || sign.getLanePosition() == null) {
			this.render = false;
			
			logger.log(Level.WARNING,"Can't determine sign position");
		}
		
		this.position = sign.getDirection().normalizeLocal().multLocal(distance).addLocal(sign.getLanePosition());
		this.set(this.position.x, this.position.y, width , 0 , 0 , -height);
	}
	
	private void initFont() {
		Font awtFont = new Font(TrueTypeFont.TIMES_FONT,Font.BOLD,36);
		font = new TrueTypeFont(awtFont,true);
		int boundX = (int)(width/1.1f);
		int boundY = (int)(height/1.1f);
		font.setBounds(3,3);
		
		fontInited = true;
	}
	
	@Override
	public void render(RenderType mode) {
		if (!fontInited) initFont();
		if (texture != null) texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
		glColor4f(1, 1, 1, 1);
		//GL11.glColor3f(color.r, color.g, color.b);
		this.texturedVertex(1, 0, x1, y1);
		this.texturedVertex(1, 1, x2, y2);
		this.texturedVertex(0, 1, x3, y3);
		this.texturedVertex(0, 0, x4, y4);
		GL11.glEnd();
		
		if (sign.getSignType() == Type.SpeedLimitSign) {
			Color.black.bind();
			font.drawString(this.position.x, this.position.y,String.valueOf(value));
		}
		
	}
}