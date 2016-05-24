/**  
 * @Title: RegisterResult.java
 * @Package com.trgis.trmap.personal.mobile.controller.result
 * @author zhangqian
 * @date 2016年1月15日 下午4:59:54
 * @version V1.0  
 */
package com.trgis.trmap.personal.mobile.controller.result;

/**
 * @ClassName: RegisterResult
 * @Description: 用户注册结果
 * @author zhangqian
 * @date 2016年1月15日 下午4:59:54
 *
 */
public class RegisterResult {

	/**
	 * 注册成功
	 */
	public static final int SUCCESS = 0;
	
	/**
	 * 注册失败
	 */
	public static final int FAILED = 1;
	
	public static final String MSG_OK = "ok";

	// 状态码 0 成功 非0 失败
	private int status;

	// 消息
	private String message = "";

	/**
	 * @return status
	 */

	public int getStatus() {
		return status;
	}

	/**
	 * status 要设置的 status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return message
	 */

	public String getMessage() {
		return message;
	}

	/**
	 * message 要设置的 message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
