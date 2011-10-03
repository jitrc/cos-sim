package ru.cos.nissan.utils;

import java.util.ArrayList;

import ru.cos.math.BezierLane;
import ru.cos.math.FastMath;
import ru.cos.math.Vector2f;
import ru.cos.math.Vector3f;
import ru.cos.nissan.parser.geometry.Side;
import ru.cos.nissan.parser.geometry.Waypoint;
import ru.cos.nissan.utils.PolygonNarrower.NarrowMode;
import ru.cos.scene.shapes.TransitionForm;
import ru.cos.scene.shapes.WaitingLaneShape;
import ru.cos.trace.item.base.TrafficLight;

public class RuleCutter {

	private static enum Mode {
		SplittedSegments,
		SimpleSegments,
		SplittedCurve,
		SimpleCurve,
		None
	}
	
	private float breaklength = 0;
	private float currentlength = 0;
	
	private TransitionForm segmentMainShape = null;
	private TransitionForm segmentWaitingShape = null;
	private WaitingLaneShape waitingShape = null;
	
	
	private Mode mode = Mode.None;
	
	public RuleCutter(float breakLength) {
		this.breaklength = breakLength;
	}
	
	public TransitionForm[] addSplittedBezierCurve(BezierLane curve,Vector2f shift)
	{
		if (curve == null) return null;
		
		if (this.mode != Mode.None) {
			generateError();
			return null;
		}
		
		this.mode = Mode.SplittedCurve;
		curve.setShift(shift);
		int splitIndex = curve.generateSplittedLane(breaklength);
		TransitionForm[] result = new TransitionForm[2];
		result[0] = generateBezierCurveShape(curve, 0, splitIndex);
		Vector2f tpoint = curve.getTop()[splitIndex];
		Vector2f vpoint = curve.getBottom()[splitIndex];
		generateWaitingShape(new Vector2f(tpoint.x,tpoint.y), new Vector2f(vpoint.x,vpoint.y), 0);
		result[1] = generateBezierCurveShape(curve, splitIndex, curve.getPoints().length - 1);
		return result;
	}
	
	public ArrayList<TransitionForm> generateTrafficLightsSequence(BezierLane curve ,
			TrafficLight[] lights){
		
		if (lights == null || lights.length == 0 )  {
			curve.generateLane(0, curve.getLength());
			TransitionForm form = generateBezierCurveShape(curve , 0, curve.getPoints().length - 1);
			ArrayList<TransitionForm> r = new ArrayList<TransitionForm>(1);
			r.add(form);
			form.setPosition(new Float(-1));
			return r;
		}
		
		LightsSorter sorter = new LightsSorter(lights);
		
		ArrayList<TransitionForm> result = new ArrayList<TransitionForm>(lights.length+1);
		float left = 0;
		int index = 0;
		if (eps(left, sorter.lights[0].position )) {
			index = 1; 
		}
		
		for (int i = index ; i < sorter.lights.length; i++) {
			curve.generateLane(left, sorter.lights[i].position);
			TransitionForm form = generateBezierCurveShape(curve, 0, curve.getPoints().length-1);
			form.setPosition(new Float(left));
			result.add(form);
			left = sorter.lights[i].position;
		}
		
		curve.generateLane(left, curve.getLength());
		TransitionForm form = generateBezierCurveShape(curve, 0, curve.getPoints().length-1);
		form.setPosition(new Float(left));
		result.add(form);
		
		return result;
 	}
	
	private boolean eps(float a , float b) {
		return (Math.abs(a - b) <= 0.01f) ? true : false;
	}
	
	
	private void generateError()
	{
		throw new Error("You cannot use RuleCutter twice in different Modes without calling reset()");
	}
	
	public TransitionForm addSimpleBezierCurve(BezierLane lane,Vector2f shift)
	{
		if (lane == null) return null;
		
		if (this.mode != Mode.None) {
			generateError();
			return null;
		}
		
		lane.setShift(shift);
		lane.generatePoints();
		return generateBezierCurveShape(lane, 0, lane.getPoints().length -1);
	}
	
