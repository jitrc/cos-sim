package ru.cos.scene.shapes;

import ru.cos.math.Vector2f;
import ru.cos.trace.item.Beacon;

public class BeaconForm extends TexturedRectangleShape {

	public static String textureLocation = "/textures/staff/beacons/beacon.png";
	public static float distance = 10f;
	public static float width = 5f;
	public static float height = 7f;
	
	protected Beacon beacon;
	protected Vector2f position;
	
	public BeaconForm(Beacon beacon)
	{
		super();
		
		this.beacon = beacon;
		this.setTexture(textureLocation);
		
		this.position = beacon.getDirection().normalizeLocal().multLocal(distance).addLocal(beacon.getLanePosition());
		this.set(this.position.x, this.position.y, width , 0 , 0 , height);
	}
}
