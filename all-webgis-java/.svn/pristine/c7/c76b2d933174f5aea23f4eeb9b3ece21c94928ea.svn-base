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

import com.trgis.trmap.account.web.Interceptor.annotation.AccountConfineRegistRequired;
import com.trgis.trmap.userauth.model.AccountConfine;
import com.trgis.trmap.userauth.service.AccountConfineService;


/**
 * 用户 注册 校验拦截器
 * 
 * @author chenhl
 *
 */
@Component
public class AccountConfineRegistInterceptor implements HandlerInterceptor {
	@Autowired
	private AccountConfineService accountConfineService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		AccountConfineRegistRequired annotation = method.getAnnotation(AccountConfineRegistRequired.class);
		//  限制注册次数 和访问频率
		if(annotation != null){
			
			String ip=request.getRemoteAddr();
			Integer rtype=Integer.parseInt(request.getParameter("rtype"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String strdates=df.format(new Date());
			//1,查询访问的ip 注册记录条数
			List<AccountConfine> countList = accountConfineService.findAccountConfineByIp(ip,rtype,strdates);
			// 2, 当前时间减去 本机（ip）注册最后时间 换算注册的频率  
            // 这个的除以1000得到秒，相应的60000得到分，3600000得到小时
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String strdate=sdf.format(new Date());
			Date   pardate=sdf.parse(strdate);
			long  	minute;
			if(countList.size()>0){
				 minute=(pardate.getTime() - sdf.parse(countList.get(0).getRecordTime()).getTime())/60000;	
			}else{
				minute=3;
			}
			
	   		//3,判断一天注册次数是否小于10次 并且频率minute大于1分钟 也就是注册后一分钟内不能再次进行注册
			if(countList.size()<10&&minute>1){
				return true;
			}else{
				//4,非法请求 即这些请求不符合 注册规定  
			    //转发到错误页面  
				response.sendRedirect(request.getContextPath() + "/registerror?rtype="+rtype);
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
