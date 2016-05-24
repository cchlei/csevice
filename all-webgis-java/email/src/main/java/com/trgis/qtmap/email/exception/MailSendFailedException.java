package com.trgis.qtmap.email.exception;

/**
 * 邮件发送失败时抛出此异常
 * 
 * @author zhangqian
 *
 */
public class MailSendFailedException extends Exception {
	
	public MailSendFailedException() {
		super();
	}

	public MailSendFailedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MailSendFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailSendFailedException(String message) {
		super(message);
	}

	public MailSendFailedException(Throwable cause) {
		super(cause);
	}

}
