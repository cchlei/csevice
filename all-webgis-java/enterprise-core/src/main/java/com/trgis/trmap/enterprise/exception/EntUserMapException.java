package com.trgis.trmap.enterprise.exception;

public class EntUserMapException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EntUserMapException() {
		super();
		
	}

	public EntUserMapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public EntUserMapException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public EntUserMapException(String message) {
		super(message);
		
	}

	public EntUserMapException(Throwable cause) {
		super(cause);
		
	}

}
