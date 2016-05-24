package com.trgis.trmap.qtuser.web.controller;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nimbusds.jose.JOSEException;
import com.trgis.trmap.userauth.jwt.JsonWebTokenService;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;
import com.trgis.trmap.userauth.util.UserEncrypt;

/**
 * @deprecated
 * 
 * 移动端数据接口
 * 
 * 接口移动至移动端项目后台
 * 
 * 
 * 
 * @author zhangqian
 *
 */
@Deprecated
public class MobileUserController {

	private static final Logger logger = LoggerFactory.getLogger(MobileUserController.class);

	@Autowired
	private UserService userService;

	/**
	 * 返回到客户端的json格式
	 */
	private static final String RESULT_JSON = "{\"result\":\"%s\",\"msg\":\"%s\"}";

	private static final String SUCCESS = "success";
	private static final String ERROR = "error";

	/**
	 * 地址：http://192.168.1.111:8080/qtuser/mobile/login
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
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String jwtLogin(@RequestParam String username, @RequestParam String password) {
		logger.debug(String.format("接收到用户%s的登录请求，请求参数：%s", username, password));
		if (StringUtils.isAnyBlank(username, password)) {
			logger.debug("参数错误.返回.");
			return String.format(RESULT_JSON, ERROR, "账号或密码错误");
		}
		String jwt = "";
		User user = userService.findUserByUsername(username);
		if (user == null) {
			logger.debug(String.format("未找到登录用户%s的信息", username));
			return String.format(RESULT_JSON, ERROR, "账号错误");
		}
		logger.debug(String.format("找到登录请求%s用户信息", username));
		logger.debug("开始验证用户请求信息");
		if (StringUtils.equals(user.getPassword(), UserEncrypt.md5hash(password, user.getSalt()))) {
			logger.debug("认证成功");
			try {
				switch (user.getStatus()) {
				case ACTIVE:
					logger.debug("生成用户jwt票据，有效期为一周");
					jwt = JsonWebTokenService.signed(user.getUsername());
					break;
				default:
					jwt = user.getStatus().getMessage();
					logger.debug("用户状态异常：%s", jwt);
					return String.format(RESULT_JSON, ERROR, jwt);
				}
			} catch (NoSuchAlgorithmException | JOSEException e) {
				logger.debug("移动端登录失败");
				return String.format(RESULT_JSON, ERROR, "系统异常，请重试");
			}
		} else {
			logger.debug("认证失败,密码错误.");
			return String.format(RESULT_JSON, ERROR, "密码错误");
		}
		logger.debug("返回用户认证结果.");
		return String.format(RESULT_JSON, SUCCESS, jwt);
	}

	/**
	 * 地址：http://192.168.1.111:8080/qtuser/mobile/jwtflush
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
	@RequestMapping(value = "/jwtflush", method = RequestMethod.POST)
	public String jwtFlush(@RequestParam String oldJwt) {
		if (StringUtils.isAnyBlank(oldJwt)) {
			return "";
		}
		String jwt = "";
		String username = doGetUsername(oldJwt);
		User user = userService.findUserByUsername(username);
		if (user != null)
			try {
				jwt = JsonWebTokenService.signed(user.getUsername());
			} catch (NoSuchAlgorithmException | JOSEException e) {
				logger.debug("移动端登录失败");
			}
		return jwt;
	}

	/**
	 * 地址：http://192.168.1.111:8080/qtuser/mobile/getname
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
}
