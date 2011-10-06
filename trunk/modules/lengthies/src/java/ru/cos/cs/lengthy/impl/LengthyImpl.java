package ru.cos.cs.lengthy.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.cos.cs.lengthy.Lengthy;
import ru.cos.cs.lengthy.Observation;
import ru.cos.cs.lengthy.Router;
import ru.cos.cs.lengthy.exceptions.IllegalOperationExceprion;
import ru.cos.cs.lengthy.impl.objects.ComparedObservable;
import ru.cos.cs.lengthy.impl.objects.MultiPointImpl;
import ru.cos.cs.lengthy.impl.objects.ObservableComparator;
import ru.cos.cs.lengthy.impl.objects.continuous.ContinuousPointImpl;
import ru.cos.cs.lengthy.objects.MultiPoint;
import ru.cos.cs.lengthy.objects.Point;
import ru.cos.cs.lengthy.objects.Point.PointType;
import ru.cos.cs.lengthy.objects.continuous.Continuous;
import ru.cos.cs.lengthy.objects.continuous.ContinuousPoint;
import ru.cos.sim.utils.Pair;

public abstract class LengthyImpl implements Lengthy {

	protected Lengthy prev;
	
	protected Lengthy next;

	/**
	 * All continuouses on this lengthy.
	 * Data is organizing in a map. Keys of such map are continuous instances and values are pairs of relative
	 * positions of front and back points of continuous on THIS lengthy, not their real positions. Such relative
	 * position can be negative (i.e. point out of this lengthy and behind it) or exceed lengthy length 
	 * (point ahead of this lengthy).
	 */
	protected Map<Continuous,Pair<Float, Float>> continuouses = new HashMap<Continuous, Pair<Float,Float>>();

	/**
	 * Sorted list of points on this lengthy.<br>
	 * List must be always sorted in ascent order by key of points positions.
	 */
	protected List<Point> points = new LinkedList<Point>();

	@Override
	public List<Observation> observeForward(float position,	float distance, Router router) {
		LinkedList<Observation> inFront = new LinkedList<Observation>();
		int index;
		index = Collections.binarySearch(points, new ComparedObservable(position), new ObservableComparator());
		index=index<0?-index-1:index;
		
		/* index has been found, lets collect points to the resulted list "inFront" */
		for (int i=index;i<points.size();i++){
			Point point = points.get(i);
			float distanceToObservable = point.getPosition()-position;
			if (distanceToObservable>=distance) return inFront; // we reach boundary of observation and can return result
			if (point.getPointType()==PointType.MultiPoint){
				for (Point regularPoint:((MultiPoint)point).getPoints()){
					inFront.add(new Observation(distanceToObservable, regularPoint));
				}
			}else{
				inFront.add(new Observation(distanceToObservable, point));
			}
		}
		
		float distanceToEnd = getLength() - position; // distance to the end of lengthy
		if (distanceToEnd<distance){
				inFront.addAll(next.observeForward(position-getLength(),distance,router));
		}
		return inFront;	
	}
	
	@Override
	public List<Observation> observeBackward(float position, float distance, Router router) {
		LinkedList<Observation> behind = new LinkedList<Observation>();
		int index;
		index = Collections.binarySearch(points, new ComparedObservable(position), new ObservableComparator());
		index=index<0?-index-2:index;
		
		/* index has been found, lets collect points to the resulted list "behind" */
		for (int i=index;i>=0;i--){
			Point point = points.get(i);
			float distanceToObservable = point.getPosition()-position;
			if (-distanceToObservable>=distance) return behind; // we reach boundary of observation and can return result
			if (point.getPointType()==PointType.MultiPoint){
				for (Point regularPoint:((MultiPoint)point).getPoints()){
					behind.add(new Observation(distanceToObservable, regularPoint));
				}
			}else{
				behind.add(new Observation(distanceToObservable, point));
			}
		}
		float distanceToStart = -position; // distance to the beginning of this lengthy
		if (-distanceToStart<distance){
				behind.addAll(prev.observeBackward(position+prev.getLength(),distance,router));
		}
		return behind;
	}

	@Override
	public void putPoint(Point point, float position) {
		// find index to insert
		int index = Collections.binarySearch(points, new ComparedObservable(position), new ObservableComparator());
		if (index>=0){
			Point point2 = points.get(index);
			if (point2.getPointType()==PointType.Regular){
				MultiPoint mPoint = new MultiPointImpl(point2);
				mPoint.addPoint(point);
			}else{
				MultiPoint mPoint = (MultiPoint)point2;
				mPoint.addPoint(point);
			}
		}
		else{
			index=-index-1;
			points.add(index, point);
			point.setLengthy(this);
			point.setPosition(position);
		}
	}
	
