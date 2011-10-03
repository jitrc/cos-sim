package ru.cos.math;

import java.util.ArrayList;

public class PolylineCurve {

//	private ArrayList<PolylineSegment> segments;
	
	public PolylineCurve()
	{
//		this.segments = new ArrayList<PolylineSegment>();
	}
	
//	public void addSegment(Waypoint wp1 , Waypoint wp2)
//	{
//		segments.add(new PolylineSegment(wp1, wp2));
//	}
//	
//	public TracePoint getPosition(float length,TracePoint buf)
//	{
//		if (buf == null) buf = new TracePoint(0, 0);
//		float clength = 0;
//		for (int i =0 ; i< segments.size() ; i++)
//		{
//			PolylineSegment seg = segments.get(i);
//			if (clength + seg.length > length) {
//				float slength = length - clength;
//				Vector2f nv = seg.vector.normalize().multLocal(slength);
//				buf.setX(nv.x + seg.wp1.x);
//				buf.setY(nv.y+seg.wp1.y);
//				return buf;
//			} else {
//				clength += seg.length; 
//			}
//		}
//		return null;
//	}
//	
//	public TracePoint getDirection(float length,TracePoint buf)
//	{
//		if (buf == null) buf = new TracePoint(0, 0);
//		float clength = 0;
//		for (int i =0 ; i< segments.size() ; i++)
//		{
//			PolylineSegment seg = segments.get(i);
//			if (clength + seg.length > length) {
//				float slength = length - clength;
//				buf.setX(seg.wp1.x - seg.wp1.x);
//				buf.setY(seg.wp1.y - seg.wp1.y);
//				return buf;
//			} else {
//				clength += seg.length; 
//			}
//		}
//		return null;
//	}
//	
//	public CarPosition getCarPosition(float length , CarPosition buf)
//	{
//		if (buf == null) buf = new CarPosition();
//		if (buf.getDirection() == null) buf.setDirection(new TracePoint(0, 0));
//		if (buf.getPosition() == null) buf.setPosition(new TracePoint(0, 0));
//		float clength = 0;
//		for (int i =0 ; i< segments.size() ; i++)
//		{
//			PolylineSegment seg = segments.get(i);
//			if (clength + seg.length >= length) {
//				float slength = length - clength;
//				buf.getDirection().setX(seg.wp2.x - seg.wp1.x);
//				buf.getDirection().setY(seg.wp2.y - seg.wp1.y);
//				buf.getDirection().normalize();
//				Vector2f nv = seg.vector.normalize().multLocal(slength);
//				buf.getPosition().setX(nv.x + seg.wp1.x);
//				buf.getPosition().setY(nv.y+seg.wp1.y);
//				return buf;
//			} else {
//				clength += seg.length; 
//			}
//		}
//		return null;
//	}
//	
//	class PolylineSegment{
//		
//		public float length;
//		public Waypoint wp1;
//		public Waypoint wp2;
//		public Vector2f vector;
//		
//		public PolylineSegment(Waypoint wp1, Waypoint wp2) {
//			super();
//			this.wp1 = wp1;
//			this.wp2 = wp2;
//			this.vector = wp1.getVector(wp2);
//			this.length = vector.length();	
//		}
//		
//		
//	}
}
