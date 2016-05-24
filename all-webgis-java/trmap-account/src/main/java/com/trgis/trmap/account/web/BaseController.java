/**  
 * @Title: BaseController.java
 * @Package com.trgis.trmap.account.web
 * @author zhangqian
 * @date 2016年1月29日 下午2:08:19
 * @version V1.0  
 */
package com.trgis.trmap.account.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * @ClassName: BaseController
 * @Description: Controller的一些通用方法
 * @author zhangqian
 * @date 2016年1月29日 下午2:08:19
 *
 */
public class BaseController {

	/**
	 * @Title: validation @Description: 验证验证码是否正确
	 *
	 * @param validcode @return boolean
	 *
	 * @throws
	 */
	public boolean validation(String validcode) {
		String sessionCode = (String) getSession().getAttribute("rand_admin");
		return StringUtils.equalsIgnoreCase(sessionCode, validcode);
	}

	/**
	 * @Title: getSessionCode @Description: 获取当前session中保存的验证码
	 *
	 * @return String
	 *
	 * @throws
	 */
	protected Session getSession() {
		Session session = SecurityUtils.getSubject().getSession();
		return session;
	}

	/**
	 * @Title: checkValues @Description: 检查输入的参数是否正确
	 *
	 * @param username @param email @param password @param validcode void
	 *
	 * @throws
	 */
	protected void checkValues(String... value) {
		if (StringUtils.isAnyBlank(value)) {
			throw new RuntimeException("Input parameter error.");
		}
	}

}
