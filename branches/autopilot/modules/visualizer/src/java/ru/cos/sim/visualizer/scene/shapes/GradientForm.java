package ru.cos.sim.visualizer.scene.shapes;

import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.trace.item.Beacon;
import ru.cos.sim.visualizer.trace.item.Gradient;

public class GradientForm extends TexturedRectangleShape {
	private static String startTextureLocation = "/textures/resources/staff/gradients/startSign.png";
	private static String endTextureLocation = "/textures/resources/staff/gradients/endSign.png";
	
	public static enum FormType {
		Start,
		Finish
	}
	
	protected FormType formType;
	public static float distance = 10f;
	public static float width = 5f;
	public static float height = 5f;
	
	protected Vector2f position;
	
	public GradientForm(Vector2f position, Vector2f direction,FormType type)
	{
		this.formType = type;
		
		if (this.formType == FormType.Start) {
			this.setTexture(startTextureLocation);
		} else if (this.formType == FormType.Finish) {
			this.setTexture(endTextureLocation);
		}
		
		this.position = direction.normalize().multLocal(distance).addLocal(position);
		this.set(this.position.x, this.position.y, width, 0, 0, height);
	}
}