	@Override
	public Set<ContinuousPoint> observeContinuous(float position) {
		Set<ContinuousPoint> result = new HashSet<ContinuousPoint>();
		for (Continuous continuous:continuouses.keySet()){
			Pair<Float,Float> points = continuouses.get(continuous);
			if (points.getFirst()<=position && points.getSecond()>=position){
				result.add(new ContinuousPointImpl(continuous, position-points.getFirst(), this, position));
			}
		}
		return result;
	}
	

	@Override
	public void removePoint(Point point) {
		if (point.getLengthy()!=this)
			throw new IllegalOperationExceprion("Point is not on the lengthy from which it requested to be removed.");
		int index = Collections.binarySearch(points, point, new ObservableComparator());
		if (index<0)
			throw new InternalError("For some reason there is no any point on the position from which point requested to be removed.");
		Point pointToRemove = points.get(index);
		if (pointToRemove.getPointType()==PointType.MultiPoint){
			MultiPoint mPoint = (MultiPoint) pointToRemove;
			mPoint.removePoint(point);
			point.setPosition(Float.NaN);
			point.setLengthy(null);
			if (mPoint.getPoints().size()!=0)
				return;
			else
				point = mPoint;
		}
		points.remove(index);
		point.setPosition(Float.NaN);
		point.setLengthy(null);
	}

	@Override 
	public void putContinuous(Continuous continuous, float position, Router router) {
		
		if (position>=0 && position<=getLength()){
			ContinuousPoint backPoint = continuous.getBackPoint();
			putPoint(backPoint, position);
		}
		
		continuous.getOccupiedLengthies().add(this);
		Pair<Float,Float> ghostPoints = new Pair<Float, Float>(position, position+continuous.length());
		continuouses.put(continuous, ghostPoints);
		if (position+continuous.length()>getLength()){
				next.putContinuous(continuous, position-getLength(), router);
		}else{
			ContinuousPoint frontPoint = continuous.getFrontPoint();
			putPoint(frontPoint, position+continuous.length());
		}
	}

	@Override
	public void putContinuousFromEndPoint(Continuous continuous, float position, Router router) {
		if(position>=0 && position<=getLength()){
			ContinuousPoint frontPoint = continuous.getFrontPoint();
			putPoint(frontPoint, position);
		}
		
		continuous.getOccupiedLengthies().add(0, this);
		Pair<Float,Float> ghostPoints = new Pair<Float, Float>(position-continuous.length(), position);
		continuouses.put(continuous, ghostPoints);
		if (position-continuous.length()<0){
			prev.putContinuousFromEndPoint(continuous, position+prev.getLength(), router);
		}else{
			ContinuousPoint backPoint = continuous.getBackPoint();
			putPoint(backPoint, position-continuous.length());
		}
	}

	@Override
	public void move(Continuous continuous, float distance, Router router) {
		ContinuousPoint frontPoint = continuous.getFrontPoint();
		Lengthy lengthy = frontPoint.getLengthy();
		float position = frontPoint.getPosition();
		removeContinuous(continuous);
		
		lengthy.putPoint(frontPoint, position);
		lengthy.move(frontPoint, distance, router);
		
		lengthy = frontPoint.getLengthy();
		position = frontPoint.getPosition();
		
		lengthy.removePoint(frontPoint);
		
		lengthy.putContinuousFromEndPoint(continuous, position, router);
	}

	@Override
	public void move(Point point, float distance, Router router) {
		if (point.getLengthy()!=this) {
			if (point.getLengthy()==null)
				throw new IllegalOperationExceprion("Point is not placed on any lengthy yet.");
			point.getLengthy().move(point, distance, router);
			return;
		}
		
		float newPosition = point.getPosition()+distance;
		if (newPosition>this.getLength()){
			this.removePoint(point);
			next.putPoint(point, 0);
			next.move(point, newPosition-this.getLength(), router);
		}else if (newPosition<0){
			this.removePoint(point);
			prev.putPoint(point, prev.getLength());
			prev.move(point, newPosition, router);
		}else{
			this.removePoint(point);
			this.putPoint(point,newPosition);
		}
	}

	@Override
	public void removeContinuous(Continuous continuous) {
		ContinuousPoint backPoint = continuous.getBackPoint();
		ContinuousPoint frontPoint = continuous.getFrontPoint();
		backPoint.getLengthy().removePoint(backPoint);
		frontPoint.getLengthy().removePoint(frontPoint);
		for(Lengthy lengthy:continuous.getOccupiedLengthies()){
			RegularLengthyImpl impl = (RegularLengthyImpl)lengthy;
			impl.continuouses.remove(continuous);
		}
		continuous.getOccupiedLengthies().clear();
	}

	public List<Point> getPoints(){
		return points;
	}
}
