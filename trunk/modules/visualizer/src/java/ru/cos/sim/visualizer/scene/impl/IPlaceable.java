package ru.cos.sim.visualizer.scene.impl;

import ru.cos.sim.visualizer.color.Color;
import ru.cos.sim.visualizer.renderer.impl.IRenderable;

/**
 * @author Dudinov Ivan
 *
 */
public interface IPlaceable extends IRenderable {
	
	public void setTranslation(float x , float y, float z);
	public void preRender();
	public void postRender();
	public void flushModifications();
	public void setColor(Color c);
	public void checkFrustum();
	/**
	 * @param x
	 * @param y
	 */
	
	/**
	 * Sets rotation to this object. Place object in direction
	 * of the vector. Try to not use this method 
	 * in the loop due to high computation difficulty;
	 *  
	 * @deprecated
	 * @param x - x coordinate of this vector
	 * @param y - y coordinate of this vector
	 */
	public void applyRotation(float x , float y);
	
	/**
	 * Sets rotation to this object. Angle in degrees.
	 * @param angle - angle in degrees
	 */
	public void setRotation(float angle);
}
