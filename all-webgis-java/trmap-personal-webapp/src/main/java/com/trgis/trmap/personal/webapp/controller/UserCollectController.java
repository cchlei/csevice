/**
 * Copyright © 2016陕西天润有限责任公司. All rights reserved.
 *
 * @Title: UserCollectController.java
 * @Prject: trmap-personal-webapp
 * @Package: com.trgis.trmap.personal.webapp.controller
 * @Description: TODO
 * @author: chlei  
 * @date: 2016年3月9日 下午2:27:42
 * @version: V1.0  
 */
package com.trgis.trmap.personal.webapp.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.common.util.CollectionUtils;
import com.trgis.trmap.personal.exception.UserTopicException;
import com.trgis.trmap.personal.model.UserCollect;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.personal.service.UserCollectService;
import com.trgis.trmap.personal.service.UserRecordService;
import com.trgis.trmap.personal.service.UserTopicService;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.personal.webapp.utils.BeanUtil;
import com.trgis.trmap.personal.webapp.utils.Result;
import com.trgis.trmap.personal.webapp.vo.UserCollectVo;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;
/**
 * @ClassName: UserCollectController
 * @Description:个人用户收藏控制器
 * @author: chlei
 * @date: 2016-03-09 2:27:42
 */
@Controller
@RequestMapping(value = "/collect")
public class UserCollectController {

	
	@Autowired
	private UserTopicService userTopicService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserCollectService userCollectService;
	@Autowired
	private UserRecordService userRecordService;
	
