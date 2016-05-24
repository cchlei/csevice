package com.trgis.trmap.personal.exception;

public class UserCollectException extends Exception {
	/**
	 * 自定义收藏异常类
	 * @author yanpeng
	 */
	private static final long serialVersionUID = 1L;

	public UserCollectException() {
		super();
		
	}

	public UserCollectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public UserCollectException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public UserCollectException(String message) {
		super(message);
		
	}

	public UserCollectException(Throwable cause) {
		super(cause);
		
	}
}
