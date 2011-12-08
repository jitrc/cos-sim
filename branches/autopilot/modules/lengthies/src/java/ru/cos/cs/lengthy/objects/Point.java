package ru.cos.cs.lengthy.objects;

import ru.cos.cs.lengthy.Lengthy;

/**
 * Point object on the lengthy.
 * @author zroslaw
 */
public interface Point{
	
	/**
	 * Types of points.
	 * @author zroslaw
	 */
	public enum PointType{
		Regular,
		MultiPoint
	}
	
	/**
	 * Return current lengthy.
	 * @return lengthy instance on which this observable is placed
	 */
	public Lengthy getLengthy();

	/**
	 * Current position.
	 * @return position of this point on the lengthy.
	 */
	public float getPosition();
	
	/**
	 * Set lengthy.
	 */
	public void setLengthy(Lengthy lengthy);

	/**
	 * Set position.
	 */
	public void setPosition(float position);
	
	/**
	 * Get point type.
	 * @return point type.
	 */
	public PointType getPointType();

}