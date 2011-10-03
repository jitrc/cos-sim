package ru.cos.cs.lengthy;

import java.util.Set;

/**
 * Lengthies join.
 * 
 * @author zroslaw
 */
public interface Join extends Lengthy {

	/**
	 * Retrieve joined lengthies.
	 * @return
	 */
	public Set<RegularLengthy> getJoinedLengthies();
	
	/**
	 * Join specified lengthy.
	 * @param lengthy lengthy to be joined
	 */
	public void join(RegularLengthy joinedLengthy);

	/**
	 * Unjoin lengthy.
	 * @param lengthy
	 */
	public void unjoin(RegularLengthy lengthy);

	public void setNext(RegularLengthy nextLengthy);
	
	public RegularLengthy getNext();
	
}