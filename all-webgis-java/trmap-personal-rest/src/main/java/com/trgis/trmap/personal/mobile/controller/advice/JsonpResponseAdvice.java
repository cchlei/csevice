/**
 * 
 */
package com.trgis.trmap.personal.mobile.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * 视图解析返回ResponseBody时建议返回类型
 * 
 * @author zhangqian
 *
 */
@ControllerAdvice
public class JsonpResponseAdvice extends AbstractJsonpResponseBodyAdvice {

	public JsonpResponseAdvice() {
		//支持jsonp返回的方法限定参数 
		//http://ip:port/[app]/action?[callback|jsonp|jsonpcallback]=callbackmethodnameparam
		super("callback","jsonp","jsonpcallback");
	}
	
}
