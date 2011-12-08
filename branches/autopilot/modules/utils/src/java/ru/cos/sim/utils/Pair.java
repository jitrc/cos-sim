package ru.cos.sim.utils;

/**
 * Pair of two objects.
 * @author zroslaw
 */
public class Pair<T, S>{
	private T first;
	private S second;

	public Pair(T f, S s){ 
		first = f;
		second = s;   
	}
	public T getFirst(){
		return first;
	}
	public S getSecond()   {
		return second;
	}
	public void setFirst(T first) {
		this.first = first;
	}
	public void setSecond(S second) {
		this.second = second;
	}
	public String toString()  { 
		return "(" + first.toString() + ", " + second.toString() + ")"; 
	}
}