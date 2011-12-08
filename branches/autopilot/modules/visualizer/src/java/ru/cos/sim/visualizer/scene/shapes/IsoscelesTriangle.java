package ru.cos.sim.visualizer.scene.shapes;

public class IsoscelesTriangle extends TriangleItem {

	protected IsoscelesTriangle()
	{
		super();
	}
	/**
	 * Generates vertical isosceles triangle.
	 * @param cx - middle point of triangle's base side. X coordinate.
	 * @param cy - middle point of triangle's base side. Y coordinate.
	 * @param width - base side length;
	 * @param height - length of the height from top point to base side.
	 */
	public IsoscelesTriangle(float cx, float cy, float width, float height) {
		super(cx - width/2.0f,cy,cx + width/2.0f,cy,cx,cy+height);
	}

	public IsoscelesTriangle(float x1, float y1, float x2, float y2, float x3,
			float y3) {
		super(x1, y1, x2, y2, x3, y3);
	}
	
	protected void set(float cx, float cy, float width, float height)
	{
		super.set(cx - width/2.0f,cy,cx + width/2.0f,cy,cx,cy+height);
	}
}
