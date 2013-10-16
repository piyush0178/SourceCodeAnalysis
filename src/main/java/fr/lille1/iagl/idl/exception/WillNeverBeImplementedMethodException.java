package fr.lille1.iagl.idl.exception;

public class WillNeverBeImplementedMethodException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String message;

	public WillNeverBeImplementedMethodException(final String message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}
