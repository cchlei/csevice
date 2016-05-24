package com.trgis.trmap.personal.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.trmap.personal.exception.UserGroupException;
import com.trgis.trmap.personal.model.UserGroup;
import com.trgis.trmap.personal.service.UserGroupService;
import com.trgis.trmap.personal.webapp.utils.Result;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;

/**
 * @ClassName: GroupController
 * @Description: 分组模块
 * @author yanpeng
 * @date 2016年3月28日 下午4:33:37
 */
@Controller
@RequestMapping("/group")
public class GroupController {
	
	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
	
	@ResponseBody
	@RequestMapping("groups")
	public Result getGroups(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Result result = new Result();
		try {
			List<UserGroup> groups = userGroupService.getGroups(getUserBean());
			Map<String,Object> map = null;
			if (groups == null || groups.size() == 0) {
				throw new UserGroupException("没有分组信息");
			}
			for (UserGroup userGroup : groups) {
				map= new HashMap<String,Object>();
				map.put("gid",userGroup.getGid());
				map.put("name",userGroup.getGroupName());
				list.add(map);
			}
			result.setStatus(Result.STATUS_OK);
			result.setData(list);
			logger.debug("获取分组列表成功");
		} catch (UserGroupException e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg(e.getMessage());
			logger.debug("获取分组列表失败 ："+ e.getMessage());
		}
		result.setData(list);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public Result delete(Long buid){
		Result result = new Result();
		try {
			if (buid ==null) {
				throw new UserGroupException("分组信息为空");
			}
			userGroupService.deleteGroup(getUserBean().getId(), buid);
			result.setMsg("分组删除成功");
			result.setStatus(Result.STATUS_OK);
		} catch (Exception e) {
			result.setMsg("分组删除失败 ： "+e.getMessage());
			result.setStatus(Result.STATUS_FAILED);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("add")
	public Result add(String addname){
		Result result = new Result();
		try {
			if (addname ==null) {
				throw new UserGroupException("分组信息为空");
			}
			if (addname.equals("未分组")) {
				throw new UserGroupException("添加失败，未分组为默认分组");
			}
			if (addname.length()>=7) {
				throw new UserGroupException("分组名称长度最大为6");
			}
			UserGroup group = new UserGroup();
			group.setGroupName(addname);
			group.setUser(getUserBean());
			UserGroup saveGroup = userGroupService.saveGroup(group,getUserBean());
			result.setMsg("分组保存成功");
			result.setData(saveGroup.getGid());
			result.setStatus(Result.STATUS_OK);
		}catch(UserGroupException e){
			result.setMsg(e.getMessage());
			result.setStatus(Result.STATUS_FAILED);
		}catch (Exception e) {
			result.setMsg("分组保存失败 ");
			result.setStatus(Result.STATUS_FAILED);
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("edit")
	public Result edit(Long buid,String editname){
		Result result = new Result();
		try {
			if (buid ==null || editname == null) {
				throw new UserGroupException("分组信息不完整");
			}
			if (editname.length()>=7) {
				throw new UserGroupException("分组名称长度最大为6");
			}
			UserGroup userGroup = new UserGroup();
			userGroup.setGid(buid);
			userGroup.setGroupName(editname);
			userGroupService.updateGroup(userGroup,getUserBean());
			result.setMsg("分组保存成功");
			result.setStatus(Result.STATUS_OK);
		}catch(UserGroupException e){
			result.setMsg(e.getMessage());
			result.setStatus(Result.STATUS_FAILED);
			if (e.getMessage().equals("修改成功")) {
				result.setStatus(Result.STATUS_OK);
			}
		}catch (Exception e) {
			result.setMsg("分组保存失败 ");
			result.setStatus(Result.STATUS_FAILED);
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
