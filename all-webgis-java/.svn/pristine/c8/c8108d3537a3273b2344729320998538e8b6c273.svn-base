/**
 * Copyright © 2016陕西天润有限责任公司. All rights reserved.
 *
 * @Title: UserTopicController.java
 * @Prject: trmap-personal-webapp
 * @Package: com.trgis.trmap.personal.webapp.controller
 * @author: chlei  
 * @date: 2016年3月9日 下午2:27:42
 * @version: V1.0  
 */
package com.trgis.trmap.personal.webapp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.trgis.trmap.personal.exception.UserCollectException;
import com.trgis.trmap.personal.exception.UserTopicException;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.personal.model.UserTopicPermission;
import com.trgis.trmap.personal.service.UserAttentionService;
import com.trgis.trmap.personal.service.UserCollectService;
import com.trgis.trmap.personal.service.UserRecordService;
import com.trgis.trmap.personal.service.UserTopicPermissionService;
import com.trgis.trmap.personal.service.UserTopicService;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.personal.util.StringHandle;
import com.trgis.trmap.personal.webapp.interceptor.annotation.UserTopicRequired;
import com.trgis.trmap.personal.webapp.utils.BeanUtil;
import com.trgis.trmap.personal.webapp.utils.Result;
import com.trgis.trmap.personal.webapp.vo.UserTopicCountVo;
import com.trgis.trmap.personal.webapp.vo.UserTopicListVo;
import com.trgis.trmap.personal.webapp.vo.UserTopicVo;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;
/**
 * @ClassName: UserTopicController
 * @Description:个人用户专题控制器
 * @author: chlei
 * @date: 2016-03-09 2:27:42
 */
@Controller
@RequestMapping(value = "/topic")
public class UserTopicController {

	private static final Log log = LogFactory.getLog(UserTopicController.class);
//	private static final String JSONP = "%s(%s)";
	
	@Autowired
	private UserTopicService userTopicService;
	
	@Autowired
	private UserRecordService userRecordService;
	
	@Autowired
	private UserCollectService userCollectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAttentionService userAttentionService;


