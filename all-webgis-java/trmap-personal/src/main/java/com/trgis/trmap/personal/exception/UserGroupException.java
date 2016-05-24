package com.trgis.trmap.personal.exception;


/**
 * @ClassName: UserGroupException
 * @Description: 
 * @author yanpeng
 * @date 2016年3月25日 上午9:17:47
 */
public class UserGroupException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserGroupException() {
		super();
		
	}

	public UserGroupException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public UserGroupException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public UserGroupException(String message) {
		super(message);
		
	}

	public UserGroupException(Throwable cause) {
		super(cause);
		
	}
}
