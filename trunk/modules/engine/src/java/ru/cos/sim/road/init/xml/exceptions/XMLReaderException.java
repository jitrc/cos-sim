/**
 * 
 */
package ru.cos.sim.road.init.xml.exceptions;

/**
 * Exception while reading XML data
 * @author zroslaw
 */
public class XMLReaderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param arg0
	 */
	public XMLReaderException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public XMLReaderException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public XMLReaderException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
