/**  
 * @Title: JSONPResult.java
 * @Package com.trgis.trmap.account.web
 * @author zhangqian
 * @date 2016年1月29日 下午1:58:07
 * @version V1.0  
 */
package com.trgis.trmap.system.utils;

/**
 * @ClassName: JSONPResult
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhangqian
 * @date 2016年1月29日 下午1:58:07
 *
 */
public class JSONPResult {

	static final String JSONP = "%s(%s)";

	private String callback;

	private Result result;

	public static String jsonp(String callback, Result result) {
		return String.format(JSONP, callback, result.json());
	}

	/**
	 * @return callback
	 */
	public String getCallback() {
		return callback;
	}

	/**
	 * callback 要设置的 callback
	 */
	public void setCallback(String callback) {
		this.callback = callback;
	}

	/**
	 * @return result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * result 要设置的 result
	 */
	public void setResult(Result result) {
		this.result = result;
	}

}
