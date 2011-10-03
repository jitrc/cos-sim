/**
 * 
 */
package ru.cos.cs.lengthy.exceptions;

/**
 * Illegal operation.
 * @author zroslaw
 */
public class IllegalOperationExceprion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public IllegalOperationExceprion() {
		super();
	}

	/**
	 * @param arg0
	 */
	public IllegalOperationExceprion(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public IllegalOperationExceprion(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public IllegalOperationExceprion(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
