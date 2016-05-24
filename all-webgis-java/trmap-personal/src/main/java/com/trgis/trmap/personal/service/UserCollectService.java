package com.trgis.trmap.personal.service;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.personal.exception.UserCollectException;
import com.trgis.trmap.personal.exception.UserTopicException;
import com.trgis.trmap.personal.model.UserCollect;
import com.trgis.trmap.personal.model.UserTopic;

public interface UserCollectService {
	/**
	 * 获取我的收藏数
	 * @param id 用户id
	 * @return
	 */
	public Long getCollectCount(long id) throws UserCollectException ;
	
	/**
	 * 获取我的专题被收藏数
	 * @author chlei
	 * @param id 用户id
	 * @return
	 */
	public Long findUserCollectByUserTopic(Long topicId);
	
	/**
	 * 收藏别人的专题
	 * @author chlei
	 * @param userCollect
	 * @return void
	 */
	public void addUserCollect(UserCollect userCollect);
	
	/**
	 * 取消 收藏别人的专题
	 * @author chlei
	 * @param userCollect
	 * @return void
	 */
	public void cancelUserCollect(UserTopic userTopic,Long userid);
	
	/**
	 * @Description: 修改收藏专题的颜色
	 * @author yanpeng
	 * @date 2016年3月11日 下午4:00:14
	 * @param color
	 * @param userId
	 * @param topicId
	 * @throws UserCollectException
	 */
	public void updateTopicColor(String color,Long userId,Long topicId) throws UserCollectException ;

	/**
	 * @Description: 修改收藏专题的状态
	 * @author yanpeng
	 * @date 2016年3月11日 下午5:48:51
	 * @param status
	 * @param topicId
	 * @param userId
	 * @throws UserTopicException
	 */
	public void updateSelectStatus(Integer status,Long topicId,Long userId) throws UserCollectException ;
	
	/**
	 * 我的收藏分页查询 .
	 * @param page .
	 * @param hql .
	 * @return Page .
	 */
	public Page<UserCollect> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order);
	
	/**
	 * 分享 .
	 * @param page .
	 * @param hql .
	 * @return Page .
	 */
	public Boolean findCollectTopicByUser(Long userId,Long topicId);
	
	/**
	 * @Description: 当收藏的专题权限发生改变的时候，如果没有权限，对应的收藏记录被强制删除
	 * @author yanpeng
	 * @date 2016年5月19日 下午5:37:26
	 * @param topicid
	 * @param ids
	 * @throws UserCollectException
	 */
	public void deleteOnPermisChage(Long topicid,Long[] ids) throws UserCollectException ;
}
