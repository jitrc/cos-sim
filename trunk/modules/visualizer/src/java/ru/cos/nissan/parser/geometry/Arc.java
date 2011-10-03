package ru.cos.nissan.parser.geometry;

import java.util.ArrayList;

import org.jdom.Element;

import ru.cos.math.Vector2f;
import ru.cos.nissan.parser.Parser;
import ru.cos.nissan.parser.utils.ItemParser;

public class Arc extends GeometryPrimitive {
	public static String name = "Arc";
	
	private static float PI;
	/**
	 * Precision of the arc constructing. Increase it to make circle less aliased.
	 */
	protected static float step;
	
	static {
		PI = (float) Math.PI;
		step = PI/24;
	}
	
	private static enum Fields {
		End,
		x,
		y,
		radius
	}
	
	public float radius = 0;
	public Vector2f endPoint;
	public Vector2f beginPoint;
	public ArrayList<Vector2f> arcPoints;
	public Vector2f center;
	
	public Arc(Element e, Vector2f beginPoint)
	{
		super(Geometrytype.Arc);
		this.radius = ItemParser.getFloat(e
				, Fields.radius.name());
		
		this.endPoint = new Vector2f();
		Element end = e.getChild(Fields.End.name(),Parser.getCurrentNamespace());
		
		this.endPoint.x  = ItemParser.getFloat(end, Fields.x.name());
		this.endPoint.y  = ItemParser.getFloat(end, Fields.y.name());
		this.beginPoint = beginPoint;
		
		this.arcPoints = new ArrayList<Vector2f>();
		this.calculateCenter();
		this.radius = Math.abs(radius);
		this.calculateArc(center.x, center.y, radius,
				beginPoint.x, beginPoint.y, endPoint.x, endPoint.y);
		System.out.println("-----------------------");
	}
	
	private void calculateCenter()
	{
		Vector2f v = new Vector2f(endPoint.x - beginPoint.x, endPoint.y - beginPoint.y);
		v.x /= 2.0f;
		v.y /= 2.0f;
		float base = v.length();
		float height = (float) Math.sqrt(radius * radius - base* base);
		v.set(-v.y,v.x);
		v.normalizeLocal().multLocal(height);
		v.addLocal((endPoint.x + beginPoint.x) / 2.0f, (endPoint.y + beginPoint.y) / 2.0f);
		this.center = v;
	}
	
	public Vector2f getEndPoint()
	{
		return endPoint;
	}
	
	/**
	 * Adds Arc to the segment form.
	 * 
	 * @param cx - x coordinate of the arc center
	 * @param cy - y coordinate of the arc center
	 * @param radius - radius of the arc
	 * @param bx - x coordinate of the arc begin point
	 * @param by - y coordinate of the arc begin point
	 * @param ex - x coordinate of the arc end point
	 * @param ey - y coordinate of the arc end point
	 */
	private void calculateArc(float cx, float cy , float radius , float bx ,float by , float ex , float ey)
	{

		float nbx = bx - cx;
		float nby = by - cy;
		
		float nex = ex - cx;
		float ney = ey - cy;

		float beg = calcAngle(nbx, nby);
		float end = calcAngle(nex, ney);
		float gap = step;
		
		if (end < beg) {
			float full = PI*2.0f;
			if (beg - end < full/2.0) gap = -step; else end+=full;
		} else {
			float full = PI*2;
			if (end-beg > full/2.0) {
				gap = -step;
				beg+=full;
			}
		}
		
		float cur = beg;
		
		//Constructing arc 
		while (true) {
			this.addPoint(cx+radius*Math.cos(cur), cy + radius*Math.sin(cur));
			cur+=gap;
			if ((end < beg) && (cur < end)) break;
			if ((end >= beg) && (cur > end)) break;
		}
		
	}
	
	/**
	 * Adds point to the current form. Automatically makes line from
	 * previous point to this.
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void addPoint(double x , double y)
	{
		this.arcPoints.add(new Vector2f((float)x,(float)y));
		System.out.println(x + "  , " + y);
	}
	
	/**
	 * Calculates angle between the vector with end in this point and begin in (0,0),
	 * and vector (1,0).
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @return - angle , in range [0,2*PI)
	 */
	private float calcAngle(float x , float y)
	{
		if ((x == 0 ) && (y > 0 )) return PI/2.0f;
		if ((x == 0 ) && (y <= 0 )) return 3*PI/2.0f;
		float angle = (float) Math.atan(y/x);
		if ((x < 0) && (y >= 0)) return angle + PI;
		if ((x < 0) && (y < 0)) return angle + PI;
		if ((x >= 0) && (y < 0)) return angle + 2*PI;
		return angle;
	}
	
}
