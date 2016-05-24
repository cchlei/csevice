package com.trgis.trmap.personal.service;

import java.util.List;

import com.trgis.trmap.personal.exception.UserGroupException;
import com.trgis.trmap.personal.model.UserGroup;
import com.trgis.trmap.userauth.model.User;

/**
 * @ClassName: UserGroupService
 * @Description: 分组接口
 * @author yanpeng
 * @date 2016年3月24日 下午3:38:26
 */
public interface UserGroupService {
	/**
	 * @Description: 添加分组
	 * @author yanpeng
	 * @date 2016年3月25日 上午9:14:17
	 * @param userid
	 * @param userGroup
	 */
	public UserGroup saveGroup(UserGroup userGroup,User user) throws UserGroupException;
	/**
	 * @Description: 修改分组
	 * @author yanpeng
	 * @date 2016年3月25日 上午9:14:29
	 * @param userGroup
	 */
	public void updateGroup(UserGroup userGroup,User user) throws UserGroupException;
	/**
	 * @Description: 删除分组，将分组下的好友转移到未分组
	 * @author yanpeng
	 * @date 2016年3月25日 上午9:14:39
	 * @param userId
	 * @param groupId
	 */
	public void deleteGroup(Long userId,Long groupId) throws UserGroupException;
	
	/**
	 * @Description: 获取分组列表
	 * @author yanpeng
	 * @date 2016年3月25日 上午9:14:53
	 * @param userid
	 */
	public List<UserGroup> getGroups(User user) throws UserGroupException;
}