	@RequestMapping("/collectview")
	public ModelAndView initMap() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("topic/theme_collect");
		return mv;
	}
	
	/**
	 * @Description:去添加的页面
	 * @Author liuyan 
	 * @Date 2016年3月10日
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/toadd", method ={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toAdd(ModelAndView mv){
		mv.setViewName("/topic/addsubject");
		return mv;
		
	}
	
	/**
	 * @Description:去修改的页面
	 * @Author liuyan 
	 * @Date 2016年3月10日
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/toedit", method ={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toEdit(ModelAndView mv){
		UserTopic usertopic = null;
		try {
			usertopic = userTopicService.findUserTopicById(16080l);
		} catch (UserTopicException e) {
			e.printStackTrace();
		}
		mv.addObject("userTopic",usertopic);
		mv.setViewName("/topic/theme_edit");
		return mv;
		
	}
	
	
    /**
     * 个人收藏分页列表
     * shareflag 分享状态 0 为未分享 1已分享
     * @author chlei
     * @return
     * @throws ParseException 
     * @param 	"total":12,
  				"totalCount":180,
		  		"rows":[
		    		{
				      "id":"1",
				      "title":"四川九寨沟旅游记录",
				      "images":"../images/illu1.png",
				      "headimg":"../images/header.png",
				      "name":"被风吹过的夏天",
				      "collect":"5",
				      "color":"#2bba8d",
				      "iscollect":true
		    		},
     */
	@ResponseBody
    @RequestMapping(value = "/List", method={RequestMethod.POST,RequestMethod.GET})
    public Map<String,Object> userTopicList(@RequestParam("rows")Integer rows, @RequestParam("page")Integer page,Integer shareflag,HttpServletRequest request) throws ParseException {
    	Map<String,Object> map = new HashMap<String,Object>();
    	//查询条件
    	ConditionGroup group = new ConditionGroup();
    	group.setSearchRelation(SearchRelation.AND);
    	
    	List<SearchCondition> conditions = new ArrayList<SearchCondition>();
    	String pageNoStr = request.getParameter("page");
    	int pageNo = pageNoStr==null?0:Integer.parseInt(pageNoStr);
    	String rowsStr = request.getParameter("rows");
        int pageSize = pageNoStr==null?24:Integer.parseInt(rowsStr);
        
		//用户
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userService.findUserByUsername(username);
	    conditions.add(new SearchCondition("user.id", Operator.EQ, user.getId()));
		group.setConditions(conditions);
		//排序
		String timeindex = request.getParameter("timeindex");//时间排序
		OrderBy  order = timeindex==null? new OrderBy("creatime", "desc"):new OrderBy("creatime", timeindex);
		
		Page<UserCollect> pages = userCollectService.findByConditions(group, pageNo, pageSize, order);
		//构造一下UserCollectVo
		List<UserCollectVo>  userTopicVoList = new ArrayList<>();
		if(null != pages &&BeanUtil.isNotEmpty(pages.getContent())){
	        List<UserCollect> list = pages.getContent();
	        userTopicVoList= CollectionUtils.copyBean(list, UserCollectVo.class);
	        
	        for (UserCollectVo userCollectvo : userTopicVoList) {
	        	
	        	//查询我的专题被收藏数
	        	userCollectvo.setCollect(userCollectService.findUserCollectByUserTopic(userCollectvo.getTopic().getId()));
	        	userCollectvo.setIscollect(true);
	        	userCollectvo.setRecord(userRecordService.findUserRecordByUserTopic(userCollectvo.getTopic().getId(),EnumUtil.DELFLAG.NOMAL.getValue()));//查询专题记录数
	        	
	        	//收藏专题 如果没有上传专题图片 给了一个服务器上默认的图片地址
	        	if(BeanUtil.isEmpty(userCollectvo.getTopic().getCoverurl())){
	        		userCollectvo.getTopic().setCoverurl("796dfdb1-bfdd-4a5a-99c4-684febb4d053-d1458103329757");
	        	}
	        	
			}
	        
	        map.put("rows", userTopicVoList);
	        map.put("total", pages.getTotalElements());
	        map.put("totalCount", pages.getTotalElements());
        }else{
        	map.put("rows", userTopicVoList);
 	        map.put("total", 0);
 	        map.put("totalCount", 0);
        }
        return map;
    }
	
	
	/**
	 * @author chlei
	 * @Description: 收藏专题 取消收藏
	 * @param UserTopic userTopic 哪个专题,String collect 收藏 取消标记
	 */
	@ResponseBody
    @RequestMapping(value = "/addAndDel", method={RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> collectAddCancel(UserTopic userTopic,String collect){
			//1,哪个专题
			Map<String,Object> map = new HashMap<String,Object>();
			UserCollect userCollect=new UserCollect();
			//2,收藏者 用户
			String username = (String) SecurityUtils.getSubject().getPrincipal();
			User user = userService.findUserByUsername(username);
			
			userCollect.setTopic(userTopic);
			userCollect.setUser(user);
			if(BeanUtil.isNotEmpty(collect)){
				
				//3,添加到收藏
				if(collect.equals("true")){
					try {
						userTopic = userTopicService.findUserTopicById(userTopic.getId());
					} catch (UserTopicException e) {
						e.printStackTrace();
					}
					userCollect.setSelectStatus(EnumUtil.CHECKSTATUS.CHECKED.getValue());
					userCollect.setTopic(userTopic);
					if (BeanUtil.isNotEmpty(userTopic.getTopiccolor())) {
						userCollect.setTopiccolor(userTopic.getTopiccolor());
					}else{
						userCollect.setTopiccolor("#3469b4");
					}
					userCollectService.addUserCollect(userCollect);
					map.put("status", Result.STATUS_OK);
			        map.put("msg", "收藏成功！");
			        map.put("coverurl",userTopic.getCoverurl() );
				}
						
				//4, 取消收藏
				if(collect.equals("false")){
					userCollectService.cancelUserCollect(userTopic,user.getId());
					map.put("status", Result.STATUS_OK);
			        map.put("msg", "取消收藏成功！");
			        map.put("coverurl",userTopic.getCoverurl() );
				}
			}else{
				map.put("status", Result.STATUS_OK);
		        map.put("msg", "收藏取消参数错误！");
		        map.put("coverurl",userTopic.getCoverurl() );
			}
		return map;
	}
	
}
