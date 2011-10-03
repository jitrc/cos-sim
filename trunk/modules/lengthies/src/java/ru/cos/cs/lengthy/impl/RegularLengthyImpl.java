/**
 * 
 */
package ru.cos.cs.lengthy.impl;

import ru.cos.cs.lengthy.Lengthy;
import ru.cos.cs.lengthy.RegularLengthy;

/**
 * Implementation of Lengthy interface
 * @author zroslaw
 */
public class RegularLengthyImpl extends LengthyImpl	implements RegularLengthy {
	
	protected final float length;

	public RegularLengthyImpl(float length) {
		this.length = length;
	}

	@Override
	public Lengthy getNext() {
		return next;
	}

	@Override
	public Lengthy getPrev() {
		return prev;
	}

	@Override
	public float getLength() {
		return length;
	}

	@Override
	public void setNext(Lengthy next) {
		this.next = next;
	}

	@Override
	public void setPrev(Lengthy prev) {
		this.prev = prev;
	}
	
}
