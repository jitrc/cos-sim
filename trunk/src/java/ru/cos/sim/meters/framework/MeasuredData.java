/**
 * 
 */
package ru.cos.sim.meters.framework;

/**
 * 
 * @author zroslaw
 */
public interface MeasuredData<T extends MeasuredData<?>> extends Cloneable{

	public T clone();
	
	public void normalize(T norma);
}
