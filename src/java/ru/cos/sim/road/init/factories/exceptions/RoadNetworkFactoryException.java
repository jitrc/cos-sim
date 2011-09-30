package ru.cos.sim.road.init.factories.exceptions;

/**
 * Road network factory exception
 * @author zroslaw
 */
public class RoadNetworkFactoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RoadNetworkFactoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoadNetworkFactoryException(String message) {
		super(message);
	}

	public RoadNetworkFactoryException(Throwable cause) {
		super(cause);
	}

}
