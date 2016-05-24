package com.trgis.trmap.personal.service.impl;

import java.util.HashSet;
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
import com.trgis.trmap.personal.dao.UserCollectDao;
import com.trgis.trmap.personal.exception.UserCollectException;
import com.trgis.trmap.personal.exception.UserTopicException;
import com.trgis.trmap.personal.model.UserCollect;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.personal.service.UserCollectService;
import com.trgis.trmap.personal.util.BeanUtil;
/**
 * 
 * @author chlei
 *
 */
@Service
@Transactional
public class UserCollectServiceImpl implements UserCollectService{

	@Autowired
	private UserCollectDao userCollectDao;
	
	private static final Log log = LogFactory.getLog(UserRecordServiceImpl.class);
	
	@Override
	public Long getCollectCount(long id) throws UserCollectException {
		try {
			log.debug("查询成功！");
			return userCollectDao.countCollect(id);
		} catch (Exception e) {
			log.debug("查询失败！");
			e.printStackTrace();
			throw new UserCollectException("查询失败!");
		}
	}

	@Override
	public Long findUserCollectByUserTopic(Long topicId) {
		
		return  userCollectDao.findUserCollectByUserTopic(topicId);
	}

	/**
	 * @author chlei
	 * @return 收藏专题
	 */
	@Override
	public void addUserCollect(UserCollect userCollect) {
		
		userCollectDao.save(userCollect);
	}
	
	/**
	 * @author chlei
	 * 取消收藏
	 */
	@Override
	public void cancelUserCollect(UserTopic userTopic,Long userid) {
		
		userCollectDao.deleteUserCollectByUserTopic(userTopic,userid);
	}
	

	@Override
	public void updateTopicColor(String color,Long userId,Long topicId) throws UserCollectException {
		try {
			if (BeanUtil.isEmpty(userId)||BeanUtil.isEmpty(color)||BeanUtil.isEmpty(topicId)) {
				throw new UserTopicException("用户id,专题id或者颜色不能为空！");
			}
			log.debug("修改收藏专题成功！");
			userCollectDao.updateUpdateColor(color,userId,topicId);
		} catch (Exception e) {
			log.debug("修改收藏专题失败！");
			e.printStackTrace();
			throw new UserCollectException("修改收藏专题失败!");
		}
	}

	@Override
	public void updateSelectStatus(Integer status, Long topicId, Long userId) throws UserCollectException {
		try {
			if (BeanUtil.isEmpty(topicId) || BeanUtil.isEmpty(status) || BeanUtil.isEmpty(userId)) {
				throw new UserTopicException("专题id或者状态不能为空！");
			}
			log.debug("收藏专题状态修改成功！");
//			status = (status == EnumUtil.CHECKSTATUS.NOCHECK.getValue()) ? EnumUtil.CHECKSTATUS.NOCHECK.getValue()
//					: EnumUtil.CHECKSTATUS.CHECKED.getValue();
			userCollectDao.updateStatus(status, topicId,userId);
		} catch (Exception e) {
			log.debug("收藏专题状态修改失败！");
			throw new UserCollectException("收藏专题状态修改失败！");
		}
	}

	/**
	 * @author chlei
	 * @param ConditionGroup conditionGroup, int pageNum, int pageSize,OrderBy... order
	 * @收藏分页
	 */
	@Override
	public Page<UserCollect> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,OrderBy... order) {
		
		Specification<UserCollect> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = userCollectDao.count(specifications);
		if(count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return userCollectDao.findAll(specifications,page);
	}

	/**
	 * @author chlei
	 * @param userid topicid
	 * @return Boolean
	 */
	@Override
	public Boolean findCollectTopicByUser(Long userId, Long topicId) {
		
		Boolean flage=false;
		
		UserCollect userCollect=userCollectDao.findUserCollect(userId,topicId);
		
		if(BeanUtil.isNotEmpty(userCollect)){
			flage=true;
		}
		
		return flage;
	}

	@Override
	public void deleteOnPermisChage(Long topicid, Long[] ids) throws UserCollectException {
		try {
			if (BeanUtil.isEmpty(topicid)) {
				throw new UserCollectException("为空!");
			}
			if (ids==null||ids.length==0) {
				userCollectDao.deleteUserCollectByUserTopic(topicid);
			}else{
				Long[] findByTopic = userCollectDao.findByTopic(topicid);
				if (findByTopic==null||findByTopic.length==0) {
					return;
				}
				Set<Long> set = new HashSet<Long>();
				for (Long id : findByTopic) {
					set.add(id);
				}
				for (Long id : ids) {
					if (set.contains(id)) {
						set.remove(id);
					}
				}
				if (set.size()!=0) {
					int index = 0;
					Long[] aids = new Long[set.size()];
					for (Long id : set) {
						aids[index] = id;
					}
					userCollectDao.delete(topicid,aids);
				}
			}
			log.debug("成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("失败!");
			throw new UserCollectException("失败!");
		}
	}

}
