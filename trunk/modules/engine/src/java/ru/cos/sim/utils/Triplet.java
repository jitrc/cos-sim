/**
 * 
 */
package ru.cos.sim.utils;

/**
 * Triplet of objects.
 * @author zroslaw
 */
public class Triplet<F,S,T> {
	private F first;
	private S second;
	private T third;

	public Triplet(F f, S s,T t){ 
		first = f;
		second = s;
		third = t;
	}
	
	public Triplet() {
	}

	public F getFirst(){
		return first;
	}
	
	public S getSecond()   {
		return second;
	}
	
	public T getThird()   {
		return third;
	}

	public void setFirst(F first) {
		this.first = first;
	}

	public void setSecond(S second) {
		this.second = second;
	}

	public void setThird(T third) {
		this.third = third;
	}

	public String toString()  { 
		return "(" + first.toString() + ", " + second.toString() + third.toString() + ")"; 
	}
}

