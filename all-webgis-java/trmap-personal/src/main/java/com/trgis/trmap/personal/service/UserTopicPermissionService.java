package com.trgis.trmap.personal.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.personal.exception.UserTopicPermissionException;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.personal.model.UserTopicPermission;
import com.trgis.trmap.userauth.model.User;

/**
 * @ClassName: UserTopicPermissionService
 * @Description: 专题公开权限service
 * @author yanpeng
 * @date 2016年5月12日 下午2:02:03
 */
public interface UserTopicPermissionService {
	/**
	 * @Description: 查询分享给当前用户的所有专题
	 * @author yanpeng
	 * @date 2016年5月12日 下午2:14:29
	 * @param conditionGroup
	 * @param pageNum
	 * @param pageSize
	 * @param order
	 * @return
	 * @throws UserTopicPermissionException
	 */
	Page<UserTopicPermission> queryByUser(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order) throws UserTopicPermissionException;

	/**
	 * @Description: 删除专题所有权限，包括删除专题和公开专题权限
	 * @author yanpeng
	 * @date 2016年5月12日 下午2:17:55
	 * @param userTopic
	 */
	void deleteByTopic(Long topicid) throws UserTopicPermissionException;
	/**
	 * @Description: 删除单个用户的权限
	 * @author yanpeng
	 * @date 2016年5月12日 下午2:18:47
	 * @param userTopic
	 * @param user
	 */
	void deletePermission(Long topicId,Long userId) throws UserTopicPermissionException;
	/**
	 * @Description: 删除多个用户的权限
	 * @author yanpeng
	 * @date 2016年5月12日 下午2:18:47
	 * @param userTopic
	 * @param user
	 */
	void deletePermission(Long topicId,Long[] userIds) throws UserTopicPermissionException;
	
	/**
	 * @Description: 添加单条权限
	 * @author yanpeng
	 * @date 2016年5月12日 下午2:20:34
	 * @param userTopic
	 * @param user
	 * @throws UserTopicPermissionException
	 */
	void addPermission(UserTopic userTopic,Long userId) throws UserTopicPermissionException;
	/**
	 * @Description: 添加单条权限
	 * @author yanpeng
	 * @date 2016年5月12日 下午2:20:34
	 * @param userTopic
	 * @param user
	 * @throws UserTopicPermissionException
	 */
	void addPermission(Long topicId,Long userId) throws UserTopicPermissionException;
	
	/**
	 * @Description: 批量添加权限
	 * @author yanpeng
	 * @date 2016年5月12日 下午2:26:38
	 * @param topicId
	 * @param userId
	 * @throws UserTopicPermissionException
	 */
	void addPermissions(Long topicId,Long[] userId) throws UserTopicPermissionException;

	/**
	 * @Description: 统计专题开放的人数
	 * @author yanpeng
	 * @date 2016年5月13日 上午9:11:58
	 * @param topicId
	 * @return
	 * @throws UserTopicPermissionException
	 */
	Long countUser(Long topicId) throws UserTopicPermissionException;

	List<UserTopicPermission> queryAll(User user) throws UserTopicPermissionException;
}
