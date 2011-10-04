package ru.cos.sim.visualizer.scene.shapes;

import java.util.logging.Level;
import java.util.logging.Logger;

import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.trace.item.Segment;
import ru.cos.sim.visualizer.trace.item.Sign;
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
	
	private static Logger logger = Logger.getLogger(SignForm.class.getName());
	
	public SignForm(Sign sign) {
		super();
		this.sign = sign;
		
		if (sign.getSignType() == Type.NoSpeedLimitSign) {
			this.setTexture(noLimitResourceLocation);
		} else {
			int value = (int)sign.getSpeedLimit();
			String location = limitBaseResourceLocation + value + noLimitResourceExtension; 
			this.setTexture(location);
		}
		
		
		if (sign.getDirection() == null || sign.getLanePosition() == null) {
			this.render = false;
			
			logger.log(Level.WARNING,"Can't determine sign position");
		}
		
		this.position = sign.getDirection().normalizeLocal().multLocal(distance).addLocal(sign.getLanePosition());
		this.set(this.position.x, this.position.y, width , 0 , 0 , -height);
	}	
}