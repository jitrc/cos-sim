/**
 * 
 */
package ru.cos.sim.driver;

/**
 * 
 * @author zroslaw
 */
public class DriverException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DriverException() {
	}

	/**
	 * @param arg0
	 */
	public DriverException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public DriverException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public DriverException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
