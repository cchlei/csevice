package com.trgis.trmap.personal.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.trgis.trmap.personal.dao.UserAttentionDao;
import com.trgis.trmap.personal.dao.UserGroupDao;
import com.trgis.trmap.personal.dao.UserMessageDao;
import com.trgis.trmap.personal.dao.UserTopicPermissionDao;
import com.trgis.trmap.personal.exception.UserAttentionException;
import com.trgis.trmap.personal.exception.UserGroupException;
import com.trgis.trmap.personal.model.UserAttention;
import com.trgis.trmap.personal.model.UserGroup;
import com.trgis.trmap.personal.model.UserMessage;
import com.trgis.trmap.personal.model.UserTopicPermission;
import com.trgis.trmap.personal.service.UserAttentionService;
import com.trgis.trmap.personal.util.BeanUtil;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.personal.util.GroupAttentionVo;
import com.trgis.trmap.userauth.dao.UserDAO;
import com.trgis.trmap.userauth.model.User;


@Transactional
@Service
public class UserAttentionServiceImp implements UserAttentionService{

	@Autowired
	private UserAttentionDao userAttentionDao;
	@Autowired
	private UserGroupDao userGroupDao;
	@Autowired
	private UserMessageDao userMessageDao;
	@Autowired
	private UserDAO userDAO;
	
	private static final Log log = LogFactory.getLog(UserRecordServiceImpl.class);
	@Override
	public void cancleAttention(Long cuid,Long buid) throws UserAttentionException {
		try {
			UserAttentionException exception = BeanUtil.isEmpty(buid) ? new UserAttentionException("好友为空!") : null;
			exception = BeanUtil.isEmpty(cuid) ? new UserAttentionException("当前用户信息为空!") : null;
			if (exception !=null) {
				throw exception;
			}
			UserAttention cuserAttention = userAttentionDao.getUserAttention(cuid,buid);
			if (cuserAttention.getAtteflag()==EnumUtil.ATTENTIONFLAG.HGZ.getValue()){
				UserAttention buserAttention = userAttentionDao.getUserAttention(buid,cuid);
				userAttentionDao.updateAttentionFlag(EnumUtil.ATTENTIONFLAG.YGZ.getValue(),buserAttention.getId());
			}
			userAttentionDao.delete(cuserAttention);
			log.debug("取消关注好友成功！");
		} catch (Exception e) {
			log.debug("取消关注好友失败！");
			throw new UserAttentionException("取消关注好友成功！",e);
		}
	}

