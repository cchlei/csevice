package com.trgis.trmap.personal.exception;

/***
 * @ClassName: UserAccountException
 * @Description: TODO
 * @author yanpeng
 * @date 2016年3月29日 上午11:09:28
 */
public class UserIntegralException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserIntegralException() {
		super();
		
	}

	public UserIntegralException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public UserIntegralException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public UserIntegralException(String message) {
		super(message);
		
	}

	public UserIntegralException(Throwable cause) {
		super(cause);
		
	}
}
