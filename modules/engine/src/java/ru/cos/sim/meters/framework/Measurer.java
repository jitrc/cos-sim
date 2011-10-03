/**
 * 
 */
package ru.cos.sim.meters.framework;

/**
 *
 * @author zroslaw
 */
public interface Measurer<T> {
	
	public void measure(float dt);
	
	public T getInstantData();
	
}
