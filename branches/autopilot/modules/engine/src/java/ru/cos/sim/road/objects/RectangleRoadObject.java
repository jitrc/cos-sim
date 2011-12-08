/**
 * 
 */
package ru.cos.sim.road.objects;

/**
 * Rectangle road object with length and width.
 * Length is measured to the lengthy direction, 
 * width measured perpendicularly to the lengthy.
 * @author zroslaw
 */
public interface RectangleRoadObject extends RoadObject {
	
	/**
	 * Get length
	 * @return object's length
	 */
	public float getLength();
	
	/**
	 * Set length
	 * @param length
	 */
	public void setLength(float length);
	
	/**
	 * Get width
	 * @return object's width
	 */
	public float getWidth();
	
	/**
	 * Set width
	 * @param width
	 */
	public void setWidth(float width);
	
}
