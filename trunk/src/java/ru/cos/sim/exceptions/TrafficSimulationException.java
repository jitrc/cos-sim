package ru.cos.sim.exceptions;
/**
 * 
 */

/**
 * @author zroslaw
 *
 */
public class TrafficSimulationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TrafficSimulationException() {
		super();
	}

	public TrafficSimulationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public TrafficSimulationException(String arg0) {
		super(arg0);
	}

	public TrafficSimulationException(Throwable arg0) {
		super(arg0);
	}

}
