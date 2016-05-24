package com.trgis.trmap.personal.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.trmap.personal.exception.UserAttentionException;
import com.trgis.trmap.personal.model.UserAttention;
import com.trgis.trmap.personal.service.UserAttentionService;
import com.trgis.trmap.personal.util.GroupAttentionVo;
import com.trgis.trmap.personal.webapp.utils.Result;
import com.trgis.trmap.personal.webapp.vo.UserAttentionVo;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserEntity;
import com.trgis.trmap.userauth.model.emun.UserStatus;
import com.trgis.trmap.userauth.service.UserService;

/**
 * @ClassName: AttentionController
 * @Description: 好友
 * @author yanpeng
 * @date 2016年3月28日 上午11:10:43
 */
@Controller
@RequestMapping("/attention")
public class AttentionController {

	@Autowired
	private UserAttentionService userAttentionService;
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(AttentionController.class);
	
	/**
	 * @Description: 获取我关注好友的列表
	 * @author yanpeng
	 * @date 2016年3月28日 下午2:03:09
	 * @param page
	 * @param rows
	 * @param groupId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("attens")
	public UserAttentionVo getAttentions(Integer page,Integer rows,Long gid,String type){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		UserAttentionVo result = new UserAttentionVo();
		User user = getUserBean();
		try {
	    	int pageNum = page==null?0:page;
	        int pageSize = rows==null?12:rows;
	        /*获取好友  或者  粉丝数量*/
	        ConditionGroup groupCount = new ConditionGroup();
	        groupCount.setSearchRelation(SearchRelation.AND);
	        Page<UserAttention> findByConditions = null;
	        List<SearchCondition> condition = new ArrayList<SearchCondition>();
	        if (type==null || type.equals("groups")) {
	        	condition.add(new SearchCondition("cuser", Operator.EQ, user.getId()));
	        	if (gid !=null) {
	        		condition.add(new SearchCondition("userGroup", Operator.EQ,gid));
	        	}
	        }else{
	        	condition.add(new SearchCondition("buser", Operator.EQ, user.getId()));
	        }
			groupCount.setConditions(condition);
			Long number = userAttentionService.getAttentionNumber(groupCount);
			result.setTotalCount(number);
			/*分页查询好友*/
			ConditionGroup group = new ConditionGroup();
			group.setSearchRelation(SearchRelation.AND);
			List<SearchCondition> conditions = new ArrayList<SearchCondition>();
			Map<String,Object> map = null;
			if (type==null || type.equals("groups")) {
				conditions.add(new SearchCondition("cuser", Operator.EQ,user.getId()));
				if (gid != null) {
					conditions.add(new SearchCondition("userGroup", Operator.EQ,gid));
				}
				group.setConditions(conditions);
				OrderBy order = new OrderBy("occurtime", "desc");
				findByConditions = userAttentionService.findByConditions(group, pageNum, pageSize,
						order);
				if (findByConditions == null) {
					throw new UserAttentionException("没有好友");
				}
				for (UserAttention userAttention : findByConditions.getContent()) {
					map = new HashMap<String,Object>();
					map.put("userName", userAttention.getBuser().getUsername());
					map.put("userId", userAttention.getBuser().getId());
					map.put("headimg", userAttention.getBuser().getHeadimg());
					map.put("atteflag", userAttention.getAtteflag());
					list.add(map);
				}
			}else{ //分页查询粉丝列表
				conditions.add(new SearchCondition("buser", Operator.EQ, getUserBean().getId()));
				group.setConditions(conditions);
				OrderBy order = new OrderBy("occurtime", "desc");
				findByConditions = userAttentionService.findByConditions(group, pageNum, pageSize,
						order);
				if (findByConditions == null) {
					throw new UserAttentionException("没有粉丝");
				}
				for (UserAttention userAttention : findByConditions.getContent()) {
					map = new HashMap<String,Object>();
					map.put("userName", userAttention.getCuser().getUsername());
					map.put("userId", userAttention.getCuser().getId());
					map.put("headimg", userAttention.getCuser().getHeadimg());
					map.put("atteflag", userAttention.getAtteflag());
					list.add(map);
				}
			}
			if (list.size() == 0) {
				throw new UserAttentionException("没有好友");
			}
			result.setStatus(Result.STATUS_OK);
			result.setData(list);
			logger.debug("获取好友列表成功");
		} catch (UserAttentionException e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setData(list);
			if (e.getMessage().equals("没有好友")) {
				result.setMsg("该分组还没有关注的好友，要多去结识好友哦");
			}else if(e.getMessage().equals("没有粉丝")){
				result.setMsg("您还没有粉丝，要多去结识好友哦");
			}
			logger.debug("获取好友列表失败："+e.getMessage());
		}catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setData(list);
			result.setMsg("服务器异常");
			logger.debug("获取好友列表失败："+e.getMessage());
		}
		return result;
	}
	
	/**
	 * @Description: 取消关注好友
	 * @author yanpeng
	 * @date 2016年3月28日 下午2:29:56
	 * @param buid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("cancle")
	public Result cancle(Long buid){
		Result result = new Result();
		try {
			userAttentionService.cancleAttention(getUserBean().getId(), buid);
			result.setStatus(Result.STATUS_OK);
			logger.debug("取消关注成功");
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setData(e.getMessage());
			logger.debug("取消关注失败"+e.getMessage());
		}
		return result;
	}

	/**
	 * @Description: 添加关注
	 * @author yanpeng
	 * @date 2016年3月28日 下午2:48:16
	 * @param buid
	 * @param groupId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add")
	public Result add(Long buid,Long groupId){
		Result result = new Result();
		try {
			User buser = userService.findUser(buid);
			userAttentionService.saveAttention(getUserBean(), buser, groupId);
			result.setStatus(Result.STATUS_OK);
			logger.debug("关注好友成功");
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setData(e.getMessage());
			logger.debug("关注好友失败"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * @Description: 修改好友分组
	 * @author yanpeng
	 * @date 2016年3月29日 上午9:24:14
	 * @param userId
	 * @param gid
	 * @return
	 */
    @ResponseBody
	@RequestMapping("edit")
	public Result editGroup(Long userId,Long gid){
		Result result = new Result();
		try {
			userAttentionService.updateGroup(gid, getUserBean().getId(), userId);
			result.setStatus(Result.STATUS_OK);
			result.setMsg("修改分组成功");
			logger.debug("修改分组成功");
		} catch (UserAttentionException e) {
			e.printStackTrace();
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("修改分组失败");
			logger.debug("修改分组是失败");
		}
		return result;
	}
    
    @ResponseBody
 	@RequestMapping("query")
 	public UserAttentionVo queryUser(String key,Integer page,Integer rows){
    	UserAttentionVo result = new UserAttentionVo();
 		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
 		try {
 			int pageNum = page==null?0:page;
	        int pageSize = rows==null?9:rows;
 			User userBean = getUserBean();
 			ConditionGroup group = new ConditionGroup();
 			group.setSearchRelation(SearchRelation.AND);
 			List<SearchCondition> conditions = new ArrayList<SearchCondition>();
 			conditions.add(new SearchCondition("status", Operator.EQ,UserStatus.ACTIVE));
 			conditions.add(new SearchCondition("id", Operator.NEQ,userBean.getId()));
 			conditions.add(new SearchCondition("username", Operator.LIKE,key));
 			conditions.add(new SearchCondition("entity", Operator.EQ,UserEntity.getEntity(0)));
 			group.setConditions(conditions);
 			OrderBy order = new OrderBy("id", "asc");
			Page<User> findByConditions = userAttentionService.findUserByConditions(group, pageNum, pageSize,order);
			if (findByConditions == null) {
				throw new UserAttentionException("没有粉丝");
			}
			result.setTotalCount(findByConditions.getTotalElements());
			Map<String,Object> map;
 			for (User user : findByConditions.getContent()) {
 				map = new HashMap<String,Object>();
				map.put("userId", user.getId());
				map.put("userName", user.getUsername());
				map.put("headimg", user.getHeadimg());
				map.put("atteflag", userAttentionService.isAttention(userBean,user));
				list.add(map);
			}
 			result.setStatus(Result.STATUS_OK);
 			result.setData(list);
 			logger.debug("查询好友成功");
 		} catch (UserAttentionException e) {
 			result.setStatus(Result.STATUS_FAILED);
 			result.setMsg(e.getMessage());
 			result.setData(list);
 			logger.debug(e.getMessage());
 		}
 		return result;
 	}
    
    @RequestMapping("toaddens")
    public ModelAndView toAttens(ModelAndView mv){
    	mv.setViewName("groups");
    	return mv;
    }
    
    @ResponseBody
    @RequestMapping("queryattens")
    public UserAttentionVo queryGroupAtentions(Long topicId){
    	UserAttentionVo result = new UserAttentionVo();
    	try {
			List<GroupAttentionVo> users = userAttentionService.getAttensAndGroup(getUserBean(), topicId);
			result.setData(users);
			Long total = 0l;
			for (GroupAttentionVo groupAttentionVo : users) {
				total += groupAttentionVo.getUser().size();
			}
			result.setStatus(Result.STATUS_OK);
			result.setTotalCount(total);
		} catch (UserAttentionException e) {
			e.printStackTrace();
		}
    	return result;
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
