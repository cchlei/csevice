package com.trgis.trmap.userauth.exception;

/**
 * 账号查询异常
 * 
 * 通常出现在通过邮箱或者是验证码验证错误的时候抛出此异常
 * 
 * @author zhangqian
 *
 */
public class AccountFindException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3148041254883778898L;

	public AccountFindException() {
		super();
	}

	public AccountFindException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccountFindException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountFindException(String message) {
		super(message);
	}

	public AccountFindException(Throwable cause) {
		super(cause);
	}

}
