package ru.cos.cs.lengthy.objects.continuous;

import ru.cos.cs.lengthy.objects.Point;

public interface ContinuousPoint extends Point {
	
	public enum Type{
		FrontPoint,
		BackPoint,
		IntermediatePoint
	}

	public float getPositionOnContinuous();
	
	public void setPositionOnContinuous(float positionOnContinuous);
	
	public Continuous getContinuous();
	
	public void setContinuous(Continuous continuous);
	
	public Type getContinuousPointType();
	
	
}