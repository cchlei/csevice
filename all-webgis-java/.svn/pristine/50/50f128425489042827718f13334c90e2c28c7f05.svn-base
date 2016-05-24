package com.trgis.trmap.personal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.PageAndSortUtil;
import com.trgis.trmap.personal.dao.UserTopicPermissionDao;
import com.trgis.trmap.personal.exception.UserTopicPermissionException;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.personal.model.UserTopicPermission;
import com.trgis.trmap.personal.service.UserTopicPermissionService;
import com.trgis.trmap.personal.util.BeanUtil;
import com.trgis.trmap.userauth.model.User;


@Service
@Transactional
public class UserTopicPermissionServiceImpl implements UserTopicPermissionService{

	
	@Autowired
	private UserTopicPermissionDao userTopicPermissionDao;
	
	private static final Log log = LogFactory.getLog(UserTopicPermissionServiceImpl.class);

	@Override
	public Page<UserTopicPermission> queryByUser(ConditionGroup conditionGroup, int pageNum, int pageSize,
			OrderBy... order) throws UserTopicPermissionException {
		try {
			Specification<UserTopicPermission> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
			long count = userTopicPermissionDao.count(specifications);
			if(count == 0) {
				return null;
			}
			PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
			log.debug("专题权限列表获取成功！");
			return userTopicPermissionDao.findAll(specifications,page);
		} catch (Exception e) {
			log.debug("专题权限列表获取失败!");
			throw new UserTopicPermissionException("专题权限列表获取失败！");
		}
	}

	@Override
	public void deleteByTopic(Long topicId) throws UserTopicPermissionException {
		try {
			if (BeanUtil.isEmpty(topicId)) {
				throw new UserTopicPermissionException("专题id为空!");
			}
			userTopicPermissionDao.deleteByTopic(topicId);
			log.debug("根据专题id删除专题权限成功！");
		} catch (Exception e) {
			log.debug("根据专题id删除专题权限失败!");
			throw e;
		}
		
	}

	@Override
	public void deletePermission(Long topicId, Long userId) throws UserTopicPermissionException {
		try {
			if (BeanUtil.isEmpty(topicId)) {
				throw new UserTopicPermissionException("专题id为空!");
			}
			if (BeanUtil.isEmpty(userId)) {
				throw new UserTopicPermissionException("用户id为空!");
			}
			userTopicPermissionDao.delete(topicId,userId);
			log.debug("删除单个用户专题权限成功！");
		} catch (Exception e) {
			log.debug("删除单个用户专题权限失败!");
			throw e;
		}
	}

	@Override
	public void deletePermission(Long topicId, Long[] userIds) throws UserTopicPermissionException {
		try {
			if (BeanUtil.isEmpty(topicId)) {
				throw new UserTopicPermissionException("专题id为空!");
			}
			if (BeanUtil.isEmpty(userIds)) {
				throw new UserTopicPermissionException("用户id为空!");
			}
			userTopicPermissionDao.delete(topicId,userIds);
			log.debug("删除单个用户专题权限成功！");
		} catch (Exception e) {
			log.debug("删除单个用户专题权限失败!");
			throw e;
		}
	}

	@Override
	public void addPermission(UserTopic userTopic, Long userId) throws UserTopicPermissionException {
		try {
			if (BeanUtil.isEmpty(userTopic)) {
				throw new UserTopicPermissionException("专题信息为空!");
			}
			if (BeanUtil.isEmpty(userId)) {
				throw new UserTopicPermissionException("用户id为空!");
			}
			UserTopicPermission userTopicPermission = new UserTopicPermission();
			userTopicPermission.setUserTopic(userTopic);
			User user = new User();
			user.setId(userId);
			userTopicPermission.setUser(user);
			userTopicPermissionDao.save(userTopicPermission);
			log.debug("添加单个用户专题权限成功！");
		} catch (Exception e) {
			log.debug("添加单个用户专题权限失败!");
			throw e;
		}
	}

	@Override
	public void addPermission(Long topicId, Long userId) throws UserTopicPermissionException {
		try {
			if (BeanUtil.isEmpty(topicId)) {
				throw new UserTopicPermissionException("专题信息为空!");
			}
			if (BeanUtil.isEmpty(userId)) {
				throw new UserTopicPermissionException("用户id为空!");
			}
			UserTopicPermission userTopicPermission = new UserTopicPermission();
			UserTopic userTopic = new UserTopic();
			userTopic.setId(topicId);
			userTopicPermission.setUserTopic(userTopic);
			User user = new User();
			user.setId(userId);
			userTopicPermission.setUser(user);
			userTopicPermissionDao.save(userTopicPermission);
			log.debug("添加单个用户专题权限成功！");
		} catch (Exception e) {
			log.debug("添加单个用户专题权限失败!");
			throw e;
		}
	}

	@Override
	public void addPermissions(Long topicId, Long[] userId) throws UserTopicPermissionException {
		try {
			if (BeanUtil.isEmpty(topicId)) {
				throw new UserTopicPermissionException("专题信息为空!");
			}
			if (BeanUtil.isEmpty(userId)) {
				throw new UserTopicPermissionException("用户id为空!");
			}
			UserTopic userTopic = new UserTopic();
			userTopic.setId(topicId);
			UserTopicPermission userTopicPermission = null;
			User user = null;
			List<UserTopicPermission> list = new ArrayList<UserTopicPermission>();
			for (Long id : userId) {
				userTopicPermission = new UserTopicPermission();
				user = new User();
				user.setId(id);
				userTopicPermission.setUserTopic(userTopic);
				userTopicPermission.setUser(user);
				list.add(userTopicPermission);
			}
			userTopicPermissionDao.save(list);
			log.debug("添加单个用户专题权限成功！");
		} catch (Exception e) {
			log.debug("添加单个用户专题权限失败!");
			throw e;
		}
	}

	@Override
	public Long countUser(Long topicId) throws UserTopicPermissionException {
		try {
			if (BeanUtil.isEmpty(topicId)) {
				throw new UserTopicPermissionException("专题id为空!");
			}
			log.debug("统计专题开放人数成功！");
			return userTopicPermissionDao.countUser(topicId);
		} catch (Exception e) {
			log.debug("统计专题开放人数失败!");
			throw e;
		}
	}

	@Override
	public List<UserTopicPermission> queryAll(User user) throws UserTopicPermissionException{
		try {
			if (BeanUtil.isEmpty(user.getId())) {
				throw new UserTopicPermissionException("专题id为空!");
			}
			log.debug("统计专题开放人数成功！");
			return userTopicPermissionDao.queryByUser(user);
		} catch (Exception e) {
			log.debug("统计专题开放人数失败!");
			throw e;
		}
	}
}
