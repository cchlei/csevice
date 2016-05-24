/**  
 * @Title: Result.java
 * @Package com.trgis.trmap.account.web
 * @author zhangqian
 * @date 2016年1月29日 下午1:43:34
 * @version V1.0  
 */
package com.trgis.trmap.account.web;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @ClassName: Result
 * @Description: 封装返回结果集
 * @author zhangqian
 * @param <T>
 * @date 2016年1月29日 下午1:43:34
 *
 */
public class Result {

	/**
	 * 结果状态码
	 */
	public static final int STATUS_OK = 0; // 成功
	public static final int STATUS_FAILED = 1; // 失败
	
	public static final String DEFAUL_MESSAGE = "ok"; // 默认返回的结果消息

	@JSONField(name="status")
	@JsonProperty(value="status")
	private int status;

	@JSONField(name="msg")
	@JsonProperty(value="msg")
	private String msg;
	
	@JSONField(name="data")
	@JsonProperty(value="data")
	private Object data;
	
	/**
	 * @return data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * data 要设置的 data
	 */
	public void setData(Object data) {
		this.data = data;
	}

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
	 * @return msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * msg 要设置的 msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @Title: json
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 *
	 * @return Object
	 *
	 * @throws
	 */
	public String json() {
		return JSONObject.toJSONString(this);
	}

}
