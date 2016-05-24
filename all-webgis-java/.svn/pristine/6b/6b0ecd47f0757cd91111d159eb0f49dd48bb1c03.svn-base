package com.trgis.trmap.personal.mobile.controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import com.trgis.resource.client.OssService;
import com.trgis.trmap.personal.mobile.controller.result.ActionResult;
import com.trgis.trmap.personal.mobile.controller.result.STATUSCODE;
import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.qtuser.service.UserMapService;
import com.trgis.trmap.userauth.exception.AccountRegisterException;
import com.trgis.trmap.userauth.jwt.JsonWebTokenService;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserStatus;
import com.trgis.trmap.userauth.service.UserForm;
import com.trgis.trmap.userauth.service.UserService;
import com.trgis.trmap.userauth.util.UserEncrypt;

/**
 * 移动端数据接口
 * 
 * @author zhangqian
 *
 */
@RestController
@RequestMapping(value = "/mobile")
public class MobileUserController {

	private static final Logger logger = LoggerFactory.getLogger(MobileUserController.class);

	@Autowired
	private OssService ossService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserMapService userMapService;

	/**
	 * 地址：http://192.168.1.111:8080/mobile/login
	 * 
	 * 接收请求:POST
	 * 
	 * 移动端登录接口
	 * 
	 * @param username
	 *            账号
	 * @param password
	 *            密码
	 * 
	 * @return 返回JWT票
	 * 
	 *         返回值说明 :
	 * 
	 *         error 参数错误 error_user 用户名错误 error_pass 密码错误
	 * 
	 *         inactive 账号未激活 locked 账号已锁定 closed 账号已冻结
	 * 
	 *         jwt票据
	 */
	@RequestMapping(value = "/login")
	public ActionResult jwtLogin(@RequestParam String username, @RequestParam String password) {
		logger.debug("->接收到用户[{}]的登录请求", username);
		ActionResult result = new ActionResult();
		if (StringUtils.isAnyBlank(username, password)) {
			result.set(STATUSCODE.AUTHCATIONINFO_ANYBLANK_ERROR, "账号或密码不能为空");
			logger.debug("<-[{}]参数错误.", STATUSCODE.AUTHCATIONINFO_ANYBLANK_ERROR);
			return result;
		}
		String jwt = "";
		User user = userService.findUserByUsername(username);
		if (user == null) {
			result.set(STATUSCODE.AUTHCATIONINFO_USERNAME_NOTEXIST, "账号不存在");
			logger.debug("<-[{}]账号[{}]信息不存在.", STATUSCODE.AUTHCATIONINFO_ANYBLANK_ERROR, username);
			return result;
		}
		logger.debug("--[{}]找到用户[{}]信息.", STATUSCODE.SUCCESS, username);
		if (StringUtils.equals(user.getPassword(), UserEncrypt.md5hash(password, user.getSalt()))) {
			logger.debug("--认证成功.", STATUSCODE.SUCCESS);
			try {
				switch (user.getStatus()) {
				case ACTIVE:
					logger.debug("<-生成用户jwt票据，有效期为一周");
					jwt = JsonWebTokenService.signed(user.getUsername());
					result.set(STATUSCODE.SUCCESS, "ok", jwt);
					break;
				default:
					jwt = user.getStatus().getMessage();
					logger.debug("<-[{}]用户状态不可用:{}.", STATUSCODE.AUTHCATION_STATUS_ERROR, jwt);
					result.set(STATUSCODE.AUTHCATION_STATUS_ERROR, "用户状态不可用.");
				}
				return result;
			} catch (NoSuchAlgorithmException | JOSEException e) {
				logger.error("登录失败.",e);
				result.set(STATUSCODE.SYSTEM_ERROR, "系统运行时异常,请重试.");
			}
		} else {
			result.set(STATUSCODE.AUTHCATIONINFO_PASSWORD_ERROR, "密码错误.");
			logger.debug("<-[{}]账号[{}]信息不存在.", STATUSCODE.AUTHCATIONINFO_ANYBLANK_ERROR, username);
			return result;
		}
		return result;
	}

