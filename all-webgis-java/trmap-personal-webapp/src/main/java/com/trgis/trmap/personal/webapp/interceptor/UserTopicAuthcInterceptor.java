/**
 * 
 */
package com.trgis.trmap.personal.webapp.interceptor;

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

import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.personal.service.UserTopicService;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.personal.webapp.interceptor.annotation.UserTopicRequired;

/**
 * 用户操作地图合法性校验拦截器
 * 
 * @author zhangqian
 *
 */
@Component
public class UserTopicAuthcInterceptor implements HandlerInterceptor {

	@Autowired
	private UserTopicService userTopicService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		UserTopicRequired annotation = method.getAnnotation(UserTopicRequired.class);
		if(annotation != null){
			// 用于获取@PathVariable的值
			Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			Long id = Long.valueOf((String) pathVariables.get("id"));
			String userName = (String) SecurityUtils.getSubject().getPrincipal();
			UserTopic userTopic = userTopicService.findUserTopicById(id);
			if(userTopic.getShareflag().equals(EnumUtil.SHAREFLAG.YFX.getValue())){
				return true;
			}
			return StringUtils.equals(userTopic.getUser().getUsername(), userName);
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
