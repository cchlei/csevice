/**
 * 
 */
package com.trgis.trmap.enterprise.web.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.trmap.enterprise.model.EntCainfo;
import com.trgis.trmap.enterprise.service.EntCainfoService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.enterprise.web.interceptor.annotation.UserCainfoRequired;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.EnterpriseUserService;

/**
 * 用户认证校验拦截器
 * 
 * @author Alice
 *
 */
@Component
public class UserCainfoInterceptor implements HandlerInterceptor {

	@Autowired
	private EnterpriseUserService entUserService;
	@Autowired
	private EntCainfoService cainfoService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		UserCainfoRequired annotation = method.getAnnotation(UserCainfoRequired.class);
		if(annotation != null){
			String username = (String) SecurityUtils.getSubject().getPrincipal();
			User user = entUserService.findUserByUsername(username);
			//未认证用户不得申请服务
			EntCainfo sqf = cainfoService.findByUser(user);
			if(BeanUtil.isNotEmpty(sqf)){
				return sqf.getCastatus().equals(EnumUtil.CASTATUS.AUDITED.getValue());
			}else{
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
