package ru.cos.exception;

/**
 * General VE excpetion.
 * @author zroslaw
 */
public class VisualizerEngineException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VisualizerEngineException() {
	}

	public VisualizerEngineException(String arg0) {
		super(arg0);
	}

	public VisualizerEngineException(Throwable arg0) {
		super(arg0);
	}

	public VisualizerEngineException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
