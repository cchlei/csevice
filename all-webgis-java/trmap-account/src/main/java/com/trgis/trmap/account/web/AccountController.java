/**  
 * @Title: AccountController.java
 * @Package com.trgis.trmap.account.web
 * @author zhangqian
 * @date 2016年1月29日 下午1:38:48
 * @version V1.0  
 */
package com.trgis.trmap.account.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.trmap.account.web.Interceptor.annotation.AccountConfineFogotRequired;
import com.trgis.trmap.userauth.exception.ValidationCodeException;
import com.trgis.trmap.userauth.model.AccountConfine;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserEntity;
import com.trgis.trmap.userauth.service.AccountConfineService;
import com.trgis.trmap.userauth.service.EnterpriseUserService;
import com.trgis.trmap.userauth.util.UserEncrypt;

/**
 * @ClassName: AccountController
 * @Description: 帐号业务控制
 * @author zhangqian
 * @date 2016年1月29日 下午1:38:48
 *
 */
@Controller
public class AccountController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	@Autowired
	private EnterpriseUserService userService;
	
	@Autowired
	private AccountConfineService accountConfineService;
	
	/**
	 * 拦截器 限制 企业账号 个人账号 注册错误跳转
	 * @author chlei
	 * @param rtype (0:代表个人账号,1 : 代表企业账号)
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/registerror", method = RequestMethod.GET)
	public ModelAndView registerror(String rtype){
		ModelAndView mod= new ModelAndView();
		mod.setViewName("account/oftenregerror");
		return mod;
	}
	
	/**
	 * 拦截器限制重置密码错误跳转
	 * @author chlei
	 * @param 	count:找回密码次数,
	 * 			minute:找回密码频率不能低于1分钟
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/fogoterror", method = RequestMethod.GET)
	public Map<String, Object> fogoterror(String count ,String minute){
		Map<String, Object> result = new HashMap<String, Object>();
		Integer size=Integer.parseInt(count);
		Integer minutesize=Integer.parseInt(minute); 
		if(size>=10){
			result.put("result", "error");
			result.put("msg", "今天找回密码次数已用完，请明天再试！");
		}
		if(minutesize<=1){
			result.put("result", "error");
			result.put("msg", "找回密码频率过高，请稍后尝试！");
			if(size>=10){
				result.put("result", "error");
				result.put("msg", "今天找回密码次数已用完，请明天再试！");
			}
		}
		
		return result;
	}
	
	/**
	 * 验证码
	 * @return
	 */
	@RequestMapping(value = "/random", method = RequestMethod.GET)
	public ModelAndView random(){
		ModelAndView mod= new ModelAndView();
		mod.setViewName("random");
		return mod;
	}
	/**
	 * 发送激活邮件
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resendmail/{userId}", method ={RequestMethod.POST, RequestMethod.GET})
	public Map<String, Object> resendmail(@PathVariable("userId") String userId) {
		logger.debug("重发激活邮件");
		Map<String, Object> map = new HashMap<String, Object>();
		// 解密
		String userIdEnc = "-1l";
		try {
			userIdEnc = UserEncrypt.QTAES_Decrypt(userId);
		} catch (Exception e1) {
			System.out.println("Decrypt error");
			e1.printStackTrace();
			map.put("result", "Decrypt error");
			return map;
		}
		User user = userService.findUser(Long.valueOf(userIdEnc));
		try {
			userService.sendUserActiveEmail(user);
			map.put("result", "success");
		} catch (Exception e) {
			System.out.println("noUser error");
			map.put("result", "noUser error");
			return map;
		}
		return map;
	}
	
	/**
    * 邮箱激活
    * @param email
    * @param code
    * @param mv
    * @return
    */
	@RequestMapping(value = "/active", method = RequestMethod.GET)
	public ModelAndView accountActive(@RequestParam String email, @RequestParam String code, ModelAndView mv) {
		logger.debug("收到邮箱激活请求.From:" + email);
		User user = null;
		try {//激活成功到注册成功页面
			user = userService.findUserByEmail(email);
			userService.activeUser(email, code);
			mv.addObject("username", user.getUsername());
			if(user.getEntity().getIndex() == UserEntity.ENTERPRISE.getIndex()){//企业版
				mv.setViewName("account/eactivesucc");
			}else{//个人版
				mv.setViewName("account/pactivesucc");
			}
			return mv;
		} catch (Exception e) {//激活失败
			mv.addObject("username", user.getUsername());
			mv.setViewName("account/activefail");
			return mv;
		}
	}
	
	/**
	 * @author Alice
	 * 跳转忘记密码第一步（输入邮箱页面）
	 *
	 */
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public ModelAndView forgot(ModelAndView mv) {
		mv.setViewName("account/validatemail");
		return mv;
	}
	
	/**
	 * @author Alice
	 * 确保用户修改密码的邮件链接仅为一封有效，若之前有修改连接则删除之前记录，创建新的连接
	 * @param email
	 * @return
	 */
	@AccountConfineFogotRequired
	@ResponseBody
	@RequestMapping(value = "/findUser")
	public Map<String, Object> findUser(String email, String u, String k,String validcode,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (!validation(validcode)) {
			result.put("result", "error");
			result.put("msg", "验证码错误！");
			return result;
		}
		try{
			User user = userService.findUserByEmail(email);
			if(user != null){
				logger.debug("=== user finded===");
				userService.findPassFromMailAndMobile(email, user.getMobile());
				logger.debug("=== resendPassEmail success===");
				//进行企业注册流水记录
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				String iprecords=request.getRemoteAddr();
				
				String date=sdf.format(new Date()); 
				
				AccountConfine accountConfine=new AccountConfine();
				accountConfine.setIp(iprecords);
				accountConfine.setRecordTime(date);
				accountConfine.setEmail(email);
				accountConfineService.addAccountConfine(accountConfine);
				logger.debug("=== add record success===");
				result.put("result", "success");
			}else{
				result.put("result", "error");
				result.put("msg", "未查找到用户,输入邮箱有误");
			}
		} catch (Exception e) {//激活失败
			result.put("result", "error");
			result.put("msg", "未查找到用户,输入邮箱有误");
		}
		return result;
	}
	
	/**
	 * @author Alice
	 * 跳转忘记密码第二步（输入密码页面）
	 * 如果该链接已经重置过密码则不能进行修改
	 */
	@RequestMapping(value = "/toforgot", method = RequestMethod.GET)
	public ModelAndView toforgot(@RequestParam String email, @RequestParam String u, @RequestParam String k, ModelAndView mv) {
		User user = userService.findUserByEmail(email);
		logger.debug("===do toresetpass===");
		boolean b = false;
		try {
			b = userService.validationResetPass(u, k);
		} catch (ValidationCodeException e) {
			mv.setViewName("account/shixiao");
		}
		if(b){
			mv.addObject("userId", user.getId());
			mv.addObject("u", u);
			mv.addObject("k", k);
			mv.setViewName("account/forgotpassword");//跳转去修改密码的页面
		}else{
			//"您已经通过该链接修改过密码,该链接已失效"
			mv.setViewName("account/czemsx");
		}
		return mv;
	}
	
	/**
	 * @author Alice
	 * 检查修改密码请求是否有效
	 * @param u
	 * @param k
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validateRequest")
	public Map<String, Object> validateRequest(String u, String k) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean b = false;
		try {
			b = userService.validationResetPass(u, k);
		} catch (ValidationCodeException e) {
			result.put("result", "error");
			result.put("msg", "此修改密码链接已失效");
			return result;
		}
		if(b){
			result.put("result", "success");
		}else{
			result.put("result", "error");
			result.put("msg", "您已经通过该链接修改过密码,该链接已失效");
		}
		return result;
	}
	
	/**
	 * @author Alice
	 * 忘记密码的重置密码
	 * @param newPass
	 * @return
	 */
	@RequestMapping(value = "/modifypass", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView modifypass(@RequestParam Long userId, @RequestParam String password, ModelAndView mv) {
	    logger.debug("===do resetpass===");
		User user = userService.findUser(Long.valueOf(userId));
	    if(user!=null){
	    	mv.addObject("user", user);
	    	mv.addObject("userId", user.getId());
	    	try {
				logger.debug("=== do changePass===");
				userService.resetPassword(user.getId(), password);
				logger.debug("=== changePass success===");
				mv.setViewName("account/passchangsucc");//密码修改成功
	        } catch (Exception e) {
				logger.debug("=== changePass fail===");
				mv.setViewName("account/passchangerr");//密码修改失败
           	}
	   }else{
			mv.setViewName("account/passchangerr");//密码修改失败
	   }
		return mv;
	}
	/**
	 * @Description:修改个人信息页面的邮箱验证
	 * @Author liuyan 
	 * @Date 2016年4月19日 上午11:07:09
	 * @param email
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/yzemail", method ={RequestMethod.POST, RequestMethod.GET})
	public Result YZEmail(String email) {
		Result result = new Result();
		try {
			User user = userService.findUserByEmail(email);
			if(user != null){
				result.setStatus(Result.STATUS_FAILED);
				result.setMsg("邮箱已存在");
				logger.debug("邮箱已存在！");
			}else{
				result.setStatus(Result.STATUS_OK);
				logger.debug("邮箱验证通过！");
			}
		} catch (Exception e) {
			logger.debug("邮箱验证失败！");
		}
		return result;
		
	}
}
