package com.trgis.trmap.personal.mobile.controller.result;

/**
 * 事件响应结果
 * 
 * @author zhangqian
 *
 */
public class ActionResult {

	// 状态码
	private int status;

	// 消息
	private String message;

	// 结果
	private Object result;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * 设置结果，不含数据
	 * @param status
	 * @param string
	 */
	public void set(int status, String string) {
		this.status = status;
		this.message = string;
	}
	
	/**
	 * 设置结果信息，含数据
	 * @param status
	 * @param string
	 * @param result
	 */
	public void set(int status, String string, String result) {
		this.status = status;
		this.message = string;
		this.result = result;
	}

}
