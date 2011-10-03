package ru.cos.nissan.simulation;

/**
 * Visual controller exception.
 * @author zroslaw
 */
@SuppressWarnings("serial")
public class VisualControllerException extends RuntimeException {

	public VisualControllerException() {
	}

	public VisualControllerException(String message) {
		super(message);
	}

	public VisualControllerException(Throwable cause) {
		super(cause);
	}

	public VisualControllerException(String message, Throwable cause) {
		super(message, cause);
	}

}
