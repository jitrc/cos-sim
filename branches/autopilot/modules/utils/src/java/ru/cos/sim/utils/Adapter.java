package ru.cos.sim.utils;

public interface Adapter<S, D> {

	public D adapt(S source);
	
}
