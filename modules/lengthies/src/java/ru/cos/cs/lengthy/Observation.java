package ru.cos.cs.lengthy;

import ru.cos.cs.lengthy.objects.Point;

public class Observation {

	private float distance;
	private Point point; 
	
	public Observation(float distance, Point observable) {
		super();
		this.distance = distance;
		this.point = observable;
	}

	public float getDistance() {
		return distance;
	}

	public Point getPoint() {
		return point;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

}