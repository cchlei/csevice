package com.trgis.trmap.personal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.personal.model.UserTopic;

/**
 * @author chlei
 * 关于专题的dao
 */
@Repository
public interface UserTopicDao extends JpaRepository<UserTopic,Long>,JpaSpecificationExecutor<UserTopic>{
	
	/**
	 * 查询出所有专题记录，包括未分享的
	 * @param delflag
	 * @return
	 */
	public List<UserTopic> findUserTopicByDelflag(Integer delflag);
	
	/**
	 * 获取所有专题数
	 * @param id
	 * @param 删除标志
	 * @return
	 */
	@Query("select count(*) from UserTopic where user_id = ?1 and delflag =?2")
	public Long countTopicAll(Long id,Integer delflag);
	
	/**
	 * 获取未分享专题数
	 * @param id
	 * @param 分享状态
	 * @param 删除标志
	 * @return
	 */
	@Query("select count(*) from UserTopic where user_id = ?1 and shareflag = ?2 and delflag =?3")
	public Long countTopicShareflag(Long id,Integer shareflag,Integer delflag);


	/**
	 * 获取所有未删除的分享数
	 * @param integer 删除标志
	 * @return 
	 */
	@Query("select count(*) from UserTopic where delflag =?1 and shareflag=?2 ")
	public Long getShareCount(Integer integer,Integer shareflag);
	
	/**
	 * @Description: 根据用户id，获取所有未删除的专题
	 * @author yanpeng
	 * @date 2016年3月11日 上午11:41:21
	 * @param id
	 * @return
	 */
	@Query("from UserTopic where delflag =?1 and user_id = ?2 order by creatime desc")
	public List<UserTopic> findAllByUser(Integer delflag,Long id);
	
	/**
	 * @author chlei
	 * @date 2016年3月11日 上午13:38:21
	 * @param EnumUtil.SHAREFLAG.YFX.getValue() 已分享,EnumUtil.DELFLAG.NOMAL.getValue() 未删除
	 * @return List<UserTopic>
	 */
	@Query("from UserTopic where shareflag =?1 and delflag = ?2")
	public List<UserTopic> findUserTopicByShare(Integer shareflag,Integer delflag);

	/**
	 * @Description: 修改专题颜色
	 * @author yanpeng
	 * @date 2016年3月11日 下午3:09:10
	 * @param color
	 * @param topicId
	 */
	@Modifying 
	@Query("update UserTopic set topiccolor =?1 where id =?2") 
	public void updateColor(String color, Long topicId);

	/**
	 * @Description:修改我的专题状态
	 * @author yanpeng
	 * @date 2016年3月11日 下午5:31:28
	 * @param status
	 * @param topicId
	 */
	@Modifying 
	@Query("update UserTopic set selectStatus =?1 where id =?2")
	public void updateStatus(Integer status, Long topicId);
	
	/**
	 * 如果是随机获取5条记录，则
	 * select * from test order by random() limit 5;
	 * from UserTopic where shareflag =?1 and delflag = ?2 order by limit 8
	 * @author chlei
	 * @param userid 
	 * @return 所有分享
	 */
	@Query(nativeQuery=true , value="select * from trmap_user_topic where shareflag =?1 and delflag = ?2 and user_id<>?3 and permissions=?4 order by random() limit 8")
	public List<UserTopic> findShareTopic(Integer shareflag,Integer delflag, Long userid,Integer jurisdict);
	
	/**
	 * @Description: 判断专题是否为某人的
	 * @author yanpeng
	 * @date 2016年3月15日 下午6:11:20
	 * @param userId
	 * @param topicId
	 * @return
	 */
	@Query("select count(*) from UserTopic where user_id =?1 and id=?2 ")
	public Integer isMyTopic(Long userId, Long topicId);

	/**
	 * @Description: 获取当前用户关注的
	 * @author yanpeng
	 * @date 2016年3月31日 下午5:19:55
	 * @param userid
	 * @return
	 */
	@Query(nativeQuery=true , value="SELECT * FROM trmap_user_topic where shareflag =?1 and delflag = ?2 and EXISTS(SELECT buser_id FROM trmap_user_attention where cuser_id= ?3)")
	public List<UserTopic> queryAttentionTopics(Integer shareflag,Integer delflag,Long userid);

}
