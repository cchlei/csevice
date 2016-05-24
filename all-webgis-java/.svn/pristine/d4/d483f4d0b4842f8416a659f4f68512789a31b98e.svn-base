package com.trgis.trmap.personal.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.trmap.personal.exception.UserAttachException;
import com.trgis.trmap.personal.exception.UserIntegralException;
import com.trgis.trmap.personal.exception.UserTopicException;
import com.trgis.trmap.personal.model.UserIntegral;
import com.trgis.trmap.personal.service.UserAttachService;
import com.trgis.trmap.personal.service.UserIntegralService;
import com.trgis.trmap.personal.service.UserTopicService;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;

/**
 * @ClassName: AccountController
 * @Description: 账户
 * @author yanpeng
 * @date 2016年3月28日 上午11:10:43
 */
@Controller
@RequestMapping("/account")
public class IntegralController {

	@Autowired
	private UserAttachService userAttachService;
	@Autowired
	private UserTopicService userTopicService;
	@Autowired
	private UserIntegralService userIntegralService;
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(IntegralController.class);
	
	/**
	 * @Description: 获得个人统计信息
	 * @author yanpeng
	 * @date 2016年4月13日 下午2:53:38
	 * @param modelAndView
	 * @return
	 */
    @ResponseBody
	@RequestMapping("info")
	public ModelAndView editGroup(ModelAndView modelAndView){
    	User user = getUserBean();
    	Map<String,Object> map = new HashMap<String,Object>();
    	try {
			UserIntegral userIntegral = userIntegralService.getUserIntegral(user);
			map.put("defaultSpaceSize", userIntegral.getSpaceSize());
			map.put("topicCount", userIntegral.getTopicCount());
			map.put("grade", userIntegral.getGrade());
			map.put("name", user.getUsername());
			logger.debug("用户等级信息查询成功");
		} catch (UserIntegralException e) {
			logger.debug("用户等级信息查询失败");
			e.printStackTrace();
		}
    	try {
    		Double spaceSize = userAttachService.getSpaceSize(user);
    		spaceSize = spaceSize == null ? 0 : spaceSize;
			map.put("spaceSize", spaceSize);
			logger.debug("用户空间统计成功");
		} catch (UserAttachException e) {
			e.printStackTrace();
			logger.debug("用户空间统计失败");
		}
		try {
			Map<String, Long> topicCount = userTopicService.getTopicCount(user.getId());
			map.put("yfx", topicCount.get("yfx"));
			map.put("wfx", topicCount.get("wfx"));
			map.put("all", topicCount.get("all"));
		} catch (UserTopicException e) {
			e.printStackTrace();
		}
		modelAndView.setViewName("account/account");
		modelAndView.addObject("account", map);
    	return modelAndView;
    }
    
    @ResponseBody
   	@RequestMapping("page")
   	public ModelAndView toPage(ModelAndView modelAndView){
    	modelAndView.setViewName("account_index");
    	return modelAndView;
    }
    	
	/**
	 * @Description: 获取当前登录用户
	 * @author yanpeng
	 * @date 2016年3月28日 下午2:01:40
	 * @return
	 */
	private User getUserBean() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		return userService.findUserByUsername(username);
	}
}
