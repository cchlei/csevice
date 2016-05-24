package com.trgis.trmap.system.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.common.util.DateUtil;
import com.trgis.trmap.system.utils.ConvertJSON;
import com.trgis.trmap.system.utils.JSONPage;
import com.trgis.trmap.system.utils.Result;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserEntity;
import com.trgis.trmap.userauth.model.emun.UserStatus;
import com.trgis.trmap.userauth.service.UserService;
import com.trgis.trmap.userauth.util.QTManager;

@Controller
@RequestMapping(value = "/user")
public class UserManageController {

	private static final Logger logger = LoggerFactory.getLogger(UserManageController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String redirectUserList() {
		return "user/list";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public String userList(Long draw, String searchText, String filterName, String userStatus, String startDate,
			String endDate, Integer start, Integer length, String column, String dir) {
		QTManager manager = (QTManager) SecurityUtils.getSubject().getPrincipal();
		logger.debug("当前管理员是：" + manager.getId());
		logger.debug(String.format("draw = %s. start = %s. length = %s", draw, start, length));
		logger.debug(String.format("User status is %s", UserStatus.getStatus(userStatus)));
		logger.debug(String.format("Search Date during %s - %s", startDate, endDate));

		if (start == null) {
			start = 0;
		}
		if (length == null) {
			length = 10;
		}
		int pageNum = (start / length) + 1;

		// 配置查询条件组合
		ConditionGroup cgRoot = new ConditionGroup();
		cgRoot.setSearchRelation(SearchRelation.AND);// 基本组合关系为AND

		// 添加根条件 用户状态条件
		if (StringUtils.isNotBlank(userStatus)) {
			cgRoot.getConditions().add(new SearchCondition("status", Operator.EQ, UserStatus.getStatus(userStatus)));
		}

		// 查询过滤条件为 子条件组
		if (StringUtils.isNotBlank(searchText)) {
			ConditionGroup filterGroup = new ConditionGroup();
			filterGroup.setSearchRelation(SearchRelation.OR); // 设置条件关系为OR

			// 判断并添加查询条件
			List<SearchCondition> searchFilters = new ArrayList<SearchCondition>(); // 设置条件
			switch (filterName) {
			case "账号":
				searchFilters.add(new SearchCondition("username", Operator.LIKE, searchText));
				break;
			case "姓名":
				searchFilters.add(new SearchCondition("name", Operator.LIKE, searchText));
				break;
			case "邮箱":
				searchFilters.add(new SearchCondition("email", Operator.LIKE, searchText));
				break;
			default:
				searchFilters.add(new SearchCondition("username", Operator.LIKE, searchText));
				searchFilters.add(new SearchCondition("name", Operator.LIKE, searchText));
				searchFilters.add(new SearchCondition("email", Operator.LIKE, searchText));
			}
			filterGroup.getConditions().addAll(searchFilters);// 将条件集合加入到第二组条件中

			cgRoot.getSubConditions().add(filterGroup);// 讲过滤条件组加入根查询条件中
		}

		// 设置时间条件组
		if (StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(endDate)) {
			ConditionGroup dateTimeFilter = new ConditionGroup();
			dateTimeFilter.setSearchRelation(SearchRelation.AND);
			try {
				if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
					dateTimeFilter.getConditions()
							.add(new SearchCondition("createDate", Operator.BETWEEN,
									DateUtils.parseDate(startDate, DateUtil.PARSEPATTERN),
									DateUtils.parseDate(endDate, DateUtil.PARSEPATTERN)));
				} else {
					if (StringUtils.isNotBlank(startDate)) {
						dateTimeFilter.getConditions().add(new SearchCondition("createDate", Operator.GTE,
								DateUtils.parseDate(startDate, DateUtil.PARSEPATTERN)));
					}
					if (StringUtils.isNotBlank(endDate)) {
						dateTimeFilter.getConditions().add(new SearchCondition("createDate", Operator.LTE,
								DateUtils.parseDate(endDate, DateUtil.PARSEPATTERN)));
					}
				}
			} catch (ParseException e) {
				logger.debug("exception:", e);
				return "";
			}
			cgRoot.getSubConditions().add(dateTimeFilter);
		}
		OrderBy order = null;
		if (StringUtils.isNotBlank(column)) {
			order = new OrderBy(column, dir);
		}
		Page<User> userPage = userService.findByConditions(cgRoot, pageNum, length, order);
		try {
			return ConvertJSON.convert2DataTables(draw, userPage);
		} catch (JsonProcessingException e) {
			logger.debug("exception:", e);
			return "";
		}
	}

	/**
	 * @author Alice
	 * 用户列表页面转发
	 * @return
	 */
	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	public String userlist() {
		return "user/userlist";
	}
	
    /**
     * @author Alice
     * 用户分页列表
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/userlist", method = RequestMethod.POST)
    public JSONPage getUserList(int rows,int page,String searchVal,String entity) {
    	// 配置查询条件组合
 		ConditionGroup cgRoot = new ConditionGroup();
 		cgRoot.setSearchRelation(SearchRelation.AND);// 基本组合关系为AND
 		UserEntity userentity = UserEntity.PERSONAL;
 		if(StringUtils.isNoneBlank(entity)){
 			userentity = UserEntity.getEntity(Integer.parseInt(entity));
 		}
 		cgRoot.getConditions().add(new SearchCondition("entity", Operator.EQ, userentity));
 		
    	//查询条件
    	String[] search= null;
    	if(!StringUtils.isEmpty(searchVal)){
			search = searchVal.split(":");//search[0]值search[1]字段
			// 查询过滤条件为 子条件组
			ConditionGroup filterGroup = new ConditionGroup();
			filterGroup.setSearchRelation(SearchRelation.OR); // 设置条件关系为OR
			// 判断并添加查询条件
			List<SearchCondition> searchFilters = new ArrayList<SearchCondition>(); // 设置条件
			if ("all".equals(search[1])) {
				 String [] types = {"username","name","email","mobile","status"};
				 for (int i = 0; i < types.length; i++) {
					 if("status".equals(types[i])){
						 searchFilters.add(new SearchCondition(types[i], Operator.EQ, UserStatus.getStatusByName(search[0])));
					 }else{
						 searchFilters.add(new SearchCondition(types[i], Operator.LIKE, search[0]));
					 }
				 }
			 }else{
				 if("status".equals(search[1])){
					 searchFilters.add(new SearchCondition(search[1], Operator.EQ, UserStatus.getStatusByName(search[0])));
				 }else{
					 searchFilters.add(new SearchCondition(search[1], Operator.LIKE, search[0]));
				 }
			 }
			filterGroup.getConditions().addAll(searchFilters);// 将条件集合加入到第二组条件中
			cgRoot.getSubConditions().add(filterGroup);// 讲过滤条件组加入根查询条件中
    	}
    	OrderBy order = new OrderBy("id", "desc");
		Page<User> userPage = userService.findByConditions(cgRoot, page, rows, order);

		JSONPage json = new JSONPage();
        //构造操作按钮
        List<User> user = userPage==null?new ArrayList<User>():userPage.getContent();
        for (User u : user) {
        	if(u.getStatus()==UserStatus.LOCKED)
			u.setOperation("<a class='viewInfo' href='javascript:void(0);' onclick='unlock("+u.getId()+");'>解冻</a>");
		}
        json.setRows(user);
        json.setTotal(userPage==null?0:(int)userPage.getTotalElements());
		return json;
    }
    
    /**
     * @author Alice
     * 锁定用户
     */
    @ResponseBody
    @RequestMapping(value = "/locked", method = RequestMethod.POST)
    public Result locked(Long id,Result result) {
		try {
    		User user = userService.findUser(id);
    		if(user != null){
    			if(UserStatus.CLOSED.equals(user.getStatus())){//锁定
    				result.setStatus(Result.STATUS_FAILED);
        			result.setMsg("该用户已被封停！");
    			}else{
	    			userService.changeUserStatus(id,UserStatus.LOCKED);
	    			result.setStatus(Result.STATUS_OK);
    			}
    		} else{
    			result.setStatus(Result.STATUS_FAILED);
    			result.setMsg("锁定用户失败！");
    		}
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("锁定用户失败！");
		}
		return result;
    }
    
    /**
     * @author Alice
     * 封停用户
     */
    @ResponseBody
    @RequestMapping(value = "/closed", method = RequestMethod.POST)
    public Result closed(Long id,Result result) {
		try {
    		User user = userService.findUser(id);
    		if(user != null){
    			userService.changeUserStatus(id,UserStatus.CLOSED);
    			result.setStatus(Result.STATUS_OK);
    		}else{
    			result.setStatus(Result.STATUS_FAILED);
    			result.setMsg("封停用户失败！");
    		}
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("封停用户失败！");
		}
		return result;
    }
    
    /**
     * @author Alice
     * 解冻用户
     */
    @ResponseBody
    @RequestMapping(value = "/unlock/{id}", method = RequestMethod.POST)
    public Result unlock(@PathVariable("id") Long id,Result result) {
		try {
    		User user = userService.findUser(id);
    		if(user != null){
    			userService.changeUserStatus(id,UserStatus.ACTIVE);
    			result.setStatus(Result.STATUS_OK);
    		}else{
    			result.setStatus(Result.STATUS_FAILED);
    			result.setMsg("解冻用户失败！");
    		}
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("解冻用户失败！");
		}
		return result;
    }
    
	/**
	 * @author Alice
	 * 用户资料重置页面转发
	 * @return
	 */
	@RequestMapping(value = "/usermodify", method = RequestMethod.GET)
	public String usermodify() {
		return "user/modify";
	}
    
    /**
     * @author Alice
     * 重置用户密码
     */
    @ResponseBody
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public Result resetPwd(Long id,Result result) {
		try {
    		User user = userService.findUser(id);
    		if(user != null){
    			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
    			userService.resetPassword(id, uuid);
    			result.setStatus(Result.STATUS_OK);
    			result.setMsg(uuid);
    		}else{
    			result.setStatus(Result.STATUS_FAILED);
    			result.setMsg("重置用户密码失败！");
    		}
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("重置用户密码失败！");
		}
		return result;
    }
    
}
