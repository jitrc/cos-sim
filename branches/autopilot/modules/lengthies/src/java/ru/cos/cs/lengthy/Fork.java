package ru.cos.cs.lengthy;

import java.util.Set;

/**
 * Fork.
 * When observation process comes through Fork instance, it must put into observations
 * appropriate ForkPoint instance.
 * @author zroslaw
 */
public interface Fork extends Lengthy{
	
	/**
	 * Retrieve forked lengthies.
	 * @return
	 */
	public Set<RegularLengthy> getForkedLengthies();
	
	/**
	 * Fork to specified lengthy. Parameter object will be changed too.
	 * @param lengthy
	 */
	public void forkTo(RegularLengthy lengthy);

	/**
	 * Unfork specified lengthy. Parameter object will be changed too.
	 * @param lengthy
	 */
	public void unforkTo(RegularLengthy lengthy);

	public void setPrev(RegularLengthy prevLengthy);
	
	public RegularLengthy getPrev();

}