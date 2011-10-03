/**
 * 
 */
package ru.cos.sim.exceptions;

/**
 * General exception during traffic agent creation.
 * @author zroslaw
 */
@SuppressWarnings("serial")
public class TrafficAgentCreationException extends RuntimeException {

	public TrafficAgentCreationException() {
		super();
	}

	public TrafficAgentCreationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public TrafficAgentCreationException(String arg0) {
		super(arg0);
	}

	public TrafficAgentCreationException(Throwable arg0) {
		super(arg0);
	}

}
