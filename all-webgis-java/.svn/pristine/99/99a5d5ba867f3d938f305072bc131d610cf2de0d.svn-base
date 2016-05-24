/**
 * 
 */
package com.trgis.trmap.account.web.Interceptor;


import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.trmap.account.web.Interceptor.annotation.AccountConfineFogotRequired;
import com.trgis.trmap.userauth.model.AccountConfine;
import com.trgis.trmap.userauth.service.AccountConfineService;

/**
 * 用户操作忘记密码 发送邮箱验证合法性校验拦截器
 * 
 * @author chenhl
 *
 */
@Component
public class AccountConfineFogotInterceptor implements HandlerInterceptor {

	@Autowired
	private AccountConfineService accountConfineService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		AccountConfineFogotRequired annotation = method.getAnnotation(AccountConfineFogotRequired.class);
		// 限制某个电脑找回密码发送邮件 不能频率过高的发送邮件并且一天最多能发多少个
		if(annotation != null){
			
			String ip=request.getRemoteAddr();
			
			String email=request.getParameter("email");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String strdates=df.format(new Date());
			//1,查询访问的ip 注册记录条数
			List<AccountConfine> count = accountConfineService.findByEmail(ip,email,strdates);
			//2, 当前时间减去 本机（ip）注册最后时间 换算注册的频率  
            // 这个的除以1000得到秒，相应的60000得到分，3600000得到小时  
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String strdate=sdf.format(new Date());
			Date   pardate=sdf.parse(strdate);
			long  	minute;
			if(count.size()>0){
				 minute=(pardate.getTime() - sdf.parse(count.get(0).getRecordTime()).getTime())/60000;	
			}else{
				minute=3;
			}
 		   	//3,判断一天找回次数是否小于10次 并且频率minute大于1分钟 也就是找回后一分钟内不能再次进行找回 
			if(count.size()<10&&minute>1){
					return true;
			}else{
				//4,非法请求 即这些请求不符合 找回密码规定 
			    //转发到错误页面  
				response.sendRedirect(request.getContextPath() + "/fogoterror?count="+count.size()+"&minute="+minute);
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