	/**
	 * 地址：http://192.168.1.111:8080/mobile/jwtflush
	 * 
	 * 接收请求:POST
	 * 
	 * 移动端刷新票据接口
	 * 
	 * @param jwt
	 *            jwt票据
	 * 
	 * @return 返回新的JWT票
	 */
	@ResponseBody
	@RequestMapping(value = "/jwtflush")
	public String jwtFlush(@RequestParam String oldJwt) {
		if (StringUtils.isAnyBlank(oldJwt)) {
			return "";
		}
		String jwt = "";
		String username = doGetUsername(oldJwt);
		if (StringUtils.isNotBlank(username))
			try {
				jwt = JsonWebTokenService.signed(username);
			} catch (NoSuchAlgorithmException | JOSEException e) {
				logger.debug("移动端登录失败");
			}
		return jwt;
	}

	/**
	 * 地址：http://192.168.1.111:8080/mobile/getname
	 * 
	 * 接收请求:POST
	 * 
	 * 获取用户名
	 * 
	 * @param jwt
	 *            jwt票据
	 * 
	 * @return 用户昵称
	 */
	@ResponseBody
	@RequestMapping(value = "/getname", method = RequestMethod.POST)
	public String getName(@RequestParam String jwt) {
		String username = doGetUsername(jwt);
		if (StringUtils.isBlank(username)) {
			return "";
		}
		User user = userService.findUserByUsername(username);
		if (user == null) {
			return "";
		}
		return user.getName();
	}

	/**
	 * 用户注册 访问地址：http://localhost:8080/mobile/register userForm包含属性 username 账号
	 * textpassword 密码 name 名称 nickname 昵称 gender 性别 mobile 电话 email 邮箱
	 * 
	 * @Title: userRegister @Description: 用户注册 @param userForm @return 设定文件
	 *         String 返回类型 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> userRegister(UserForm userForm) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user;
		try {
			user = userService.createUser(userForm);
			userService.changeUserStatus(user.getId(), UserStatus.ACTIVE);// 默认注册完就是激活状态
			if (user != null) {
				// 注册成功是需要添加一个移动端默认图层
				UserMap usermap = new UserMap();
				usermap.setUser(user);
				usermap.setMapname("移动端");
				usermap.setApp(true);
				userMapService.createUserMap(usermap);
				logger.debug("移动端图层添加成功");
				result.put("result", "success");
				result.put("msg", "注册成功");
			}
			userForm.cleanTextPass(); // 处理完后清空用户输入的密码
			// TODO 开发jms邮件服务总线 业务系统不独立处理邮件发送
			logger.debug(user.toString());
		} catch (AccountRegisterException e) {
			logger.error("user register error [" + e.getMessage() + "]", e);
			result.put("result", "error");
			result.put("msg", "注册失败!");
		}
		return result;
	}

	/*
	 * 获取用户名
	 */
	private String doGetUsername(String jwt) {
		if (StringUtils.isAnyBlank(jwt)) {
			return "";
		}
		String username = JsonWebTokenService.getUsername(jwt);
		if (StringUtils.isBlank(username)) {
			return "";
		}
		User user = userService.findUserByUsername(username);
		if (user != null) {
			return user.getUsername();
		}
		return "";
	}