	@Override
	public void saveAttention(User cuser,User buser,Long gruopId) throws UserAttentionException {
		try {
			UserAttentionException exception = BeanUtil.isEmpty(buser) ? new UserAttentionException("被关注者id为空!") : null;
			exception = BeanUtil.isEmpty(cuser) ? new UserAttentionException("当前用户id为空!") : null;
			if (exception !=null) {
				throw exception;
			}
			UserAttention check = userAttentionDao.check(cuser,buser);
			if (check!=null) {
				throw new UserAttentionException("你已经关注过好友，请刷新页面尝试");
			}
			UserAttention userAttention = new UserAttention(cuser,buser);
			UserAttention buserAttention = userAttentionDao.getUserAttention(buser.getId(),cuser.getId());
			if (BeanUtil.isEmpty(buserAttention)) {
				userAttention.setAtteflag(EnumUtil.ATTENTIONFLAG.YGZ.getValue());
			}else{
				userAttention.setAtteflag(EnumUtil.ATTENTIONFLAG.HGZ.getValue());
				userAttentionDao.updateAttentionFlag(EnumUtil.ATTENTIONFLAG.HGZ.getValue(),buserAttention.getId());
			}
			if (BeanUtil.isEmpty(gruopId)) {
				List<UserGroup> groups = userGroupDao.getGroups(cuser.getId(), EnumUtil.DELFLAG.NOMAL.getValue());
				if (BeanUtil.isEmpty(groups)) {
					UserGroup userGroup = new UserGroup();
					userGroup.setGroupName("未分组");
					userGroup.setUser(cuser);
					UserGroup group = userGroupDao.save(userGroup);
					gruopId = group.getGid();
				}else{
					for (int i = 0; i < groups.size(); i++) {
						if (groups.get(i).getGroupName().equals("未分组")) {
							gruopId = groups.get(i).getGid();
							break;
						}
					}
				}
			}
			userAttention.getUserGroup().setGid(gruopId);
			userAttentionDao.save(userAttention);
			UserMessage message = new UserMessage();
			message.setMessageContent(cuser.getUsername()+" 关注了您！");
			message.setMessageTitle(cuser.getUsername()+" 关注了您！");
			message.setMsgType(EnumUtil.MESSAGE_TYPE.FANS.getValue());
			message.setUser(buser);
			userMessageDao.save(message);
			log.debug("关注好友成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("关注好友失败！");
		}
	}

	@Override
	public void updateGroup(Long groupId, Long cuid, Long buid) throws UserAttentionException {
		try {
			UserAttentionException exception = BeanUtil.isEmpty(buid) ? new UserAttentionException("被关注者id为空!") : null;
			exception = BeanUtil.isEmpty(cuid) ? new UserAttentionException("当前用户id为空!") : null;
			exception = BeanUtil.isEmpty(groupId) ? new UserAttentionException("分组id为空!") : null;
			if (exception !=null) {
				throw exception;
			}
			userAttentionDao.updateGroup(groupId,cuid,buid);
			log.debug("修改好友分组成功！");
		} catch (Exception e) {
			log.debug("修改好友分组失败!");
			throw new UserAttentionException("修改好友分组失败!");
		}
	}

	@Override
	public Page<UserAttention> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,OrderBy... order)throws  UserAttentionException{
		try {
			if (BeanUtil.isEmpty(conditionGroup)) {
				throw new UserAttentionException("查询条件为空!");
			}
			Specification<UserAttention> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
			long count = userAttentionDao.count(specifications);
			if (count == 0) {
				return null;
			}
			PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
			log.debug("我的好友列表查询成功！");
			return userAttentionDao.findAll(specifications, page);
		} catch (Exception e) {
			log.debug("我的好友列表查询失败!");
			throw new UserAttentionException("我的好友列表查询失败!",e);
		}
	}

	@Override
	public Long getAttentionNumber(ConditionGroup group) throws UserAttentionException {
		try {
			if (BeanUtil.isEmpty(group)) {
				throw new UserGroupException("查询条件为空!");
			}
			Specification<UserAttention> specifications = DynamicSpecficationUtil.buildSpecfication(group);
			log.debug("查询好友数量成功！");
			return userAttentionDao.count(specifications);
		} catch (Exception e) {
			log.debug("查询好友数量失败!");
			throw new UserAttentionException("查询好友数量失败!");
		}
	}

	@Override
	public List<Object[]> findUsers(Long userid,String name,Integer size) throws UserAttentionException {
		try {
			if (BeanUtil.isEmpty(userid)) {
				throw new UserGroupException("用户id为空!");
			}
			size = size == null ?8:size;
			name = name == null ? new String() : name;
			name = "%"+name+"%";
			log.debug("模糊查询好友成功！");
			return userAttentionDao.findUsers(userid,userid,name,name,size);
		} catch (Exception e) {
			log.debug("模糊查询好友失败!");
			throw new UserAttentionException("查询好友失败!");
		}
	}

	@Override
	public Long[] getAttens(Long userid) throws UserAttentionException {
		try {
			if (BeanUtil.isEmpty(userid)) {
				throw new UserGroupException("用户id为空!");
			}
			return userAttentionDao.findUsers(userid);
		} catch (Exception e) {
			log.debug("模糊查询好友失败!");
			throw new UserAttentionException("查询好友失败!");
		}
	}

	@Override
	public Page<User> findUserByConditions(ConditionGroup group, int pageNum, int pageSize, OrderBy order) throws UserAttentionException {
		try {
			if (BeanUtil.isEmpty(group)) {
				throw new UserAttentionException("查询条件为空!");
			}
			Specification<User> specifications = DynamicSpecficationUtil.buildSpecfication(group);
			long count = userDAO.count(specifications);
			if (count == 0) {
				return null;
			}
			PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
			log.debug("我的好友列表查询成功！");
			return userDAO.findAll(specifications, page);
		} catch (Exception e) {
			log.debug("我的好友列表查询失败!");
			throw new UserAttentionException("我的好友列表查询失败!",e);
		}
	}

