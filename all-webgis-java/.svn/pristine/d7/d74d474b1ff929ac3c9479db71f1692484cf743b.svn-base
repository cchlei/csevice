package com.trgis.trmap.personal.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.personal.exception.UserCollectException;
import com.trgis.trmap.personal.exception.UserTopicException;
import com.trgis.trmap.personal.model.UserCollect;
import com.trgis.trmap.personal.model.UserTopic;

@Repository
public interface UserCollectDao extends JpaRepository<UserCollect,Long>,JpaSpecificationExecutor<UserCollect>{

	@Query("select count(*) from UserCollect where user_id = ?1")
	public Long countCollect(Long id);
	
	/**
	 * @author chlei
	 * @param UserTopic
	 * @return Long
	 */
	
	@Query(nativeQuery=true,value="select COUNT(*) from trmap_user_collect  where topic_id = ?1")
	public Long findUserCollectByUserTopic(Long topicId);
	
	
	/**
	 * @author chlei
	 * @param UserTopic
	 * @return Long
	 */
	@Modifying
	@Query("delete from UserCollect where topic =?1 and user_id=?2") 
	public void deleteUserCollectByUserTopic(UserTopic UserTopic , Long userid);
	
	/**
	 * @author liuyan
	 * @param UserTopic
	 * @return Long
	 * 根据专题删除收藏表
	 */
	@Modifying
	@Query("delete from UserCollect where topic_id =?1") 
	public void deleteUserCollectByUserTopic(Long tid);
	/**
	 * @Description: 根据用户id，获取所有收藏未删除的专题
	 * @author yanpeng
	 * @date 2016年3月11日 下午2:07:06
	 * @param userid
	 * @return
	 */
	@Query("from UserCollect  where user_id = ?1 order by id desc")
	public List<UserCollect> findUserCollectByUser(Long userid);
	
//	/**
//	 * @author chlei
//	 * @param UserTopic
//	 * @return Long
//	 */
//	@Query("delete from UserCollect where topic =?1 and user_id=?2")
//	public void addUserCollect(UserTopic userTopic, User user);
	
	/**
	 * @Description: 修改收藏专题的颜色
	 * @author yanpeng
	 * @date 2016年3月11日 下午4:00:14
	 * @param color
	 * @param userId
	 * @param topicId
	 * @throws UserCollectException
	 */
	@Modifying 
	@Query("update UserCollect set topiccolor =?1 where user_id =?2 and topic_id = ?3") 
	public void updateUpdateColor(String color, Long userId, Long topicId);
	
	/**
	 * @Description: 修改收藏专题的状态
	 * @author yanpeng
	 * @date 2016年3月11日 下午5:48:51
	 * @param status
	 * @param topicId
	 * @param userId
	 * @throws UserTopicException
	 */
	@Modifying 
	@Query("update UserCollect set selectStatus =?1 where topic_id =?2 and user_id = ?3") 
	public void updateStatus(Integer status, Long topicId, Long userId);
	
	/**
	 * @Description: 根据用户id，专题id  判断这个这个专题是否被我收藏 boolean类型
	 * @author chlei
	 * @date 2016年3月14日 下午17:29:06
	 * @param userid topicid
	 * @return boolean类型  true 按到 false
	 */
	@Query("from UserCollect  where user_id = ?1 and topic_id =?2")
	public UserCollect findUserCollect(Long userid,Long topicid);

	@Modifying 
	@Query(nativeQuery=true,value="select user_id from trmap_user_collect  where topic_id = ?1") 
	Long[] findByTopic(Long topicid);

	@Modifying 
	@Query(nativeQuery=true,value="delete from trmap_user_collect where topic_id =?1 and user_id in (?2)")
	public void delete(Long topicid, Long[] aids);
	
	
}
