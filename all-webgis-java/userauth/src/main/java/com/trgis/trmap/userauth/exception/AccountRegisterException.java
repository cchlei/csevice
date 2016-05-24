package com.trgis.trmap.userauth.exception;

/**
 * 用户账号注册时由于注册信息错误抛出此异常
 * @author zhangqian
 *
 */
public class AccountRegisterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2512462517365124534L;

	public AccountRegisterException() {
		super();
	}

	public AccountRegisterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccountRegisterException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountRegisterException(String message) {
		super(message);
	}

	public AccountRegisterException(Throwable cause) {
		super(cause);
	}

}
