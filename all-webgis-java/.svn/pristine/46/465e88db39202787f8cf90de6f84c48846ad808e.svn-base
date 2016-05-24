package com.trgis.trmap.personal.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.personal.exception.UserTopicException;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.userauth.model.User;

/**
 * 专题服务类
 * @author chlei
 * @date 2016-03-08
 */
public interface UserTopicService {
	
	/**
	 * @Description:专题事件
	 * @Author liuyan
	 * @Date 2016年3月9日
	 * @param userTopic
	 * @throws UserTopicException
	 */
	public UserTopic createUserTopic(UserTopic userTopic) throws UserTopicException;

	/**
	 * 删除专题记录
	 * @param id
	 * @throws UserTopicException
	 */
	public void deleteUserTopic(Long id) throws UserTopicException;

	/**
	 * @Description:修改专题记录
	 * @Author liuyan
	 * @Date 2016年3月10日
	 * @param userTopic
	 * @throws UserTopicException
	 */
	public void editUserTopic(UserTopic userTopic) throws UserTopicException;

	/**
	 * @param userid
	 * @Description: 根据用户id，获取所有未删除的专题
	 * @author yanpeng
	 * @date 2016年3月11日 上午11:33:08
	 * @param id 用户id
	 * @return
	 * @throws UserTopicException 
	 */
	public List<UserTopic> findAllUserTopic(Long userid) throws UserTopicException;
	
	/**
	 * @param userid
	 * @Description: 根据用户id，获取所有收藏未删除的专题
	 * @author yanpeng
	 * @date 2016年3月11日 上午11:33:08
	 * @param id 用户id
	 * @return
	 * @throws UserTopicException 
	 */
	public List<UserTopic> findAllUserTopicCollect(Long userid) throws UserTopicException;

	/**
	 * @Description:获取所有专题名称
	 * @Author liuyan
	 * @Date 2016年3月11日
	 * @return
	 * @throws UserTopicException 
	 */
	public List<String> findUserTopicname() throws UserTopicException;


	/**
	 * 获取所有分享的专题
	 * @param List<UserTopic>
	 */
	public List<UserTopic> findShareUserTopic(); 
	
	/**
	 * @Description:根据获取个人专题 根据专题id查询专题的标题、描述、相册封面，收藏数等信息。
	 * @Author liuyan
	 * @Date 2016年3月11日
	 * @param id
	 * @return UserTopic
	 * @throws UserTopicException 
	 */
	public UserTopic findUserTopicById(Long id) throws UserTopicException;

	/**
	 * 分页查询 .
	 * @param page .
	 * @param hql .
	 * @return Page .
	 */
	public Page<UserTopic> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order);

	/**
	 * 获取专题数量
	 * @param id 用户id
	 * @return
	 */
	public Map<String, Long> getTopicCount(Long id) throws UserTopicException;

	/**
	 * 获取所有分享数
	 * @return
	 * @throws UserTopicException
	 */
	public Long getShareCount(Integer shareFlag) throws UserTopicException;
	
	/**
	 * @Description: 修改专题颜色
	 * @author yanpeng
	 * @date 2016年3月11日 下午3:09:10
	 * @param color
	 * @param topicId
	 */
	public void updateTopicColor(String color,Long topicId) throws UserTopicException ;
	
	/**
	 * @Description: 修改我的专题状态
	 * @author yanpeng
	 * @date 2016年3月11日 下午5:26:47
	 * @param status
	 * @param topicId
	 * @throws UserTopicException
	 */
	public void updateSelectStatus(Integer status,Long topicId) throws UserTopicException ;
	
	public List<UserTopic> getShareList(Integer shareflag,Integer delflag, Long userid);
	
	/**
	 * @Description: 判断是否为我的专题
	 * @author yanpeng
	 * @date 2016年3月15日 下午6:06:47
	 * @param userId
	 * @param topid
	 * @return
	 */
	public Integer isMyTopic(Long userId,Long topicId) throws UserTopicException;
	
	/**
	 * @Description: 获取当前用户所关注用户的专题列表
	 * @author yanpeng
	 * @date 2016年3月31日 下午4:55:33
	 * @param user
	 * @param shareflag
	 * @param delflag
	 * @return
	 * @throws UserTopicException
	 */
	public List<UserTopic> getAttentionTopic(User user,Integer shareflag,Integer delflag) throws UserTopicException;

	/**
	 * @Description: 分页获取我关注好友的专题
	 * @author yanpeng
	 * @date 2016年4月5日 下午2:25:28
	 * @param group
	 * @param pageNo
	 * @param pageSize
	 * @param order
	 * @return
	 * @throws UserTopicException
	 */
	public Page<UserTopic> findByUsers(ConditionGroup group, int pageNo, int pageSize, OrderBy order)throws UserTopicException;
}