	@Override
	public Integer isAttention(User cuser, User buser) throws UserAttentionException {
		try {
			UserAttention userAttention = userAttentionDao.getUserAttention(cuser.getId(),buser.getId());
			if (userAttention!=null) {
				return userAttention.getAtteflag();
			}else{
				return -1;
			}
		} catch (Exception e) {
			log.debug("我的好友列表查询失败!");
			throw new UserAttentionException("我的好友列表查询失败!",e);
		}
	}
	
	@Autowired
	private UserTopicPermissionDao userTopicPermissionDao;

	@Override
	public List<GroupAttentionVo> getAttensAndGroup(User userBean,Long topicid) throws UserAttentionException {
		try {
			List<GroupAttentionVo> data = new ArrayList<GroupAttentionVo>();
			List<UserGroup> groups = userGroupDao.getGroups(userBean.getId(), EnumUtil.DELFLAG.NOMAL.getValue());
			GroupAttentionVo vo = null;
			if(topicid!=null){
				List<UserTopicPermission> bytopic = userTopicPermissionDao.queryBytopic(topicid);
				if(bytopic!=null&&bytopic.size()!=0){
					Set<Long> gids = new HashSet<Long>();
					Set<Long> uids = new HashSet<Long>();
					for (UserTopicPermission temp : bytopic) {
						if(temp.getGid()!=null){
							gids.add(temp.getGid());
						}
						uids.add(temp.getUser().getId());
					}
					for (UserGroup userGroup : groups) {
						vo = new GroupAttentionVo();
						vo.setGid(userGroup.getGid());
						vo.setName(userGroup.getGroupName());
						if (gids.contains(userGroup.getGid())) {
							vo.setShrink(true);
							gids.remove(userGroup.getGid());
						}
						List<UserAttention> list = userAttentionDao.getAttentionByGroup(userGroup.getGid());
						List<GroupAttentionVo.UserVo> users = new ArrayList<GroupAttentionVo.UserVo>();
						GroupAttentionVo.UserVo votemp = null;
						for (UserAttention temp : list) {
							votemp = new GroupAttentionVo.UserVo();
							votemp.setUserId(temp.getBuser().getId());
							votemp.setUserName(temp.getBuser().getUsername());
							votemp.setHeadimg(temp.getBuser().getHeadimg());
							if (uids.contains(temp.getBuser().getId())) {
								votemp.setChecked(true);
								uids.remove(temp.getBuser().getId());
							}
							users.add(votemp);
						}
						vo.setUser(users);
						data.add(vo);
					}
				}else{
					data = datas(groups);
				}
			}else{
				data = datas(groups);
			}
			return data;
		} catch (Exception e) {
			log.debug("我的好友列表查询失败!");
			throw new UserAttentionException("我的好友列表查询失败!",e);
		}
	}
	private List<GroupAttentionVo> datas(List<UserGroup> groups) {
		List<GroupAttentionVo> data = new ArrayList<GroupAttentionVo>();
		GroupAttentionVo vo = null;
		for (UserGroup userGroup : groups) {
			vo = new GroupAttentionVo();
			vo.setGid(userGroup.getGid());
			vo.setName(userGroup.getGroupName());
			List<UserAttention> list = userAttentionDao.getAttentionByGroup(userGroup.getGid());
			List<GroupAttentionVo.UserVo> users = new ArrayList<GroupAttentionVo.UserVo>();
			GroupAttentionVo.UserVo votemp = null;
			for (UserAttention temp : list) {
				votemp = new GroupAttentionVo.UserVo();
				votemp.setUserId(temp.getBuser().getId());
				votemp.setUserName(temp.getBuser().getUsername());
				votemp.setHeadimg(temp.getBuser().getHeadimg());
				users.add(votemp);
			}
			vo.setUser(users);
			data.add(vo);
		}
		return data;
	}
}