	@RequestMapping("/tolist")
	public ModelAndView initList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("topic/themes");
		return mv;
	}
	/**
	 * 专题公共菜单部分
	 * @return
	 */
	@RequestMapping("/totopic")
	public ModelAndView toTopic(HttpServletRequest request) {
		String open = request.getParameter("open");
		ModelAndView mv = new ModelAndView();
		mv.addObject("open", open);
		mv.setViewName("topic_index");
		return mv;
	}
	@RequestMapping("/toshare")
	public ModelAndView shareme(ModelAndView mv) {
		mv.setViewName("/topic/sharetome");
		return mv;
	}
	/**
	 * @Description:我的关注列表页面
	 * @author yanpeng
	 * @date 2016年4月5日 下午2:49:48
	 * @return
	 */
	@RequestMapping("/attenview")
	public ModelAndView attenView() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("topic/theme_attens");
		return mv;
	}
	@RequestMapping("/shareListView")
	public ModelAndView shareList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("topic/theme_share");
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
	 * @Description:去专题信息的页面
	 * @Author liuyan 
	 * @Date 2016年3月11日
	 * @param mv
	 * @return
	 */
	@UserTopicRequired
	@RequestMapping(value = "/toinfo/{id}", method ={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toInfo(@PathVariable("id") Long topicid, ModelAndView mv){
		UserTopic usertopic = null;
		try {
			usertopic = userTopicService.findUserTopicById(topicid);
		} catch (UserTopicException e) {
			e.printStackTrace();
		}
		mv.addObject("userTopic",usertopic);
		mv.setViewName("/topic/theme_detail");
		return mv;
	}
	/**
	 * @Description:去收藏专题详细信息的页面
	 * @Author liuyan 
	 * @Date 2016年3月11日
	 * @param mv
	 * @return
	 */
	@UserTopicRequired
	@RequestMapping(value = "/topulicinfo/{id}", method ={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toPulicinfo(@PathVariable("id") Long topicid, ModelAndView mv){
		UserTopic usertopic = null;
		try {
			usertopic = userTopicService.findUserTopicById(topicid);
		} catch (UserTopicException e) {
			e.printStackTrace();
		}
		mv.addObject("userTopic",usertopic);
		mv.setViewName("/topic/theme_detail_public");
		return mv;
	}
	/**
	 * @Description:去修改的页面
	 * @Author liuyan 
	 * @Date 2016年3月10日
	 * @param mv
	 * @return
	 */
	@UserTopicRequired
	@RequestMapping(value = "/toedit/{id}", method ={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toEdit(@PathVariable("id") Long topicId, ModelAndView mv){
		UserTopic usertopic;
		try {
			User user  = this.getUserBean();
			usertopic = userTopicService.findUserTopicById(topicId);
			mv.addObject("userTopic",usertopic);
			mv.addObject("user",user);
			mv.setViewName("/topic/theme_edit");
		} catch (UserTopicException e) {
			e.printStackTrace();
		}
		return mv;
		
	}
	
	
    /**
     * 个人专题分页列表
     * shareflag 分享状态 0 为未分享 1已分享
     * @author chlei
     * @return
     * @throws ParseException 
     */
	@ResponseBody
    @RequestMapping(value = "/List", method={RequestMethod.POST,RequestMethod.GET})
    public Map<String,Object> userTopicList(@RequestParam("rows")Integer rows, @RequestParam("page")Integer page,Integer shareflag,HttpServletRequest request) throws ParseException {
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");   
    	Map<String,Object> map = new HashMap<String,Object>();
    	//查询条件
    	ConditionGroup group = new ConditionGroup();
    	group.setSearchRelation(SearchRelation.AND);
    	
    	List<SearchCondition> conditions = new ArrayList<SearchCondition>();
    	// 关键字
    	String searchText = request.getParameter("searchText");//搜索
    	if(BeanUtil.isNotEmpty(searchText)){
    		searchText = searchText ==null?"":StringHandle.HtmltoText(searchText).replaceAll("\"", "").replace("'", "");
    		conditions.add(new SearchCondition("name", Operator.LIKE, searchText));
    	}
    	String pageNoStr = request.getParameter("page");
    	int pageNo = pageNoStr==null?0:Integer.parseInt(pageNoStr);
    	String rowsStr = request.getParameter("rows");
        int pageSize = pageNoStr==null?24:Integer.parseInt(rowsStr);
        
	    //删除状态
		conditions.add(new SearchCondition("delflag", Operator.EQ, EnumUtil.DELFLAG.NOMAL.getValue()));
		//分享状态
		if(BeanUtil.isNotEmpty(shareflag)&&(shareflag==1||shareflag==0)){//未分享 已分享状态
			conditions.add(new SearchCondition("shareflag", Operator.EQ, shareflag));
		}
		//用户
	    conditions.add(new SearchCondition("user.id", Operator.EQ, this.getUserBean().getId()));
		group.setConditions(conditions);
		//排序 应先判断方向再new
		String timeindex = request.getParameter("timeindex");//时间排序
		OrderBy  order = timeindex==null? new OrderBy("creatime", "desc"):new OrderBy("creatime", timeindex);
		
		Page<UserTopic> pages = userTopicService.findByConditions(group, pageNo, pageSize, order);//.findByConditions(group, 0, 10, order);
		//构造一下vo
		List<UserTopicVo>  userTopicVoList = new ArrayList<>();
		if(null != pages && org.apache.commons.collections.CollectionUtils.isNotEmpty(pages.getContent())){
	        List<UserTopic> list = pages.getContent();
	        userTopicVoList= CollectionUtils.copyBean(list, UserTopicVo.class);
	        
	        for (UserTopicVo userTopicVo : userTopicVoList) {
	        	
	        	userTopicVo.setCollect(userCollectService.findUserCollectByUserTopic(userTopicVo.getId()));//查询我的专题被收藏数
	        	
	        	userTopicVo.setRecord(userRecordService.findUserRecordByUserTopic(userTopicVo.getId(),EnumUtil.DELFLAG.NOMAL.getValue()));//查询专题记录数
	        	
	        	String strdate=dateformat.format(dateformat.parse(userTopicVo.getCreatime()));//格式化时间 到月
	        	userTopicVo.setCreatime(strdate);
	        	if(BeanUtil.isEmpty(userTopicVo.getCoverurl())){
	        		userTopicVo.setCoverurl("8993d50b-7e80-4f32-9a69-6074efcffeef-d1461750285536");//给了一个服务器上默认的图片地址
	        	}
			}
	        map.put("rows", userTopicVoList);
	        map.put("totalCount", pages.getTotalElements());
		} else {
			map.put("rows", userTopicVoList);
	        map.put("totalCount", 0);
		}
        return map;
    }
    
	
	/**
     * 个人专题所有分享分页列表
     * shareflag 分享状态 0 为未分享 1已分享
     * @author chlei
     * @return
     * @throws ParseException 
     */
	@ResponseBody
    @RequestMapping(value = "/shareList", method={RequestMethod.POST,RequestMethod.GET})
    public Map<String,Object> shareList(HttpServletRequest request) throws ParseException {
    	Map<String,Object> map = new HashMap<String,Object>();
    	String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userService.findUserByUsername(username);
		
	    List<UserTopic>  topicshareList=userTopicService.getShareList(EnumUtil.SHAREFLAG.YFX.getValue(),EnumUtil.DELFLAG.NOMAL.getValue(),user.getId());
	    List<UserTopicVo>  userTopicVoList=new ArrayList<>();
	    	if(null != topicshareList && BeanUtil.isNotEmpty(topicshareList)){
	    		userTopicVoList= CollectionUtils.copyBean(topicshareList, UserTopicVo.class);
	    	
		    	for (UserTopicVo userTopicVo : userTopicVoList) {
		        	//查询专题被收藏数
		        	userTopicVo.setCollect(userCollectService.findUserCollectByUserTopic(userTopicVo.getId()));
		        	//查询所有专题中是否被收藏
		        	userTopicVo.setIscollect(userCollectService.findCollectTopicByUser(user.getId(),userTopicVo.getId()));
		        	userTopicVo.setRecord(userRecordService.findUserRecordByUserTopic(userTopicVo.getId(),EnumUtil.DELFLAG.NOMAL.getValue()));//查询专题记录数
		        	
		        	//所有分享如果没有上传专题图片 给了一个服务器上默认的图片地址
		        	if(BeanUtil.isEmpty(userTopicVo.getCoverurl())){
		        		userTopicVo.setCoverurl("8993d50b-7e80-4f32-9a69-6074efcffeef-d1461750285536");
		        	}
				}
		        map.put("rows", userTopicVoList);
		        map.put("msg","换一批！");
		        map.put("status",Result.STATUS_OK);
	    	}else{
	    		map.put("rows", userTopicVoList);
		        map.put("msg","换一批！");
		        map.put("status",Result.STATUS_OK);
	    	}
		return map;
    }
	
	@Autowired
	private UserTopicPermissionService userTopicPermissionService;
	
	/**
	 * @ClassName:CreateTopic
	 * @Description:添加专题
	 * @Author liuyan
	 * @Date 2016年3月10日
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method ={RequestMethod.POST, RequestMethod.GET})
	public Result createTopic(UserTopic userTopic,String jurisdict_list) {
		Result result = new Result();
		UserTopic createUserTopic = null;
		try {
			User user = getUserBean();
			if(!BeanUtil.isEmpty(user)){
				userTopic.setUser(user);//添加用户
			}
			if(BeanUtil.isEmpty(userTopic.getCoverurl())){//给没有上传图片的一个默认图片
				userTopic.setCoverurl("8993d50b-7e80-4f32-9a69-6074efcffeef-d1461750285536");
			}
			if(BeanUtil.isEmpty(userTopic.getTopiccolor())){//默认颜色
				userTopic.setTopiccolor("#3469b4");
			}
			if(userTopic!=null){//添加专题
				createUserTopic = userTopicService.createUserTopic(userTopic);
			}
			if(jurisdict_list!=null&&jurisdict_list.trim().length()!=0){
				String[] split = jurisdict_list.split("\\|");
				Long[] ids = new Long[split.length];
				for (int i = 0; i < ids.length; i++) {
					ids[i] = Long.valueOf(split[i]);
				}
				userTopicPermissionService.addPermissions(createUserTopic.getId(), ids);
			}
			if (userTopic.getJurisdict()==EnumUtil.TOPIC_PERMISSION.FRIEND.getValue()) {
				Long[] ids = userAttentionService.getAttens(user.getId());
				userTopicPermissionService.addPermissions(createUserTopic.getId(), ids);
			}
			
			result.setStatus(Result.STATUS_OK);
			result.setMsg("专题添加成功！");
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("专题添加失败！");
		}
		return result;
	}

	
	/**
	 * @ClassName:editTopic
	 * @Description:修改专题
	 * @Author liuyan
	 * @Date 2016年3月10日
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = { RequestMethod.POST, RequestMethod.GET })
	public Result editTopic(UserTopic userTopic,String jurisdict_list) {
		Result result = new Result();
		Integer shareflags = userTopic.getShareflag();
		try {
			Integer shareflag = userTopicService.findUserTopicById(userTopic.getId()).getShareflag();

			if (userTopic != null) {// 修改专题
				User user = getUserBean();
				if (user != null) {
					userTopic.setUser(user);
				}
				userTopicService.editUserTopic(userTopic);

				// 如果状态发生改变就修改该专题下所有的记录的状态
				if (shareflags != shareflag) {
					if ((shareflags == 0 || shareflags == 1)) {// 修改附件的rid
						userRecordService.updateRecordByTopicId(userTopic.getId(), shareflags);
					}
				}
				userTopicPermissionService.deleteByTopic(userTopic.getId());
				if(shareflags==EnumUtil.SHAREFLAG.YFX.getValue()){
					//如果有好友的 id,表示指定好友
					if (userTopic.getJurisdict() == EnumUtil.TOPIC_PERMISSION.SOME.getValue()) {
						if (jurisdict_list != null && jurisdict_list.trim().length() != 0) {
							String[] split = jurisdict_list.split("\\|");
							Long[] ids = new Long[split.length];
							for (int i = 0; i < ids.length; i++) {
								ids[i] = Long.valueOf(split[i]);
							}
							userTopicPermissionService.addPermissions(userTopic.getId(), ids);
							userCollectService.deleteOnPermisChage(userTopic.getId(), ids);
						}
					}else{
						//没有指定好友，如果权限为所有好友，注意：此处不删除收藏
						if (userTopic.getJurisdict() == EnumUtil.TOPIC_PERMISSION.FRIEND.getValue()) {
							Long[] ids = userAttentionService.getAttens(user.getId());
							userTopicPermissionService.addPermissions(userTopic.getId(), ids);
							userCollectService.deleteOnPermisChage(userTopic.getId(), ids);
						}
					}
				}
			}
			result.setStatus(Result.STATUS_OK);
			result.setMsg("专题修改成功！");
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("专题修改失败！");
		}

		return result;
	}
	/**
	 * @Description:查询专题详情信息
	 * @Author liuyan 
	 * @Date 2016年3月14日
	 * @param id
	 * @return
	 */
	@UserTopicRequired
	@ResponseBody
	@RequestMapping(value = "/info/{id}", method ={RequestMethod.POST, RequestMethod.GET})
	public Map<String,Object> topicInfo(@PathVariable("id") Long topicid) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.debug("查询专题详情！");
		UserTopic userTopic = null;
		try {
			if(topicid!=null){
			userTopic	= userTopicService.findUserTopicById(topicid);//专题信息
	        //判断是否为当前用户 返回self_topic
	        Boolean self_topic = this.getUserBean().getId().equals(userTopic.getUser().getId()) ;
	        //获取公开事件数量 
	        Long num_of_pub  = userRecordService.countByTopic(userTopic, EnumUtil.SHAREFLAG.YFX.getValue(), EnumUtil.DELFLAG.NOMAL.getValue(),"");
	        //获取私密专题数量 num_of_private
	        Long num_of_private  = userRecordService.countByTopic(userTopic, EnumUtil.SHAREFLAG.WFX.getValue(), EnumUtil.DELFLAG.NOMAL.getValue(),"");
	        map.put("name", userTopic.getName());
	        map.put("description", userTopic.getDescription());
	        map.put("coverurl", userTopic.getCoverurl());
	        map.put("topic_color", userTopic.getTopiccolor());
	        map.put("num_of_private", num_of_private);
	        map.put("num_of_public", num_of_pub);
	        map.put("self_topic", self_topic);
	        map.put("shareflag", userTopic.getShareflag());
	        map.put("username", userTopic.getUser().getUsername());
	        map.put("userimg", userTopic.getUser().getHeadimg());
	        map.put("permissions", userTopic.getJurisdict());
	        map.put("firends", userTopicPermissionService.countUser(topicid));
	        User userc = getUserBean();//查看用户是否被收藏
	        Boolean boo =  userCollectService.findCollectTopicByUser(userc.getId(), topicid);
	        map.put("iscollect",boo);
	        map.put("num_of_collect", userCollectService.findUserCollectByUserTopic(topicid));//收藏数
			}
		} catch (Exception e) {
			map.put("result", "error");
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * @Description:获取专题名称
	 * @Author liuyan 
	 * @Date 2016年3月11日
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/name", method ={RequestMethod.POST, RequestMethod.GET})
	public Map<String,Object> getTopicName() {
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> themeName = null;
		try {
			themeName = userTopicService.findUserTopicname();
			map.put("themename", themeName);
			log.debug("专题名称获取完毕！");
			map.put("result", "success");
		} catch (UserTopicException e) {
			log.debug("专题名称获取失败！");
			map.put("result", "error");
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * @Description: 修改专题列表颜色
	 * @author yanpeng
	 * @date 2016年3月12日 上午9:58:29
	 * @param type
	 * @param color
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/color", method ={RequestMethod.POST, RequestMethod.GET})
	public void updateTopicColor(String color,Long id) {
		try {
			if (BeanUtil.isEmpty(color)||BeanUtil.isEmpty(id)) {
				throw new Exception();
			}
			Long userId = getUserBean().getId();
			Integer myTopic = userTopicService.isMyTopic(userId, id);
			if (myTopic > 0) {
				userTopicService.updateTopicColor(color, id);
			}else{
				userCollectService.updateTopicColor(color, userId, id);
			}
		} catch (Exception e) {
			log.debug("==修改专题颜色失败！==");
			e.printStackTrace();
		}
	}
	/**
     * 删除个人专题
     * @author chlei
     * @return
     * @throws ParseException 
     */
	@ResponseBody
	@RequestMapping(value = "/delete", method ={RequestMethod.POST, RequestMethod.GET})
	public Map<String,Object> delete(Long id) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
			try {
				
				if(BeanUtil.isNotEmpty(id)){
					userTopicService.deleteUserTopic(id);
					map.put("status", Result.STATUS_OK);
					map.put("msg"," 删除成功！");
					log.debug("专题删除完毕！");
				}
				
			} catch (UserTopicException e) {
				map.put("status", Result.STATUS_FAILED);
				map.put("msg"," 删除失败！");
				log.debug("专题删除失败！");
			}
		
		return map;
	}
	/**
	 * @Description: 修改专题选中状态
	 * @author yanpeng
	 * @date 2016年3月12日 上午10:00:48
	 * @param type
	 * @param status
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/status", method ={RequestMethod.POST, RequestMethod.GET})
	public Result updateTopicSelectStatus(Boolean status,Long id) {
		Result result = new Result();
		try {
			if (BeanUtil.isEmpty(status)||BeanUtil.isEmpty(id)) {
				throw new Exception();
			}
			int state = status ? 1 : 0;
			Long userId = getUserBean().getId();
			Integer myTopic = userTopicService.isMyTopic(userId, id);
			if (myTopic > 0) {
				userTopicService.updateSelectStatus(state, id);
			}else{
				userCollectService.updateSelectStatus(state, id, userId);
			}
			result.setStatus(Result.STATUS_OK);
			result.setMsg("修改专题选中状态成功！");
		} catch (Exception e) {
			log.debug("==修改专题选中状态失败！==");
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("修改专题选中状态失败！");
		}
		return result;
	}
	
	
	/**
	 * @Description: 我的专题，发现统计
	 * @author yanpeng
	 * @date 2016年3月14日 上午10:39:17
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/countTopic", method ={RequestMethod.POST, RequestMethod.GET})
	public UserTopicCountVo getTopicCount(){
		UserTopicCountVo userTopicCountVo = null;
		try {
			Long shareCount = userTopicService.getShareCount(EnumUtil.SHAREFLAG.ALL.getValue());
			shareCount = shareCount == null ? 0l : shareCount;
			Long id = getUserBean().getId();
			Map<String, Long> topicCount = userTopicService.getTopicCount(id);
			Long alltheme = topicCount.get("all");
			alltheme = alltheme == null ? 0l : alltheme;
			Long opentheme = topicCount.get("yfx");
			opentheme = opentheme == null ? 0l : opentheme;
			Long privatetheme = topicCount.get("wfx");
			privatetheme = privatetheme == null ? 0l : privatetheme;
			Long collectCount = userCollectService.getCollectCount(this.getUserBean().getId());
			collectCount = collectCount == null ? 0l : collectCount;
			userTopicCountVo = new UserTopicCountVo(alltheme,opentheme , privatetheme, shareCount, 0l, collectCount);
		} catch (UserTopicException e) {
			userTopicCountVo = new UserTopicCountVo();
			e.printStackTrace();
		} catch (UserCollectException e) {
			userTopicCountVo = new UserTopicCountVo();
			e.printStackTrace();
		}
		return userTopicCountVo;
	}
	
	/**
	 * @Description: 日历模式：专题列表的查询
	 * @author yanpeng
	 * @date 2016年3月15日 下午3:26:45
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/topicList", method ={RequestMethod.POST, RequestMethod.GET})
	public UserTopicListVo getTopicList(){
		UserTopicListVo userTopicListVo = null;
		try {
			Long userId = getUserBean().getId();
			List<UserTopic> myTopic = userTopicService.findAllUserTopic(userId);
			List<UserTopic> collectTopic = userTopicService.findAllUserTopicCollect(userId);
			userTopicListVo = new UserTopicListVo(myTopic,collectTopic);
		} catch (UserTopicException e) {
			userTopicListVo = new UserTopicListVo();
			e.printStackTrace();
		}
		return userTopicListVo;
	}
	
	@RequestMapping(value = "/tocalendar")
	public ModelAndView toCalendar(ModelAndView modelAndView){
		modelAndView.setViewName("/calendar");
		return modelAndView;
	}
	
	/**
	 * @Description: 获取当前用户关注的专题
	 * @author yanpeng
	 * @date 2016年4月1日 上午9:00:34
	 * @return
	 * "total":12,
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
	@RequestMapping("/attens")
	public Map<String,Object> attentionTopics(Integer rows,Integer page){
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
    	int pageNo = page==null?0:page;
        int pageSize = rows==null?24:rows;
		try {
			ConditionGroup group = new ConditionGroup();
	    	group.setSearchRelation(SearchRelation.AND);
	    	List<SearchCondition> conditions = new ArrayList<SearchCondition>();
			//用户
			String username = (String) SecurityUtils.getSubject().getPrincipal();
			User user = userService.findUserByUsername(username);
			//获得我关注的用户id
			Long[] ids =  userAttentionService.getAttens(user.getId());
			List<User> lis = new ArrayList<User>();
			User temp =null;
			for (Long id : ids) {
				temp = new User();
				temp.setId(id);
				lis.add(temp);
			}
		    conditions.add(new SearchCondition("jurisdict", Operator.EQ, EnumUtil.TOPIC_PERMISSION.ALL.getValue()));
		    conditions.add(new SearchCondition("user", Operator.IN, lis));
		    conditions.add(new SearchCondition("delflag", Operator.EQ, EnumUtil.DELFLAG.NOMAL.getValue()));
		    conditions.add(new SearchCondition("shareflag", Operator.EQ, EnumUtil.SHAREFLAG.YFX.getValue()));
			group.setConditions(conditions);
			//排序
			OrderBy  order = new OrderBy("occurtime", "desc");
			Page<UserTopic> pages = userTopicService.findByUsers(group, pageNo, pageSize, order);
			if (pages == null) {
				throw new UserTopicException("我的关注好友没有发表专题");
			}
			Map<String,Object> map;
			for (UserTopic topic : pages.getContent()) {
				map = new HashMap<String,Object>();
				map.put("topic", topic);
				map.put("collect", userCollectService.findUserCollectByUserTopic(topic.getId()));
				map.put("iscollect", userCollectService.findCollectTopicByUser(user.getId(),topic.getId()));
				map.put("record", userRecordService.findUserRecordByUserTopic(topic.getId(),EnumUtil.DELFLAG.NOMAL.getValue()));
				lists.add(map);
			}
			result.put("rows", lists);
			result.put("total", pages.getTotalElements());
			result.put("totalCount", pages.getTotalElements());
		}catch (Exception e) {
			result.put("rows", lists);
			result.put("total",0);
			result.put("totalCount",0);
			e.printStackTrace();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/shareme")
	public Map<String,Object> sharemeTopics(Integer rows,Integer page){
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
    	int pageNo = page==null?0:page;
        int pageSize = rows==null?24:rows;
		try {
			ConditionGroup group = new ConditionGroup();
	    	group.setSearchRelation(SearchRelation.AND);
	    	List<SearchCondition> conditions = new ArrayList<SearchCondition>();
			//用户
			String username = (String) SecurityUtils.getSubject().getPrincipal();
			User user = userService.findUserByUsername(username);
		    conditions.add(new SearchCondition("user", Operator.EQ, user));
			group.setConditions(conditions);
			//排序
			OrderBy  order = new OrderBy("occurtime", "desc");
			Page<UserTopicPermission> pages = userTopicPermissionService.queryByUser(group, pageNo, pageSize, order);
			if (pages == null) {
				throw new UserTopicException("我的关注好友没有发表专题");
			}
			Map<String,Object> map;
			for (UserTopicPermission topic : pages.getContent()) {
				map = new HashMap<String,Object>();
				map.put("topic", topic.getUserTopic());
				map.put("collect", userCollectService.findUserCollectByUserTopic(topic.getUserTopic().getId()));
				map.put("iscollect", userCollectService.findCollectTopicByUser(user.getId(),topic.getUserTopic().getId()));
				map.put("record", userRecordService.findUserRecordByUserTopic(topic.getUserTopic().getId(),EnumUtil.DELFLAG.NOMAL.getValue()));
				lists.add(map);
			}
			result.put("rows", lists);
			result.put("total", pages.getTotalElements());
			result.put("totalCount", pages.getTotalElements());
		}catch (Exception e) {
			result.put("rows", lists);
			result.put("total",0);
			result.put("totalCount",0);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取当前登录用户
	 * @author doris
	 * @date 2016-03-14
	 * @return user
	 */
	private User getUserBean() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User findUserByUsername = userService.findUserByUsername(username);
		return findUserByUsername;
	}
}
