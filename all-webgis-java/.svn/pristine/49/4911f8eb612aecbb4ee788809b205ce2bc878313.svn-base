package com.trgis.trmap.enterprise.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.userauth.exception.AccountRegisterException;
import com.trgis.trmap.userauth.exception.ValidationCodeException;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.EnterpriseUserService;
import com.trgis.trmap.userauth.service.UserForm;
import com.trgis.trmap.userauth.util.UserEncrypt;

/**
 * 用户账户控制器
 * @author doris
 * @date 2015-11-27
 *
 */
@Controller
@RequestMapping(value = "/entaccount")
public class EntAccountController {

	private static final Logger logger = LoggerFactory.getLogger(EntAccountController.class);

	private static final String JSONP = "%s(%s)";

	@Autowired
	private EnterpriseUserService entUserService;
	
	@RequestMapping(value = "/random", method = RequestMethod.GET)
	public ModelAndView random(){
		ModelAndView mod= new ModelAndView();
		mod.setViewName("random");
		return mod;
	}
	
	/**
	 * 获取用户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getinfo",method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = entUserService.findUserByUsername(username);
		if (BeanUtil.isNotEmpty(user)) {
			map.put("user", user);
			map.put("result","success");
			map.put("msg","获取用户信息成功！");
		}else{
			map.put("user", new User());
			map.put("result","error");
			map.put("msg","您尚未登录，请先登录再进行操作!");
		}
		
		return map;
	}
	/**
	 * 用户注册--发送激活邮件
	 * @param username
	 * @param enterprisename
	 * @param email
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView doRegister(@RequestParam String username,@RequestParam String enterprisename, 
			@RequestParam String email,@RequestParam String phone,@RequestParam String password
			) {
		ModelAndView mv = new ModelAndView();
		logger.debug("===do register===");
		UserForm userForm = new UserForm();
		userForm.setName(enterprisename);
		userForm.setUsername(username);
		userForm.setEmail(email);
		userForm.setTextpassword(password);
		userForm.setMobile(phone);
		User user = null;
		try {
			user = entUserService.createUser(userForm);
			userForm.cleanTextPass(); // 处理完后清空用户输入的密码
		//	System.out.println(user.getEmail()+user.getName()+user.getSalt()+user);
			
		} catch (AccountRegisterException e) {
			mv.addObject("msg","注册失败！");
			mv.setViewName("account/prompt_zc_fail");
			return mv;
		}
		try {
			entUserService.sendUserActiveEmail(user);
		
		}catch (Exception e) {
			// TODO 优化邮件发送失败的后续流程
			/*
			 * 出现该异常的状况。 用户账号已经注册在系统中了，但是发送激活邮件失败， 则需要用以下情况描述中的可能性进行处理。
			 */

