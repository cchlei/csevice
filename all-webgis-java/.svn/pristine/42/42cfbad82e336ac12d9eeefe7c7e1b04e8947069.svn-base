package com.trgis.trmap.personal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.trgis.trmap.personal.dao.UserAttachDao;
import com.trgis.trmap.personal.dao.UserCollectDao;
import com.trgis.trmap.personal.dao.UserCommentDao;
import com.trgis.trmap.personal.dao.UserMessageDao;
import com.trgis.trmap.personal.dao.UserRecordDao;
import com.trgis.trmap.personal.dao.UserTopicDao;
import com.trgis.trmap.personal.dao.UserTopicPermissionDao;
import com.trgis.trmap.personal.dao.UserTopicTagDao;
import com.trgis.trmap.personal.exception.UserTopicException;
import com.trgis.trmap.personal.model.UserCollect;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.personal.service.UserTopicService;
import com.trgis.trmap.personal.util.BeanUtil;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;

@Service
@Transactional
public class UserTopicServiceImpl implements UserTopicService{

	
	@Autowired
	private UserTopicDao userTopicDao;
	@Autowired
	private UserRecordDao userRecordDao;
	@Autowired
	private UserAttachDao userAttachDao;
	@Autowired
	private UserCommentDao userCommentDao;
	@Autowired
	private UserCollectDao  userCollectDao;
	@Autowired
	private UserTopicTagDao  userTopicTagDao;
	@Autowired
	private UserMessageDao userMessageDao;
	@Autowired
	private UserTopicPermissionDao userTopicPermissionDao;
	
	private static final Log log = LogFactory.getLog(UserRecordServiceImpl.class);
	

	@Override
	public UserTopic createUserTopic(UserTopic userTopic) throws UserTopicException {
		try {
			if (BeanUtil.isEmpty(userTopic)) {
				throw new UserTopicException("专题对象不能为空!");
			}
			UserTopic topic = userTopicDao.save(userTopic);
			log.debug("专题保存成功！");
			return topic;
		} catch (Exception e) {
			log.debug("专题保存失败！");
			throw new UserTopicException("专题保存失败!");
		}
	}
	/**
	 * @ClassName:deleteUserTopic
	 * @Description:删除专题
	 * @Author chlei 
	 * @Date 2016年3月10日
	 * @return
	 */
	@Override
	public void deleteUserTopic(Long topicid) throws UserTopicException {
		try {
			//1,查询专题
			UserTopic userTopic = userTopicDao.findOne(topicid);
			if (BeanUtil.isEmpty(userTopic)) {
				throw new UserTopicException("专题未找到！");
			}
			
			//删除专题的公开范围信息
			userTopicPermissionDao.deleteByTopic(topicid);
			
			//删除专题同时删除收藏表的数据
			userCollectDao.deleteUserCollectByUserTopic(userTopic.getId());
			log.debug("专题收藏级联删除成功！");
			userTopic.setDelflag(EnumUtil.DELFLAG.DEL.getValue());
			userTopicDao.saveAndFlush(userTopic);
			log.debug("专题删除状态改为已删除标记成功！");
			List<UserRecord> userRecordList = userRecordDao.findRecordByTopicId(userTopic);
			if(userRecordList.size()>0){
				Long[] ids = new Long[userRecordList.size()];
				for (int i = 0; i < ids.length; i++) {
					ids[i] = userRecordList.get(i).getId();
				}
				//删除附件
				userAttachDao.clearAttachfileByIds(EnumUtil.DELFLAG.DEL.getValue(),ids);
				log.debug("专题附件删除状态改为已删除标记！");
				//删除评论级联的消息，滞空评论，消息不删除
				Object[] comIds = userCommentDao.getIdsBy(ids);//获得评论的id数组
				if (comIds!=null&&comIds.length>0) {
					//通过评论的id集合取消消息的级联关系
					userMessageDao.updateMsg(comIds);
				}
				//删除评论
				userCommentDao.clearUserCommentByIds(ids);
				log.debug("事件 评论删除状态改为已删除成功！");
			}
			userRecordDao.clearRecordById(EnumUtil.DELFLAG.DEL.getValue(),userTopic);
			log.debug("专题事件删除状态改为已删除标记成功！");
			//删除专题同时删除收藏表的数据
//			if(BeanUtil.isEmpty(topicid)){
//				userCollectDao.deleteUserCollectByUserTopic(topicid);
//				log.debug("该专题所有收藏已取消");
//			}
//			if(userRecordList.size()>0){
//				
//				for(UserRecord userRecord:userRecordList){
//					
//					userAttachDao.clearAttachfileById(EnumUtil.DELFLAG.DEL.getValue(),userRecord);
//					log.debug("专题附件删除状态改为已删除标记！");
//					
//					userCommentDao.clearUserCommentById(userRecord.getId());
//					log.debug("事件 评论删除状态改为已删除标记！");
//				}
//				
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("专题删除失败 =="+e.getMessage());
			throw new UserTopicException("专题删除失败！对对对");
		}
	}
    
