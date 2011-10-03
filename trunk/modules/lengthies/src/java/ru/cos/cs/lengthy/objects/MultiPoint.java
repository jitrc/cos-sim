/**
 * 
 */
package ru.cos.cs.lengthy.objects;

import java.util.Set;

/**
 * MultiPoint object represents multiple points placed in one position on the lengthy.
 * @author zroslaw
 */
public interface MultiPoint extends Point {

	/**
	 * Retrieve all regular points.
	 * @return set of points 
	 */
	public Set<Point> getPoints();
	
	/**
	 * Add regular point into multipoint.
	 * @param pointpoint to add
	 */
	public void addPoint(Point point);
	
	/**
	 * Remove regular point from multipoint.
	 * @param point
	 */
	public void removePoint(Point point);
	
}
