package com.trgis.trmap.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.trmap.personal.model.UserTopicTag;
import com.trgis.trmap.personal.service.UserTopicTagService;
import com.trgis.trmap.system.utils.JSONPage;
import com.trgis.trmap.system.utils.MessageConverts;
import com.trgis.trmap.system.utils.Result;

/**
 * 
 * @author majl
 * @date
 * @return
 */
@Controller
@RequestMapping(value = "/topic")
public class UserTopicTagController {
	private static final Logger logger = LoggerFactory.getLogger(UserTopicTagController.class);


	@Autowired
	private UserTopicTagService userTopicTagService; 

	//页面转发 
	@RequestMapping(value= "/topiclist", method=RequestMethod.GET)
	public String topicTagList(){
		return "topic/topiclist";
	}



	//获取所有专题标签
	@RequestMapping(value="/topiclist",method=RequestMethod.POST)
	@ResponseBody
	public JSONPage topicTagList(int rows,int page,String searchVal){
		JSONPage jp = new JSONPage();
		try{
		logger.debug("");
		Page<UserTopicTag> tags = null ;
		tags = userTopicTagService.findAll(page, rows,searchVal);
		if(tags!=null && tags.getContent()!=null && tags.getContent().size()>0){
		jp.setRows(tags==null?new ArrayList<UserTopicTag>():tags.getContent());
		}else{
			List<UserTopicTag> tagList = new ArrayList<UserTopicTag>();
			jp.setRows(tagList);
		}
		jp.setTotal(tags==null?0:(int)tags.getTotalElements());
		}catch(Exception e){
			e.printStackTrace();
		}
		return jp;
	}

	//添加专题标签
	@RequestMapping(value="/addtopic",method=RequestMethod.POST)
	@ResponseBody
	public Result topicAdd(UserTopicTag tag,Result result){
		try{
			userTopicTagService.createEntity(tag);
			result.setStatus(Result.STATUS_OK);
			result.setMsg(MessageConverts.DEFAULT_SUCCESS_MSG);
		}catch(Exception e){
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg(MessageConverts.DEFAULT_FAILED_MSG);
			logger.debug(e.getMessage());
		}
		return result;
	}
	//修改专题标签
	@RequestMapping(value="/modfiedtopic",method=RequestMethod.POST)
	@ResponseBody
	public Result modfyTopic(UserTopicTag tag,Result result,long id){
		try{
	//		tag = userTopicTagService.findOneByEntity(tag);
			userTopicTagService.modifyEntity(tag);
			result.setStatus(Result.STATUS_OK);
			result.setMsg(MessageConverts.DEFAULT_SUCCESS_MSG);
		}catch(Exception e){
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg(MessageConverts.DEFAULT_FAILED_MSG);
			logger.debug(e.getMessage());
		}
		return result;
	}
	//删除专题标签
	@RequestMapping(value="/deletetopic",method=RequestMethod.POST)
	@ResponseBody
	public Result deleteTopic(Long id,Result result){
		try{
			userTopicTagService.removeEntity(id);
			result.setStatus(Result.STATUS_OK);
			result.setMsg(MessageConverts.DEFAULT_SUCCESS_MSG);
		}catch(Exception e){
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg(MessageConverts.DEFAULT_FAILED_MSG);
			logger.debug(e.getMessage());
		}
		return result;
	}

}
