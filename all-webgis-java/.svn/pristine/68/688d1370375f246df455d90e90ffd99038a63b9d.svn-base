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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
import com.trgis.trmap.account.web.Interceptor.annotation.AccountConfineRegistRequired;
import com.trgis.trmap.personal.model.UserMessage;
import com.trgis.trmap.personal.service.UserMessageService;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.userauth.exception.AccountFindException;
import com.trgis.trmap.userauth.exception.AccountRegisterException;
import com.trgis.trmap.userauth.model.AccountConfine;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.RegistType;
import com.trgis.trmap.userauth.service.AccountConfineService;
import com.trgis.trmap.userauth.service.UserForm;
import com.trgis.trmap.userauth.service.UserService;
import com.trgis.trmap.userauth.util.UserEncrypt;

/**
 * @Description: 个人版注册控制
 * @author 
 *
 */
@Controller
public class PregistController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PregistController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountConfineService accountConfineService;
	
	@Autowired
	private UserMessageService userMessageService;
	@AccountConfineRegistRequired
	@ResponseBody
	@RequestMapping(value = "/p/regist", method = { RequestMethod.POST})
	public ModelAndView personalRegister(@RequestParam(required = false) String callback, String username, String email,
			String password, String validcode,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		UserMessage  message = null;
		if (!validation(validcode)) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("验证码错误");
			mv.addObject(result);
			mv.setViewName("account/pregerror");
			return mv;
		}
		try {
			checkValues(username, email, password, validcode);
			UserForm userForm = new UserForm();
			userForm.setName(username);// 默认情况下用户的名称即为用户名，用户可以在个人中心重新设置
			userForm.setUsername(username);
			userForm.setEmail(email);
			userForm.setTextpassword(password);
			User user = null;
			try {
				user = userService.createUser(userForm);
				userForm.cleanTextPass(); // 处理完后清空用户输入的密码
				
				//进行个人注册流水记录
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String iprecords=request.getRemoteAddr();
				String date=sdf.format(new Date()); 
				
				AccountConfine accountConfine=new AccountConfine();
				accountConfine.setIp(iprecords);
				accountConfine.setRecordTime(date);
				accountConfine.setRegistType(RegistType.REGISTFLAG.PREGIST.getValue());
				accountConfineService.addAccountConfine(accountConfine);
				//发送欢迎信消息
				message =new UserMessage();
				message.setMsgType(EnumUtil.MESSAGE_TYPE.WELCONME.getValue());
				message.setMessageTitle("欢迎您！");
				message.setUser(user);
				message.setMessageContent("欢迎加入天润云地图，我们将时刻注意您的动态确保为您及时提供所需服务!");
				userMessageService.addMessage(message);
				logger.debug("===send welcome message  ===");
			} catch (AccountRegisterException e) {
				result.setStatus(Result.STATUS_FAILED);
				result.setMsg("e.getMessage()");
				mv.addObject(result);
				mv.setViewName("account/pregerror");
				return mv;
			}
			try {
				userService.sendUserActiveEmail(user);
			} catch (Exception e) {
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
					throw new RuntimeException("系统错误，处理用户信息失败");
				}
			}
			try {
				mv.addObject("userId", UserEncrypt.QTAES_Encrypt(user.getId().toString()));
			} catch (Exception e) {
				throw new RuntimeException("系统错误，处理用户信息失败");
			}
			mv.addObject("user", userForm);
			result.setStatus(Result.STATUS_OK);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg(e.getMessage());
			mv.addObject(result);
			mv.setViewName("account/pregerror");
			return mv;
		}
		mv.addObject(result);
		mv.setViewName("account/pregsucc");
		return mv;
	}
	
	@RequestMapping(value = "/p/validate", method = RequestMethod.GET)
	@ResponseBody
	public String validationJSONP(@RequestParam String type, @RequestParam String value,
			@RequestParam String callback) {
		User user = null;
		Result result = new Result();
		if (StringUtils.isBlank(type)) {
			result.setStatus(Result.STATUS_FAILED);
			return JSONPResult.jsonp(callback, result);
		}
		switch (type) {
		case "username":
			user = userService.findUserByUsername(value);
			if (user != null) {
				// 用户名已存在 返回false;
				result.setStatus(Result.STATUS_FAILED);
				return JSONPResult.jsonp(callback, result);
			} 
			break;
		case "email":
			user = userService.findUserByEmail(value);
			if (user != null) {
				// 用户名已存在 返回false;
				result.setStatus(Result.STATUS_FAILED);
				return JSONPResult.jsonp(callback, result);
			}
			break;
		case "validcode":
			if(!validation(value)){
				result.setStatus(Result.STATUS_FAILED);
				return JSONPResult.jsonp(callback, result);
			}
			break;
		}
		result.setStatus(Result.STATUS_OK);
		return JSONPResult.jsonp(callback, result);
	}
	/**
     * 用户信息维护（修改，目前没有界面）
     * @param id
     * @param username  用户名
     * @param enterprisename  企业名
     * @param phone   电话
     * @param url  头像
     * @return
     */
	 /**
	    * 发送重置邮箱邮件---------------------------------
	    * @param id 
	    * @param email
	    * @return
	    */
		@RequestMapping(value = "/p/toupdate",method ={RequestMethod.POST, RequestMethod.GET})
		public ModelAndView Toupdate() throws ServletException {
			ModelAndView mv = new ModelAndView();
			logger.debug("去修改页面");
			String username = (String) SecurityUtils.getSubject().getPrincipal();
			User user = userService.findUserByUsername(username);
			if(user== null){
				return new ModelAndView("redirect:http://www.trmap.cn");
			}else{
				mv.addObject(user);
			    mv.setViewName("account/user_profile");}
			return mv;
		}
	@ResponseBody
	@RequestMapping(value = "/p/update", method ={RequestMethod.POST, RequestMethod.GET})
	public Result doUpdate(@RequestParam String id,  String phone,  String url,  String ep_name,String user_sex) {
		 Result result = new Result();
		try {
			//根据id获取对象
			User user = userService.findUser(Long.valueOf(id));
			logger.debug("=== do update===");
			if(url!=null && !url.isEmpty() && !url.equals(user.getHeadimg())){//头像修改
				logger.debug("===  update  heading===");
		        user.setHeadimg(url);
		        }
	        if(phone!=null && !phone.isEmpty() && !phone.equals(user.getMobile())){//电话修改
		       logger.debug("===  update  mobile===");
		       if(userService.findUserByMobile(phone)==null){
		        	user.setMobile(phone);
		        }else{
		        	result.setStatus(Result.STATUS_FAILED);
					result.setMsg("电话号码已存在");
		        }
	          }
	    
	       
	        if( ep_name!=null && !ep_name.isEmpty() && !ep_name.equals(user.getName())){//企业名修改
	        	logger.debug("===  update  name===");
		        	if(userService.findUserByname(ep_name)==null){
		        		logger.debug("===  update username===");
		        		user.setName(ep_name);
		        	}else{
		        		result.setStatus(Result.STATUS_FAILED);
						result.setMsg("该企业名称已存在");
		        	}
		        }
	        if( user_sex!=null && !user_sex.isEmpty() && !user_sex.equals(user.getGender())){//性别修改
	        	logger.debug("===  update  sex===");
	        	user.setGender(user_sex);
	        }
	        
	    	UserForm userForm = new UserForm();
	    	BeanUtils.copyProperties(userForm,user);
	    	userService.editUser(Long.valueOf(id), userForm);
	        result.setStatus(Result.STATUS_OK);
			result.setMsg("修改用户信息成功！");
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("修改用户信息失败！");
		}
		return result;
	}
	/**
     * 修改密码
     * @param id
     * @param oldpassword
     * @param newpassword
     * @return
     */
	@ResponseBody
	@RequestMapping(value ="/p/changepass", method ={RequestMethod.POST,RequestMethod.GET})
	public Result doUppass( @RequestParam String id, @RequestParam String oldpassword,@RequestParam String newpassword) {
		 Result result = new Result();
			//根据id获取对象
			User user = userService.findUser(Long.valueOf(id));
			try {
				checkValues(oldpassword,newpassword);//是否为空
			} catch (Exception e) {
				result.setStatus(Result.STATUS_FAILED);
				result.setMsg("输入不能为空！");
			}
			if(user!=null){
		    	try {
					logger.debug("=== do changePass===");
					userService.changePass(Long.valueOf(id), oldpassword, newpassword);
					logger.debug("=== changePass success===");
					result.setStatus(Result.STATUS_OK);//密码修改成功
		        } catch (AccountFindException e) {
					logger.debug("=== changePass fail===");
					result.setStatus(Result.STATUS_FAILED);//密码修改失败
					if (e.getMessage().equals("old password is incorrect")) {
						result.setMsg("旧密码错误！");
					}else{
						result.setMsg("修改密码失败！");
					}
	           	}
		   }else{
			       result.setStatus(Result.STATUS_FAILED);//密码修改失败
			       result.setMsg("服务器异常，请重试");
		   }
	      return result;
	}
	 /**
	    * 发送重置邮箱邮件---------------------------------
	    * @param id 
	    * @param email
	    * @return
	    */
		@ResponseBody
		@RequestMapping(value = "/p/sendmail", method = RequestMethod.POST)
		public Result SendEmail(String id,String email) {
			logger.debug("重置邮箱邮件");
			Result result = new Result();
			User user = userService.findUser(Long.valueOf(id));
			user.setEmail(email);
			try {
				userService.sendresetEmail(user);
				result.setStatus(Result.STATUS_OK);
				result.setMsg("邮件已发送成功！");
			} catch (Exception e) {
				result.setStatus(Result.STATUS_FAILED);
				result.setMsg("邮件未发送成功！请重新发送！");
				return result;
			}
			return result;
		}
	/**
     * 修改邮箱
     * @param code
     * @param email  邮箱
     * @return
     */	
	@RequestMapping(value = "/p/upemail", method ={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView doUpdate(@RequestParam String code,@RequestParam String email) {
		 ModelAndView mv = new ModelAndView();
		 logger.debug("===  update  email===");
		 Result result = new Result();
		 User user =new User();
		try {
			//获取当前对象
			String username = (String) SecurityUtils.getSubject().getPrincipal();
			user = userService.findUserByUsername(username);
			
	        if(!email.equals(user.getEmail())){//邮件修改
	        	if(userService.findUserByEmail(email)==null){
	        		user.setEmail(email);
	        	    userService.resetemail(user.getId(), email, code);//修改邮件
	      	        result.setStatus(Result.STATUS_OK);
	      			mv.setViewName("account/emailsucc");
	      			logger.debug("===  update  email succ===");
	        	}else{
	        		result.setStatus(Result.STATUS_FAILED);
					result.setMsg("邮箱已存在");
					mv.setViewName("account/emailerr");
	        	}
	        }
	      
		} catch (Exception e) {
			logger.debug("===  update  email failed===");
			result.setStatus(Result.STATUS_FAILED);
			mv.setViewName("account/emailerr");
			result.setMsg("修改邮箱失败");
		}   
	     	mv.addObject(user);
	    	mv.addObject(result);
		return mv;
	}
	
	/**
	 * 验证oldpassword 和email
	 * @param type
	 * @param value
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/p/check", method =  {RequestMethod.POST, RequestMethod.GET})
	public String validation(@RequestParam String type,@RequestParam String id, @RequestParam String value,@RequestParam String callback) {
		User user = null;
		Result result = new Result();
		if (StringUtils.isBlank(type)) {
			result.setStatus(Result.STATUS_FAILED);
			return JSONPResult.jsonp(callback, result);
		}
		user = userService.findUser(Long.valueOf(id));
		if (user == null) {
			result.setStatus(Result.STATUS_FAILED);
			return JSONPResult.jsonp(callback, result);
		} 
		switch (type) {
		case "oldpassword":
			if(user!=null){
		    	if (StringUtils.equals(user.getPassword(), UserEncrypt.md5hash(value,user.getSalt()))){
					//密码相同
		    		result.setStatus(Result.STATUS_OK);
					return JSONPResult.jsonp(callback, result);
				}else{
					result.setStatus(Result.STATUS_FAILED);
					return JSONPResult.jsonp(callback, result);
					
				}
	    	}
		case "email":
			if(user!=null){
		    	if (StringUtils.equals(user.getEmail(),value)){
					//邮箱相同
		    		result.setStatus(Result.STATUS_OK);
					return JSONPResult.jsonp(callback, result);
				}else{
					result.setStatus(Result.STATUS_FAILED);
					return JSONPResult.jsonp(callback, result);
					
				}
	    	}
			
		}
		return JSONPResult.jsonp(callback, result);
	}
}


