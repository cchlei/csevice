package com.trgis.trmap.qtuser.exception;
/**
 * 矢量附件操作异常
 * @author Alice
 * 2015年9月19日
 */
public class AttachfileException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AttachfileException() {
		super();
		
	}

	public AttachfileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public AttachfileException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public AttachfileException(String message) {
		super(message);
		
	}

	public AttachfileException(Throwable cause) {
		super(cause);
		
	}

	
}