	private TransitionForm generateBezierCurveShape(BezierLane curve,
			int beginIndex, int endIndex)
	{
		TransitionForm shape = new TransitionForm();
		int j = 0;
		Vector2f[] tpoints = curve.getTop();
		Vector2f[] vpoints = curve.getBottom();
		for (int i = beginIndex; i <= endIndex ; i++)
		{
			shape.addPoint(tpoints[i]);
			shape.addPoint(vpoints[i]);
			int last = shape.getVertexCount() - 1;
			if (last >= 3) {
				shape.addIndex(last);
				shape.addIndex(last - 2);
				shape.addIndex(last - 3);
				
				shape.addIndex(last);
				shape.addIndex(last - 1);
				shape.addIndex(last - 3);
			}
		}
		
		return shape;
	}
	
	private TransitionForm addSegmentToShape(TransitionForm shape, Vector2f a,
			Vector2f b ,Vector2f c , Vector2f d)
	{
		int last = shape.getVertexCount() - 1;
		PolygonNarrower pn = new PolygonNarrower(a, b, d, c);
		pn.setNarrow(NarrowMode.Procent, 0.6f);
		pn.narrow();
		shape.addPoint(new Vector2f(pn.tb.x, pn.tb.y));
		shape.addPoint(new Vector2f(pn.te.x, pn.te.y));
		shape.addPoint(new Vector2f(pn.be.x, pn.be.y));
		shape.addPoint(new Vector2f(pn.bb.x, pn.bb.y));
		
		shape.addIndex(last + 1);
		shape.addIndex(last + 2);
		shape.addIndex(last + 3);
		
		shape.addIndex(last + 1);
		shape.addIndex(last + 3);
		shape.addIndex(last + 4);
		return shape;
	}
	
	public void reset()
	{
		this.segmentMainShape = null;
		this.segmentWaitingShape = null;
		this.mode = Mode.None;
	}
	
	public void addSegment(Side s1, Side s2){
		
		if (s1 == null || s2 == null) return;
		
		if (this.mode != Mode.SimpleSegments && this.mode != Mode.None) {
			generateError();
			return;
		}
		this.mode = Mode.SimpleSegments;
		
		if (segmentMainShape == null) {
			segmentMainShape = new TransitionForm();
		}
		this.addSegmentToShape(segmentMainShape, 
				new Vector2f(s1.startX, s1.startY),
				new Vector2f(s1.endX, s1.endY),
				new Vector2f(s2.endX, s2.endY),
				new Vector2f(s2.startX, s2.startY));
	}
	
