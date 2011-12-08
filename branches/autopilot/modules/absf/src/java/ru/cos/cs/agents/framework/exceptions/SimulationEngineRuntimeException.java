/**
 * 
 */
package ru.cos.cs.agents.framework.exceptions;

/**
 * @author zroslaw
 *
 */
public class SimulationEngineRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SimulationEngineRuntimeException() {
		super();
	}

	public SimulationEngineRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SimulationEngineRuntimeException(String arg0) {
		super(arg0);
	}

	public SimulationEngineRuntimeException(Throwable arg0) {
		super(arg0);
	}

}
