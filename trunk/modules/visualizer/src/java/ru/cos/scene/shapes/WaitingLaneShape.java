package ru.cos.scene.shapes;

import ru.cos.color.Color;
import ru.cos.math.Vector2f;

public class WaitingLaneShape extends RectangleItem {

	protected static float width = 0.1f;
	protected Vector2f b;
	protected Vector2f e;
	
	public WaitingLaneShape(Vector2f b, Vector2f e) {
		super();
		this.color = Color.white;
		this.b = b;
		this.e = e;
		Vector2f dir = e.subtract(b);
		float length = dir.length();
		dir.normalizeLocal().multLocal(length/2.0f);
		float x = b.x + dir.x;
		float y = b.y + dir.y;
		Vector2f ort = dir.clone();
		ort.rotate90();
		ort.normalizeLocal().multLocal(width);
		this.set(x, y, dir.x, dir.y, ort.x, ort.y);
	}
}