	public void addSegment(Side s1, Side s2, 
			Waypoint begin, Waypoint end ,float length){
		
		if (s1 == null || s2 == null) return;
		
		if (this.mode != Mode.SplittedSegments && this.mode != Mode.None) {
			generateError();
			return;
		}
		this.mode = Mode.SplittedSegments;
		
		if (segmentMainShape == null) {
			segmentMainShape = new TransitionForm();
		}
		if (currentlength + length > breaklength) {
			if (segmentWaitingShape != null) {
				//We are currently on the waiting rule
				this.addSegmentToShape(segmentWaitingShape, 
						new Vector2f(s1.startX, s1.startY),
						new Vector2f(s1.endX, s1.endY),
						new Vector2f(s2.endX, s1.endY),
						new Vector2f(s2.startY, s2.startY));
			} else {
				//This segment contains division point, we must devide it
				Vector2f first = new Vector2f(s1.endX - s1.startX , s1.endY - s1.startY);
				Vector2f second = new Vector2f(s2.endX - s2.startX , s2.endY - s2.startY);
				Vector2f main = begin.getVector(end);
				main.normalizeLocal().multLocal(breaklength - currentlength);;
				float l1 = this.calcHeightPosition(new Vector2f(s1.startX,s1.startY), new Vector2f(s1.endX,s1.endY) ,
						main.x+begin.x, main.y+begin.y);
				float l2 = this.calcHeightPosition(new Vector2f(s2.startX,s2.startY),  new Vector2f(s1.endX,s1.endY) ,
						main.x+begin.x, main.y+begin.y);
				first.normalizeLocal().multLocal(l1);

				this.addSegmentToShape(segmentMainShape,
						new Vector2f(s1.startX,s1.startY),
						new Vector2f(s1.startX + first.x,s1.startY + first.y),
						new Vector2f(s2.startX + second.x,s2.startY + second.y),
						new Vector2f(s2.startX,s2.startY));
				
				segmentWaitingShape = new TransitionForm();
				
				this.addSegmentToShape(segmentWaitingShape,
						new Vector2f(s1.startX + first.x,s1.startY + first.y),
						new Vector2f(s1.endX,s1.endY),
						new Vector2f(s2.endX,s2.endY),
						new Vector2f(s2.startX + second.x,s2.startY + second.y));
				this.generateWaitingShape(new Vector2f(s1.startX + first.x,s1.startY + first.y),
						new Vector2f(s2.startX + second.x,s2.startY + second.y),0.6f);
			}
		} else {
			//We are currently on main rule
			this.addSegmentToShape(segmentMainShape, 
					new Vector2f(s1.startX, s1.startY),
					new Vector2f(s1.endX, s1.endY),
					new Vector2f(s2.endX, s1.endY),
					new Vector2f(s2.startY, s2.startY));
		}
		
		this.currentlength += length;
	}
	
	public TransitionForm[] getPolylineShapes()
	{
		TransitionForm[] result;
		if (this.mode == Mode.SplittedSegments) {
			result = new TransitionForm[2];
			result[0] = this.segmentMainShape;
			result[1] = this.segmentWaitingShape;
			return result;
		} 
		if (this.mode == Mode.SimpleSegments){
			result = new TransitionForm[1];
			result[0] = this.segmentMainShape;
			return result;
		}
		return null;
	}
	
	private void generateWaitingShape(Vector2f first, Vector2f second,float scale)
	{
		PolygonNarrower pn = new PolygonNarrower(PolygonNarrower.NarrowMode.Procent,scale);
		Vector2f[] r = pn.narrowLine(first, second);
		this.waitingShape = new WaitingLaneShape(
				new Vector2f(r[0].x,r[0].y), 
				new Vector2f(r[1].x,r[1].y));
	}
	
	
	private float calcHeightPosition(Vector2f f, Vector2f s , float xp, float yp)
	{
		Vector2f base = s.subtract(f);
		Vector2f side = new Vector2f(xp - f.x , yp - f.y );
		Vector3f square = side.cross(base);
		float height = Math.abs(square.z)/base.length();
		return FastMath.sqrt(side.lengthSquared() - height*height); 
	}

	public WaitingLaneShape getWaitingShape() {
		return waitingShape;
	}
	
	class LightsSorter {
		
		public TrafficLight[] lights;

		public LightsSorter(TrafficLight[] lights) {
			super();
			this.lights = lights;
			sort(0,lights.length-1);
		}
		
		private void sort(int left , int right) {
			if ( right-left <= 0 ) return;
			int middle = partition(left,right);
			sort(left,middle-1);
			sort(middle,right);
		}
		
		private int partition(int left , int right) {
			int middle = right;
			TrafficLight me = lights[middle];
			int i = left - 1;
			
			for (int j = left; j < right; j++) {
				if (lights[j].position <= me.position) {
					i++;
					swap(i, j);
				}
			}
			
			swap(i+1,right);
			return i+1;
		}
	
		private void swap(int a , int b) {
			TrafficLight temp = lights[a];
			lights[a] = lights[b];
			lights[b] = temp;
		}
	}
}
