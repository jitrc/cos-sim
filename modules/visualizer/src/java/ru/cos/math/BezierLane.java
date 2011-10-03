package ru.cos.math;

public class BezierLane extends BezierCurve {

	protected float width = 0;
	protected Vector2f[] top;
	protected Vector2f[] bottom;
	
	public BezierLane(Vector2f[] points, float width)
	{
		super(points);
		this.width = width;
		super.generatePoints();
	}
	
	public void generatePoints()
	{
		if (base_points.length != 4) throw new Error("Cannot construct Bezier curve");
		super.generatePoints();
		generateBorders();
	}
	
	public int generateSplittedLane(float splitlength)
	{
		int si =generateSplittedCurve(splitlength);
		generateBorders();
		return si;
	}
	
	public void generateLane(float begin , float end) {
		generateBezierCurve(begin, end);
		generateBorders();
	}
	
	private void generateBorders()
	{
		Vector2f base = new Vector2f(this.base_points[1]);
		Vector2f tangent = base;
		tangent.subtractLocal(this.base_points[0]);
		tangent.normalizeLocal();
		tangent.multLocal(width/2.0f);
		tangent.rotate90();
		top = new Vector2f[result.length];
		bottom = new Vector2f[result.length];
		for (int i = 0 ; i < result.length ; i++)
		{
			if (i !=0) {
				tangent = new Vector2f(result[i]);
				tangent.subtractLocal(result[i-1]);
				tangent.normalizeLocal();
				tangent.multLocal(width/2.0f);
				tangent.rotate90();
				//if (tangent.x*base.x + tangent.y*base.y < 0 ) tangent.negateLocal();
			}
			top[i] = result[i].add(tangent);
			bottom[i] = result[i].add(tangent.negate());
		}
	}

	public Vector2f[] getTop() {
		return top;
	}

	public Vector2f[] getBottom() {
		return bottom;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}
	
	
	
}
