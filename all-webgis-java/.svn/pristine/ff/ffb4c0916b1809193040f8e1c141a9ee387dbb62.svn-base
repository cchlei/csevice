/**
 * 
 */
package com.trgis.trmap.personal.webapp.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.trmap.personal.webapp.utils.BeanUtil;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserEntity;
import com.trgis.trmap.userauth.service.UserService;
import com.trgis.trmap.userauth.util.UserEncrypt;

/**
 * 个人版2.0登录跳转
 * 
 * @author doris
 *
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public ModelAndView userLogin(ModelAndView mv) {
		try {
			String username = (String) SecurityUtils.getSubject().getPrincipal();
			User user = userService.findUserByUsername(username);
			if (BeanUtil.isNotEmpty(user)) {
				switch (user.getStatus()) {
				case ACTIVE:// 正常激活的用户跳转界面
					logger.debug(username + "用户登录成功");
					// 设置用户头像
					String headimg = "";
					if (StringUtils.isNotBlank(user.getHeadimg())) {
						headimg = user.getHeadimg();
					} else {
						headimg = String.format("http://identicon.relucks.org/%s?size=100", user.getUsername());
					}
					if (UserEntity.ENTERPRISE.equals(user.getEntity())) {
						mv.setViewName("redirect:/enterprise.jsp");// 企业账户前往企业中心
					} else {

						/*
						 * EditeBy jger 2016-03-18 18:08:32
						 * 系统统一使用Shiro代理的Session
						 */
						Session session = SecurityUtils.getSubject().getSession();
						// HttpSession session= request.getSession();
						session.setAttribute("username", username);
						session.setAttribute("headimg", headimg);
						mv.setViewName("main");
					}
					break;
				case INACTIVE:// 未激活用户跳转的界面
					logger.debug(username + "用户状态为inactive，不能登录");
					mv.addObject("code", UserEncrypt.QTAES_Encrypt(user.getId().toString()));
					mv.addObject("a", user.getEmail());
					mv.addObject("g", user.getUsername());
					mv.setViewName("redirect:http://a.trmap.cn/inactive.jsp");//前往account未激活页面
					break;
				case LOCKED:// 被锁定的账号
					logger.debug(username + "用户状态为locked，不能登录");
					mv.addObject("status", "locked");
					mv.setViewName("account/locked");
					break;
				case CLOSED:// 已关闭的账号
					logger.debug(username + "用户状态为closed，不能登录");
					mv.addObject("status", "closed");
					mv.setViewName("account/closed");
					break;
				default:
					logger.debug(username + "用户状态未定义，不能登录");
					mv.setViewName("redirect:/logout");
					break;
				}
			} else {
				logger.debug("用户登录失败，查找不到用户");
				mv.setViewName("redirect:/logout");
			}
		} catch (Exception e) {
			logger.debug("用户登录失败", e);
			mv.setViewName("redirect:/logout");
		}
		return mv;
	}

}
