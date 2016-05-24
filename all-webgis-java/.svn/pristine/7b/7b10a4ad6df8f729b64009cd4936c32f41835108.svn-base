/**
 * 
 */
package com.trgis.trmap.enterprise.web.interceptor;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntMapService;
import com.trgis.trmap.enterprise.web.interceptor.annotation.UserMapRequired;

/**
 * 用户操作地图合法性校验拦截器
 * 
 * @author zhangqian
 *
 */
@Component
public class UserMapAuthcInterceptor implements HandlerInterceptor {

	@Autowired
	private EntMapService entMapService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		UserMapRequired annotation = method.getAnnotation(UserMapRequired.class);
		if(annotation != null){
			// 用于获取@PathVariable的值
			Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			Long mapId = Long.valueOf((String) pathVariables.get("mapid"));
			String userName = (String) SecurityUtils.getSubject().getPrincipal();
			EntUserMap entUserMap = entMapService.findUserMapById(mapId);
			return StringUtils.equals(entUserMap.getUser().getUsername(), userName);
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
