package ru.cos.sim.road.exceptions;

/**
 * General road network exception.
 * @author zroslaw
 */
public class RoadNetworkException extends RuntimeException {

	/**
	 * Default agentId
	 */
	private static final long serialVersionUID = 1L;

	public RoadNetworkException() {
	}

	public RoadNetworkException(String arg0) {
		super(arg0);
	}

	public RoadNetworkException(Throwable arg0) {
		super(arg0);
	}

	public RoadNetworkException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
