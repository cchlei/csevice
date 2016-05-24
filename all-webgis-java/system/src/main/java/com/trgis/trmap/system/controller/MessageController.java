package com.trgis.trmap.system.controller;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.trmap.personal.model.UserMessage;
import com.trgis.trmap.personal.service.UserMessageService;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.system.utils.MessageConverts;
import com.trgis.trmap.system.utils.Result;
import com.trgis.trmap.system.vo.MessagForm;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;

/**
 * 
 * @author majl
 * @Description 系统消息
 * @data 2016年4月20日
 */
@Controller
@RequestMapping("/message")
public class MessageController {


	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private  UserMessageService userMesageService;

	/**
	 * 页面转发
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String redirectUserList() {
		return "message/message";
	}

	/**
	 * 发送消息
	 * @param title
	 * @param content
	 * @param usernames
	 * @return
	 */
	@RequestMapping(value="/sendMessage",method= RequestMethod.POST)
	@ResponseBody
	public Result sendMessage(MessagForm messageForm,Result result){
		UserMessage userMessage = null;
		ConditionGroup cgRoot = new ConditionGroup();
		try{
			if(messageForm.getUsernames()!=null && messageForm.getUsernames().size()>0){
				cgRoot.setSearchRelation(SearchRelation.OR);// 基本组合关系为OR
				for(String username : messageForm.getUsernames()){
					if(StringUtils.isNotBlank(username))
			cgRoot.getConditions().add(new SearchCondition("username",Operator.EQ, username));
				}
			}
			List<User> users = userService.findAllUser(cgRoot);
			for(User user :users){
				userMessage = new UserMessage();
				userMessage.setMessageTitle(messageForm.getTitle());
				userMessage.setMessageContent(messageForm.getContent());
				userMessage.setUser(user);
				userMessage.setMsgType(EnumUtil.MESSAGE_TYPE.SYSMSG.getValue());
				userMesageService.addMessage(userMessage);
			}
			result.setStatus(Result.STATUS_OK);
			result.setMsg(MessageConverts.DEFAULT_SUCCESS_MSG);
		}catch(Exception e){
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg(MessageConverts.DEFAULT_FAILED_MSG);
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

}
