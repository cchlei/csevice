package com.trgis.trmap.personal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.trmap.personal.dao.UserAttentionDao;
import com.trgis.trmap.personal.dao.UserGroupDao;
import com.trgis.trmap.personal.exception.UserGroupException;
import com.trgis.trmap.personal.model.UserGroup;
import com.trgis.trmap.personal.service.UserGroupService;
import com.trgis.trmap.personal.util.BeanUtil;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;

@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService{
	
	@Autowired
	private UserGroupDao userGroupDao;
	@Autowired
	private UserAttentionDao userAttentionDao;
	
	private static final Log log = LogFactory.getLog(UserGroupServiceImpl.class);

	@Override
	public UserGroup saveGroup(UserGroup userGroup,User user) throws UserGroupException {
		try {
			if (BeanUtil.isEmpty(userGroup)) {
				throw new UserGroupException("分组信息为空!");
			}
			List<UserGroup> groups = userGroupDao.getGroups(user.getId(), EnumUtil.DELFLAG.NOMAL.getValue());
			for (UserGroup temp : groups) {
				if (temp.getGroupName().equals(userGroup.getGroupName())) {
					throw new UserGroupException("分组已经存在");
				}
			}
			UserGroup group = userGroupDao.save(userGroup);
			log.debug("添加分组成功！");
			return group;
		} catch (Exception e) {
			log.debug("添加分组失败!");
			if (e.getMessage().equals("分组已经存在")) {
				throw e;
			}
			throw new UserGroupException("添加分组失败!");
		}
		
	}

	@Override
	public void updateGroup(UserGroup userGroup,User user) throws UserGroupException {
		try {
			if (BeanUtil.isEmpty(userGroup)) {
				throw new UserGroupException("分组信息为空!");
			}
			List<UserGroup> groups = userGroupDao.getGroups(user.getId(), EnumUtil.DELFLAG.NOMAL.getValue());
			UserGroup group = null;
			for (UserGroup temp : groups) {
				if (temp.getGroupName().equals(userGroup.getGroupName())) {
					if (userGroup.getGid().equals(temp.getGid())) {
						throw new UserGroupException("修改成功");
					}else{
						throw new UserGroupException("此分组已存在!");
					}
				}
				if (temp.getGid().equals(userGroup.getGid())) {
					group = temp;
				}
			}
			if (group==null) {
				log.debug("根据id无法查到相应的分组");
				throw new UserGroupException("根据id无法查到相应的分组!");
			}
			if (group.getGroupName().equals("未分组")) {
				log.debug("未分组不能修改！");
				throw new UserGroupException("未分组不能修改!");
			}
			group.setGroupName(userGroup.getGroupName());
			userGroupDao.saveAndFlush(group);
			log.debug("修改分组信息成功！");
		} catch (Exception e) {
			log.debug("修改分组信息失败!");
			throw e;
		}
	}

	@Override
	public void deleteGroup(Long userId,Long groupId) throws UserGroupException {
		try {
			if (BeanUtil.isEmpty(groupId)) {
				throw new UserGroupException("分组id为空!");
			}
			List<UserGroup> groups = userGroupDao.getGroups(userId, EnumUtil.DELFLAG.NOMAL.getValue());
			for (UserGroup userGroup : groups) {
				if (userGroup.getGroupName().equals("未分组")) {
					if (userGroup.getGid().equals(groupId)) {
						throw new UserGroupException("未分组不能删除!");
					}else{
						userAttentionDao.updateGroups(userGroup.getGid(), groupId);
					}
					break;
				}
			}
			userGroupDao.updateDelflag(EnumUtil.DELFLAG.DEL.getValue(),groupId);
			log.debug("删除分组成功！");
		} catch (Exception e) {
			log.debug("删除分组失败!");
			throw new UserGroupException(e.getMessage());
		}
	}

	@Override
	public List<UserGroup> getGroups(User user) throws UserGroupException {
		try {
			if (BeanUtil.isEmpty(user)) {
				throw new UserGroupException("用户为空!");
			}
			List<UserGroup> groups = userGroupDao.getGroups(user.getId(), EnumUtil.DELFLAG.NOMAL.getValue());
			if (BeanUtil.isEmpty(groups)) {
				UserGroup userGroup = new UserGroup();
				userGroup.setGroupName("未分组");
				userGroup.setUser(user);
				UserGroup group = userGroupDao.save(userGroup);
				groups = new ArrayList<UserGroup>();
				groups.add(group);
			}
			log.debug("查询分组列表成功！");
			return groups;
		} catch (Exception e) {
			log.debug("查询分组列表失败!");
			throw new UserGroupException(e.getMessage());
		}
	}
}