	@Override
	public void editUserTopic(UserTopic userTopic) throws UserTopicException {
		try {
			if (BeanUtil.isEmpty(userTopic)) {
				throw new UserTopicException("不能为空！");
			}
			//如果专题状态变为私密的，取消所有收藏者的资格
			if(userTopic.getShareflag()==0){
			   userCollectDao.deleteUserCollectByUserTopic(userTopic.getId());
			   log.debug("该专题所有收藏已取消");
			}
			   userTopicDao.saveAndFlush(userTopic);
			log.debug("专题修改成功！");
		} catch (Exception e) {
			log.debug("专题修改失败！");
			throw new UserTopicException("修改失败！");
		}
	}

	@Override
	public List<UserTopic> findAllUserTopic(Long id) throws UserTopicException {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new UserTopicException("用户不能为空！");
			}
			log.debug("专题列表查询成功！");
			return userTopicDao.findAllByUser(EnumUtil.DELFLAG.NOMAL.getValue(),id);
		} catch (Exception e) {
			log.debug("专题列表失败！");
			throw new UserTopicException("专题列表查询失败！");
		}
	}

	@Override
	public List<String> findUserTopicname() throws UserTopicException {
		try {
			List<String> names=  userTopicTagDao.findAllName();
			log.debug("查询成功！");
			return names;
		} catch (Exception e) {
			log.debug("查询失败！");
			throw new UserTopicException("查询失败!");
		}
		}

	public List<UserTopic> findShareUserTopic() {
		return userTopicDao.findUserTopicByShare(EnumUtil.SHAREFLAG.YFX.getValue(),EnumUtil.DELFLAG.NOMAL.getValue() );
	}
	
	@Override
	public UserTopic findUserTopicById(Long id) throws UserTopicException {
		
		try {
			log.debug("查询成功！");
			return userTopicDao.findOne(id);
		} catch (Exception e) {
			log.debug("查询失败！");
			throw new UserTopicException("查询失败!");
		}
	}

	@Override
	public Page<UserTopic> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,
			OrderBy... order) {
		Specification<UserTopic> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = userTopicDao.count(specifications);
		if(count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return userTopicDao.findAll(specifications,page);
		
	}

	@Override
	public Map<String, Long> getTopicCount(Long id) throws UserTopicException {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new UserTopicException("用户id为空");
			}
			log.debug("查询成功！");
			Long countTopicAll = userTopicDao.countTopicAll(id,EnumUtil.DELFLAG.NOMAL.getValue());
			Long countTopicWfx = userTopicDao.countTopicShareflag(id,EnumUtil.SHAREFLAG.WFX.getValue(),EnumUtil.DELFLAG.NOMAL.getValue());
			Long countTopicYfx = countTopicAll - countTopicWfx;
			Map<String,Long> map = new HashMap<String,Long>();
			map.put("all", countTopicAll);
			map.put("wfx", countTopicWfx);
			map.put("yfx", countTopicYfx);
			return map;
		} catch (Exception e) {
			log.debug("查询失败！");
			throw new UserTopicException("查询失败!");
		}
	}

	@Override
	public Long getShareCount(Integer shareFlag) throws UserTopicException {
		try {
			log.debug("查询成功！");
			return userTopicDao.getShareCount(EnumUtil.DELFLAG.NOMAL.getValue(),shareFlag);
		} catch (Exception e) {
			log.debug("查询失败！");
			throw new UserTopicException("查询失败!");
		}
	}
	
	@Override
	public List<UserTopic> findAllUserTopicCollect(Long userid) throws UserTopicException {
		try {
			if (BeanUtil.isEmpty(userid)) {
				throw new UserTopicException("用户不能为空！");
			}
			List<UserCollect> list =userCollectDao.findUserCollectByUser(userid);
			List<UserTopic> userTopics = new ArrayList<UserTopic>();
			UserTopic userTopic = null;
			for (UserCollect userCollect : list) {
				userTopic = userCollect.getTopic();
				if (userTopic.getDelflag()==EnumUtil.DELFLAG.DEL.getValue()) {
					continue;
				}
				userTopic.setTopiccolor(userCollect.getTopiccolor());
				userTopic.setSelectStatus(userCollect.getSelectStatus());
				userTopics.add(userTopic);
			}
			log.debug("收藏专题列表查询成功！");
			return userTopics;
		} catch (Exception e) {
			log.debug("收藏专题列表失败！");
			throw new UserTopicException("收藏专题列表查询失败！");
		}
	}
	@Override
	public void updateTopicColor(String color, Long topicId) throws UserTopicException {
		try {
			if (BeanUtil.isEmpty(topicId)||BeanUtil.isEmpty(color)) {
				throw new UserTopicException("专题或者颜色不能为空！");
			}
			log.debug("我的专题颜色修改成功！");
			userTopicDao.updateColor(color,topicId);
		} catch (Exception e) {
			log.debug("我的专题颜色修改失败！");
			throw new UserTopicException("我的专题颜色修改失败！");
		}
	}
	@Override
	public void updateSelectStatus(Integer status, Long topicId) throws UserTopicException {
		try {
			if (BeanUtil.isEmpty(topicId) || BeanUtil.isEmpty(status)) {
				throw new UserTopicException("专题id或者状态不能为空！");
			}
			log.debug("我的专题状态修改成功！");
//			status = (status == EnumUtil.CHECKSTATUS.NOCHECK.getValue()) ? EnumUtil.CHECKSTATUS.NOCHECK.getValue()
//					: EnumUtil.CHECKSTATUS.CHECKED.getValue();
			userTopicDao.updateStatus(status, topicId);
		} catch (Exception e) {
			log.debug("我的专题状态修改失败！");
			throw new UserTopicException("我的专题状态修改失败！");
		}
	}
	
	/**
	 * @author chlei
	 * @param shareflag
	 * @return List<UserTopic>
	 */
	@Override
	public List<UserTopic> getShareList(Integer shareflag,Integer delflag,Long userid) {
		
		return userTopicDao.findShareTopic(shareflag,delflag,userid,EnumUtil.TOPIC_PERMISSION.ALL.getValue());
	}
	@Override
	public Integer isMyTopic(Long userId, Long topicId) throws UserTopicException {
		try {
			if (BeanUtil.isEmpty(topicId)||BeanUtil.isEmpty(userId)) {
				throw new UserTopicException("专题id或者用户id不能为空！");
			}
			log.debug("查询成功！");
			return userTopicDao.isMyTopic(userId,topicId);
		} catch (Exception e) {
			log.debug("查询失败！");
			throw new UserTopicException("查询失败！");
		}
	}
	@Override
	public List<UserTopic> getAttentionTopic(User user, Integer shareflag, Integer delflag) throws UserTopicException {
		try {
			if (BeanUtil.isEmpty(user)) {
				throw new UserTopicException("用户信息为空");
			}
			log.debug("查询成功！");
			return userTopicDao.queryAttentionTopics(shareflag, delflag, user.getId());
		} catch (Exception e) {
			log.debug("查询失败！");
			throw e;
		}
	}
	@Override
	public Page<UserTopic> findByUsers(ConditionGroup group, int pageNo, int pageSize, OrderBy order) throws UserTopicException {
		try {
			Specification<UserTopic> specifications = DynamicSpecficationUtil.buildSpecfication(group);
			long count = userTopicDao.count(specifications);
			if(count == 0) {
				return null;
			}
			PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNo, pageSize, order);
			log.debug("成功！");
			return userTopicDao.findAll(specifications,page);
		} catch (Exception e) {
			log.debug("失败!");
			e.printStackTrace();
			throw e;
		}
	}

}
