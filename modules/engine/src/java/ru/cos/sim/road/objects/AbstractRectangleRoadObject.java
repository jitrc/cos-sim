/**
 * 
 */
package ru.cos.sim.road.objects;


/**
 * 
 * @author zroslaw
 */
public abstract class AbstractRectangleRoadObject extends AbstractRoadObject implements
		RectangleRoadObject {

	/**
	 * Length in meters.
	 */
	protected float length = 0;
	
	/**
	 * Width in meters.
	 */
	protected float width = 0;
	
	@Override
	public float getLength() {
		return length;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public void setLength(float length) {
		this.length = length;
	}

	@Override
	public void setWidth(float width) {
		this.width = width;
	}

}