			/*
			 * 关于用户账户激活有可能出现的情况描述
			 *
			 * 1.如果注册后邮件发送失败，则需要给用户重新发送激活邮件的机会
			 * 
			 * 前置条件：用户没有激活，如果已激活则不触发2的情况
			 * 2.用户没有点击重新发送激活邮件的时候，在用户下次登录的时候进入提示页面，提示用户到邮箱去激活，这时候显示部分邮箱名称
			 * 2.1如果用户进入邮箱找不到或者没有收到激活邮件，则可以让用户触发再次发送激活邮件的请求。 2.2用户收到激活连接点击激活
			 * 
			 */
				try {
					mv.addObject("userId", UserEncrypt.QTAES_Encrypt(user.getId().toString()));
			 	} catch (Exception e1) {
					throw new RuntimeException("系统错误，用户信息处理失败");
			    }
		    }
		try {
			mv.addObject("userId", UserEncrypt.QTAES_Encrypt(user.getId().toString()));
		} catch (Exception e) {
			throw new RuntimeException("系统错误，用户信息处理失败");
		}
		mv.addObject("user", userForm);
		mv.setViewName("account/prompt_zc_success");
		return mv;
	}
	
   /**
    * 重发激活邮件
    * @param userId
    * @return
    */
	@ResponseBody
	@RequestMapping(value = "/resendmail", method = RequestMethod.POST)
	public Map<String, Object> reSendEmail(String userId) {
		logger.debug("重发激活邮件");
		Map<String, Object> map = new HashMap<String, Object>();
		User user = entUserService.findUser(Long.valueOf(userId));
		try {
			entUserService.sendUserActiveEmail(user);
			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "error");
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
		User user=null;
		try {//激活成功到注册成功页面
		    user = entUserService.activeUser(email, code);
			mv.addObject("username", user.getUsername());
			mv.setViewName("account/prompt_qyzh_activationsucc");
			return mv;
		} catch (Exception e) {//激活失败
			mv.addObject("username", user.getUsername());
			mv.setViewName("account/prompt_qyzh_activationfail");
			return mv;
		}
		
	}
    /**
     * 用户信息维护（修改，目前没有界面）
     * @param id
     * @param username
     * @param enterprisename
     * @param email
     * @param phone
     * @param password
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/update", method ={RequestMethod.POST, RequestMethod.GET})
	public  Map<String, Object> doUpdate(String phone,String url) {
		 Map<String, Object> map = new HashMap<String, Object>();
		try {
			String username = (String) SecurityUtils.getSubject().getPrincipal();
			User user = entUserService.findUserByUsername(username);
			if(url!=null && !url.isEmpty()){
		        user.setHeadimg(url);
		        }
	        if(phone!=null && !phone.isEmpty()){
	        	if(entUserService.findUserByMobile(phone)==null){
	        		user.setMobile(phone);
	        	}else{
	        		map.put("result", "error");
	    			map.put("msg", "该电话号码已存在！");
	        	}
	        }
	    	UserForm userForm = new UserForm();
	    	BeanUtils.copyProperties(userForm,user);
	        entUserService.editUser(user.getId(), userForm);
		   	map.put("result", "success");
			map.put("msg", "修改用户基本信息成功！");
		} catch (Exception e) {
			map.put("result", "error");
			map.put("msg", "修改用户基本信息失败！");
		}
		return map;
	}
		
	
	
	/**
	 * 修改密码
	 * @param userEmail
	 * @param oldPass
	 * @param newPass
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resetpass", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView resetpass(@RequestParam String userId, @RequestParam String oldpassword,@RequestParam String newpassword,ModelAndView mv) {
	    logger.debug("===do resetpass===");
		User user = entUserService.findUser(Long.valueOf(userId));
		mv.addObject("user", user);
	    if(user!=null){
	    	mv.addObject("userId", user.getId());
	    	try {
				logger.debug("=== do changePass===");
				entUserService.changePass(user.getId(), oldpassword, newpassword);
				logger.debug("=== changePass success===");
				mv.setViewName("account/prompt_passchangsucc");//密码修改成功
	        } catch (Exception e) {
				logger.debug("=== changePass fail===");
				mv.setViewName("account/prompt_passchangerr");//密码修改失败
           	}
	   }else{
		   mv.setViewName("account/prompt_passchangerr");//密码修改失败
	   }
		return mv;
	}

	/**
	 * 去修改密码页面填写密码
	 *
	 */
	@RequestMapping(value = "/toreset", method = RequestMethod.GET)
	public ModelAndView toresetpass(@RequestParam String email,ModelAndView mv,HttpServletResponse response) {
			response.setHeader("Access-Control-Allow-Origin", "*");
			User user=entUserService.findUserByEmail(email);
			logger.debug("===do toresetpass===");
			mv.addObject("userId", user.getId());
			mv.setViewName("account/prompt_resetpassword");//密码修改
			return mv;
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
	 * 查看是否此用户通过链接已经修改过了，已修改则不能再次修改
	 * @param email
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findUser")
	public Map<String, Object> findUser(String email, String u, String k) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = entUserService.findUserByEmail(email);
		if(BeanUtil.isNotEmpty(user)){
			logger.debug("=== user finded===");
			entUserService.findPassFromMailAndMobile(email, user.getMobile());
			logger.debug("=== resendPassEmail success===");
			result.put("result", "success");
		}else{
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
		User user=entUserService.findUserByEmail(email);
		logger.debug("===do toresetpass===");
		mv.addObject("userId", user.getId());
		mv.addObject("u", u);
		mv.addObject("k", k);
		mv.setViewName("account/prompt_forgotpassword");
		return mv;
	}
	
	/**
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
			b = entUserService.validationResetPass(u, k);
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
	 * 
	 * @param newPass
	 * @return
	 */
	@RequestMapping(value = "/modifypass", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView modifypass(@RequestParam Long userId, @RequestParam String password, ModelAndView mv) {
	    logger.debug("===do resetpass===");
		User user = entUserService.findUser(Long.valueOf(userId));
	    if(user!=null){
	    	mv.addObject("user", user);
	    	mv.addObject("userId", user.getId());
	    	try {
				logger.debug("=== do changePass===");
				entUserService.resetPassword(user.getId(), password);
				logger.debug("=== changePass success===");
				mv.setViewName("account/prompt_passchangsucc");//密码修改成功
	        } catch (Exception e) {
				logger.debug("=== changePass fail===");
				mv.setViewName("account/prompt_passchangerr");//密码修改失败
           	}
	   }else{
			mv.setViewName("account/prompt_passchangerr");//密码修改失败
	   }
		return mv;
	}
	/**
	 * 发送重置密码邮件
	 * @param userEmail
	 * @param oldPass
	 * @param newPass
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resendPassEmail", method = RequestMethod.POST)
	public Map<String, Object> resendPassEmail(String email,HttpServletResponse response) {
		    response.setHeader("Access-Control-Allow-Origin", "*");
			User user = null;
			logger.debug("=== resendPassEmail===");
			Map<String, Object> map = new HashMap<String, Object>();
		try {
			user = entUserService.findUserByEmail(email);
			if(user!=null){
				logger.debug("=== user finded===");
			    entUserService.findPassFromMailAndMobile(email,user.getMobile());
			    logger.debug("=== resendPassEmail success===");
			    map.put("result", "success");
				map.put("msg", "用户重置密码邮件发送成功，请到邮件完成密码重置！");
			}else{
				logger.debug("=== resendPassEmail fail===");
				map.put("result", "error");
				map.put("msg", "用户重置密码邮件发送失败！");
			}
		 } catch (Exception e) {
				logger.debug("=== resendPassEmail fail===");
				map.put("result", "error");
				map.put("msg", "重置密码失败！");
		}
		return map;		 
	}
	
	/**
	 * 验证username 和email
	 * @param type
	 * @param value
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validation", method =  {RequestMethod.POST, RequestMethod.GET})
	public String validation(@RequestParam String type,@RequestParam String userId, @RequestParam String value,@RequestParam String callback) {
		User user = null;

		if (StringUtils.isBlank(type)) {
			return jsonp(callback, false);
		}
		user = entUserService.findUser(Long.valueOf(userId));
		if (user == null) {
			// 用户名已存在 返回false;
			return jsonp(callback, false);
		} 
		switch (type) {
		case "oldpassword":
			if(user!=null){
		    	if (StringUtils.equals(user.getPassword(), UserEncrypt.md5hash(value,user.getSalt()))){
					//密码相同
		    		return jsonp(callback, true);
				}else{
					return jsonp(callback, false);
					
				}
	    	}
		}
		return jsonp(callback, true);
	}
	
    /**
     * 前端重复性校验
     * @param type
     * @param value
     * @param request
     * @param callback
     * @return
     */
	@RequestMapping(value = "/validationjson", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String validationJSONP(@RequestParam String type, @RequestParam String value,HttpServletResponse response,
			@RequestParam String callback) {
		 response.setHeader("Access-Control-Allow-Origin", "*");
		User user = null;
		if (StringUtils.isBlank(type)) {
			return jsonp(callback, false);
		}
		switch (type) {
		case "username":
			user = entUserService.findUserByUsername(value);
			if (user != null) {
				// 用户名已存在 返回false;
				return jsonp(callback, false);
			} else {
				return jsonp(callback, true);
			}
		
		case "email":
			user = entUserService.findUserByEmail(value);
			if (user != null) {
				// 用户名已存在 返回false;
				return jsonp(callback, false);
			} else {
				return jsonp(callback, true);
			}
		case "phone":
			user = entUserService.findUserByMobile(value);
			if (user != null) {
				// 用户名已存在 返回false;
				return jsonp(callback, false);
			} else {
				return jsonp(callback, true);
			}
		case "safecode":
			String rand = null;
			try {
				rand = (String) org.apache.shiro.SecurityUtils.getSubject().getSession().getAttribute("rand_admin");
			} catch (Exception e) {
				return jsonp(callback, false);
			}
			if(null!=rand && rand.equals(value)){
				return jsonp(callback, true);
			}else {
				return jsonp(callback, false);
			}
			
		}
		return jsonp(callback, true);
	}

	private String jsonp(String callback, boolean b) {
		return String.format(JSONP, callback, Boolean.toString(b));
	}


}