	/**
	 * 查看个人信息
	 * 
	 * 访问地址：http://localhost:8080/mobile/userinfo?jwt=M
	 * 
	 * @param jwt
	 *            当前用户成功登录票据
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userinfo")
	public Map<String, Object> userinfo(String jwt) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SignedJWT parseJWT = SignedJWT.parse(jwt);
			// 校验jwt
			if (!JsonWebTokenService.checkDomainAndTime(parseJWT)) {
				logger.debug("[JWT]失效");
				result.put("result", "error");
				result.put("msg", "jwt失效");
				return result;
			} else {
				String username = JsonWebTokenService.getUsername(jwt);
				logger.debug(String.format("查询用户名为：%s的用户信息", username));
				User user = userService.findUserByUsername(username);
				// 简化成User数据结构返回
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", user.getId() + "");
				map.put("username", user.getUsername() == null ? "" : user.getUsername());
				map.put("nickname", user.getNickname() == null ? "" : user.getNickname());
				map.put("name", user.getName() == null ? "" : user.getName());
				map.put("gender", user.getGender() == null ? "" : user.getGender());
				map.put("headimg", user.getHeadimg() == null ? "" : user.getHeadimg());
				map.put("email", user.getEmail() == null ? "" : user.getEmail());
				map.put("mobile", user.getMobile() == null ? "" : user.getMobile());
				result.put("result", "success");
				result.put("msg", map);
				return result;
			}
		} catch (ParseException e) {
			logger.debug("[JWT]出错", e);
			result.put("result", "error");
			result.put("msg", "jwt出错");
			return result;
		}
	}

	/**
	 * 编辑个人信息
	 * 
	 * 访问地址：http://localhost:8080/mobile/editUser?jwt=M&key=name&value=123
	 * 
	 * @param jwt
	 *            当前用户成功登录票据
	 * @param key
	 *            修改的字段 key可能的值为：nickname，name，gender，mobile
	 * @param value
	 *            修改的值
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editUser")
	public Map<String, Object> editUser(String jwt, String key, String value) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SignedJWT parseJWT = SignedJWT.parse(jwt);
			// 校验jwt
			if (!JsonWebTokenService.checkDomainAndTime(parseJWT)) {
				logger.debug("[JWT]失效");
				result.put("result", "error");
				result.put("msg", "jwt失效");
				return result;
			} else {
				String username = JsonWebTokenService.getUsername(jwt);
				logger.debug(String.format("修改用户名为：%s的用户信息", username));
				User user = userService.findUserByUsername(username);
				UserForm userform = new UserForm();
				if ("nickname".equals(key)) {
					userform.setNickname(value);
					userform.setName(user.getName());
					userform.setGender(user.getGender());
					userform.setMobile(user.getMobile());
				} else if ("name".equals(key)) {
					userform.setName(value);
					userform.setNickname(user.getNickname());
					userform.setGender(user.getGender());
					userform.setMobile(user.getMobile());
				} else if ("gender".equals(key)) {
					userform.setGender(value);
					userform.setName(user.getName());
					userform.setNickname(user.getNickname());
					userform.setMobile(user.getMobile());
				} else if ("mobile".equals(key)) {
					userform.setMobile(value);
					userform.setName(user.getName());
					userform.setNickname(user.getNickname());
					userform.setGender(user.getGender());
				} else {
					logger.debug("修改属性不合法" + key);
					result.put("result", "error");
					result.put("msg", "修改属性不合法");
					return result;
				}
				userform.setHeadimg(user.getHeadimg());
				userform.setEmail(user.getEmail());
				userform.setTextpassword(user.getPassword());
				userform.setUsername(username);
				userService.editUser(user.getId(), userform);
				result.put("result", "success");
				result.put("msg", "修改成功");
				return result;
			}
		} catch (ParseException e) {
			logger.debug("[JWT]出错", e);
			result.put("result", "error");
			result.put("msg", "jwt出错");
			return result;
		} catch (Exception e) {
			logger.debug("修改用户信息出错", e);
			result.put("result", "error");
			result.put("msg", "修改用户信息失败");
			return result;
		}
	}

	/**
	 * 修改头像
	 * 
	 * 访问地址：http://localhost:8080/mobile/editHeadimg?jwt=M
	 * 
	 * @param jwt
	 *            当前用户成功登录票据
	 * @param file
	 *            图片信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editHeadimg")
	public Map<String, Object> editHeadimg(String jwt, MultipartFile file) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SignedJWT parseJWT = SignedJWT.parse(jwt);
			// 校验jwt
			if (!JsonWebTokenService.checkDomainAndTime(parseJWT)) {
				logger.debug("[JWT]失效");
				result.put("result", "error");
				result.put("msg", "jwt失效");
				return result;
			} else {
				String username = JsonWebTokenService.getUsername(jwt);
				logger.debug(String.format("修改用户名为：%s的用户信息", username));
				User user = userService.findUserByUsername(username);
				UserForm userform = new UserForm();
				if (file != null) {
					// 上传文件到服务器
					String id = ossService.uploadFile(file.getInputStream(), file.getOriginalFilename(), "qtmap");
					userform.setHeadimg(id);
				} else {
					result.put("result", "error");
					result.put("msg", "图片为空");
					return result;
				}
				userform.setEmail(user.getEmail());
				userform.setGender(user.getGender());
				userform.setMobile(user.getMobile());
				userform.setName(user.getName());
				userform.setNickname(user.getNickname());
				userform.setTextpassword(user.getPassword());
				userform.setUsername(username);
				userService.editUser(user.getId(), userform);
				result.put("result", "success");
				result.put("msg", "修改成功");
				return result;
			}
		} catch (ParseException e) {
			logger.debug("[JWT]出错", e);
			result.put("result", "error");
			result.put("msg", "jwt出错");
			return result;
		} catch (Exception e) {
			logger.debug("修改用户头像出错", e);
			result.put("result", "error");
			result.put("msg", "修改用户头像失败");
			return result;
		}
	}

	/**
	 * 修改密码
	 * 
	 * 访问地址：http://localhost:8080/mobile/modify?jwt=M
	 * 
	 * @param oldPadd
	 *            原密码
	 * @param newPass
	 *            新密码
	 * @param jwt
	 *            当前用户成功登录票据
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modify")
	public Map<String, Object> modify(String jwt, String oldPass, String newPass) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SignedJWT parseJWT = SignedJWT.parse(jwt);
			// 校验jwt
			if (!JsonWebTokenService.checkDomainAndTime(parseJWT)) {
				logger.debug("[JWT]失效");
				result.put("result", "error");
				result.put("msg", "jwt失效");
				return result;
			} else {
				String username = JsonWebTokenService.getUsername(jwt);
				logger.debug(String.format("修改用户名为：%s的用户信息", username));
				User user = userService.findUserByUsername(username);
				userService.changePass(user.getId(), oldPass, newPass);
				result.put("result", "success");
				result.put("msg", "修改成功");
				return result;
			}
		} catch (ParseException e) {
			logger.debug("[JWT]出错", e);
			result.put("result", "error");
			result.put("msg", "jwt出错");
			return result;
		} catch (Exception e) {
			logger.debug("修改密码出错", e);
			result.put("result", "error");
			result.put("msg", "修改密码失败");
			return result;
		}
	}

	/**
	 * 忘记密码 访问地址：http://localhost:8080/mobile/forgot?email=12345@qq.com
	 * 
	 * @param email
	 *            忘记密码的邮箱
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/forgot")
	public Map<String, Object> resendPassEmail(String email, HttpServletResponse response) {
		User user = null;
		logger.debug("=== resendPassEmail===");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			user = userService.findUserByEmail(email);
			if (user != null) {
				logger.debug("=== user finded===");
				userService.findPassFromMailAndMobile(email, user.getMobile());
				logger.debug("=== resendPassEmail success===");
				map.put("result", "success");
				map.put("msg", "用户重置密码邮件发送成功，请到邮件完成密码重置！");
			} else {
				logger.debug("=== resendPassEmail fail===");
				map.put("result", "error");
				map.put("msg", "用户重置密码邮件发送失败！");
			}
		} catch (Exception e) {
			logger.debug("=== resendPassEmail fail===");
			map.put("result", "error");
			map.put("msg", "重置密码邮件发送失败！");
		}
		return map;
	}
}
