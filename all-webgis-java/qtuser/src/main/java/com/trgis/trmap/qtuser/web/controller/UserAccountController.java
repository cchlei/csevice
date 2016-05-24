/**
 * 
 */
package com.trgis.trmap.qtuser.web.controller;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.trmap.userauth.exception.AccountRegisterException;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserForm;
import com.trgis.trmap.userauth.service.UserService;
import com.trgis.trmap.userauth.util.UserEncrypt;

/**
 * 用户账户控制器
 * 
 * 处理账号问题，暴露给web端
 * 
 * @author zhangqian
 *
 */
@Controller
@RequestMapping(value = "/account")
public class UserAccountController {

	private static final Logger logger = LoggerFactory.getLogger(UserAccountController.class);

	private static final String JSONP = "%s(%s)";

	@Autowired
	private UserService userService;

	/**
	 * @deprecated 使用了新的注册界面.
	 * 
	 *             http://www.trmap.cn/register.html
	 */
	@Deprecated
	// @RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		logger.debug("===redirect to register===");
		return "account/register";
	}

	@RequestMapping(value = "/register", method ={ RequestMethod.POST,RequestMethod.GET})
	public ModelAndView doRegister(@RequestParam String username, @RequestParam String email,
			@RequestParam String password) {
		logger.debug("===do register===");
		ModelAndView mv = new ModelAndView();
		UserForm userForm = new UserForm();
		userForm.setName(username);// 默认情况下用户的名称即为用户名，用户可以在个人中心重新设置
		userForm.setUsername(username);
		userForm.setEmail(email);
		userForm.setTextpassword(password);
		User user = null;
		try {
			user = userService.createUser(userForm);
			userForm.cleanTextPass(); // 处理完后清空用户输入的密码
		} catch (AccountRegisterException e) {
			mv.addObject("msg", e.getMessage());
			mv.setViewName("account/reg_fail");
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
			mv.addObject("user", userForm);
			mv.setViewName("account/regsuccess");
			return mv;
		}
		try {
			mv.addObject("userId", UserEncrypt.QTAES_Encrypt(user.getId().toString()));
		} catch (Exception e) {
			throw new RuntimeException("系统错误，处理用户信息失败");
		}
		mv.addObject("user", userForm);
		mv.setViewName("account/regsuccess");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/resendmail", method = RequestMethod.POST)
	public Map<String, Object> reSendEmail(@RequestParam String userId) {
		logger.debug("重发激活邮件");
		Map<String, Object> map = new HashMap<String, Object>();
		// 解密
		String userIdEnc = "-1l";
		try {
			userIdEnc = UserEncrypt.QTAES_Decrypt(userId);
		} catch (Exception e1) {
			throw new RuntimeException("系统错误，处理用户信息失败");
		}
		User user = userService.findUser(Long.valueOf(userIdEnc));
		try {
			userService.sendUserActiveEmail(user);
			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "error");
			return map;
		}
		return map;
	}

	@RequestMapping(value = "/active", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView accountActive(@RequestParam String email, @RequestParam String code) {
		logger.debug("收到邮箱激活请求.From:" + email);
		ModelAndView mv = new ModelAndView();
		User user = userService.activeUser(email, code);
		mv.addObject("username", user.getUsername());
		mv.setViewName("account/activesuccess");
		return mv;
	}

	@RequestMapping(value = "/validation", method = RequestMethod.POST)
	@ResponseBody
	public boolean validation(@RequestParam String type, @RequestParam String value) {
		User user = null;

		if (StringUtils.isBlank(type)) {
			return false;
		}

		switch (type) {
		case "username":
			user = userService.findUserByUsername(value);
			if (user != null) {
				// 用户名已存在 返回false;
				return false;
			} else {
				return true;
			}
		case "email":
			user = userService.findUserByEmail(value);
			if (user != null) {
				// 用户名已存在 返回false;
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	@RequestMapping(value = "/validation", method = RequestMethod.GET)
	@ResponseBody
	public String validationJSONP(@RequestParam String type, @RequestParam String value,
			@RequestParam String callback) {
		User user = null;
		if (StringUtils.isBlank(type)) {
			return jsonp(callback, false);
		}
		switch (type) {
		case "username":
			user = userService.findUserByUsername(value);
			if (user != null) {
				// 用户名已存在 返回false;
				return jsonp(callback, false);
			} else {
				return jsonp(callback, true);
			}
		case "email":
			user = userService.findUserByEmail(value);
			if (user != null) {
				// 用户名已存在 返回false;
				return jsonp(callback, false);
			} else {
				return jsonp(callback, true);
			}
		}
		return jsonp(callback, true);
	}

	private String jsonp(String callback, boolean b) {
		return String.format(JSONP, callback, Boolean.toString(b));
	}

}
