/**
 * 
 */
package ru.cos.cs.lengthy.exceptions;

/**
 * Illegal lengthies navigation exception.
 * For example, when you try to get next lengthy in fork, but it doesn't exist.
 * @author zroslaw
 */
public class IllegalLengthiesNavigationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalLengthiesNavigationException() {
		super();
	}

	public IllegalLengthiesNavigationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public IllegalLengthiesNavigationException(String arg0) {
		super(arg0);
	}

	public IllegalLengthiesNavigationException(Throwable arg0) {
		super(arg0);
	}

}
