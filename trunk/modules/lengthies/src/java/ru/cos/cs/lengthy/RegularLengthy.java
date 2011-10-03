/**
 * 
 */
package ru.cos.cs.lengthy;

/**
 * Regular lengthy that has previous and next lengthies.
 * @author zroslaw
 */
public interface RegularLengthy extends Lengthy {
	
	/**
	 * Returns next lengthy.
	 * @return next lengthy.
	 */
	public Lengthy getNext();

	/**
	 * Returns previous lengthy.
	 * @return previous lengthy.
	 */
	public Lengthy getPrev();
	
	/**
	 * Set next lengthy.
	 * @param next next lengthy
	 */
	public void setNext(Lengthy next);

	/**
	 * Set previous lengthy
	 * @param prev previous lengthy
	 */
	public void setPrev(Lengthy prev);

}
