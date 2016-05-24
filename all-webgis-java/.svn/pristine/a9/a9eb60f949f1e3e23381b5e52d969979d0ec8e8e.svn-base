package com.trgis.trmap.personal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.userauth.model.User;

/**
 * @author liuyan
 * 关于记录的dao
 */
@Repository
public interface UserRecordDao extends JpaRepository<UserRecord,Long>,JpaSpecificationExecutor<UserRecord>{

	/**
	 * 查询出所有专题记录，包括未分享的
	 * @param delflag
	 * @return
	 */
	public List<UserRecord> findUserRecordByDelflag(Integer delflag);
	
	/**
	 * @author chlei
	 * @param shareflag 
	 * @param UserTopic
	 * @return Long
	 */
	@Query(nativeQuery=true,value="select count(*) from trmap_user_record  where topic_id = ?1 and delflag=?2 and shareflag = ?3")
	public Long findUserRecordByUserTopic(Long topicId,Integer delflag, Integer shareflag);
	/**
	 * 获取分享专题分享数
	 * @param id
	 * @param shareflag
	 * @param delflag
	 * @return
	 */
	@Query("select count(*) from UserRecord  where topic = ?1 and shareflag = ?2 and delflag =?3")
	public Long countByTopic(UserTopic topic,Integer shareflag,Integer delflag);
	@Query("select count(*) from UserRecord  where topic = ?1 and shareflag = ?2 and delflag =?3 and title like %?4%")
	public Long countByTopic(UserTopic topic,Integer shareflag,Integer delflag,String key);
	/**
	 * 清除专题的事件信息	
	 * @param userTopic
	 */
	@Modifying 
	@Query("update UserRecord set delflag =?1 where topic =?2") 
	public void clearRecordById(Integer delflag ,UserTopic userTopic); 
	
	/**
	 * 更具专题id 查询记录
	 */
	@Query("from UserRecord  where topic = ?1 ")
	public List<UserRecord> findRecordByTopicId(UserTopic userTopic);
	
	/**
	 * @author Alice
	 * 查找专题所属事件
	 * @param userTopic
	 * @param delflag
	 * @param shareflag
	 * @return
	 */
	@Query("from UserRecord  where topic = ?1 and delflag = ?2 and shareflag = ?3 and title like %?4% order by occurtime desc")
	public List<UserRecord> findByTopic(UserTopic userTopic, Integer delflag, Integer shareflag, String keywords);
	@Query("from UserRecord  where topic = ?1 and delflag = ?2 and title like %?3% order by occurtime desc")
	public List<UserRecord> findByTopic(UserTopic userTopic, Integer delflag, String keywords);
	
	
	/**
	 * @Description: 按月升序查询
	 * @author yanpeng
	 * @date 2016年3月12日 上午11:07:22
	 * @param status
	 * @param date
	 * @param integer 
	 * @return
	 */
	@Query("from UserRecord  where delflag = ?1 and to_char(occurtime,'yyyy-mm') = ?2 and topic.selectStatus = ?3 and topic.user = ?4 ORDER BY occurtime ASC")
	public List<UserRecord> getListByMonth(Integer delflag, String date, Integer integer,User user);
	/**
	 * @Description: 按月降序查询
	 * @author yanpeng
	 * @date 2016年3月12日 上午11:07:22
	 * @param status
	 * @param date
	 * @param integer 
	 * @return
	 */
	@Query("from UserRecord  where delflag = ?1 and to_char(occurtime,'yyyy-mm') = ?2 and topic.selectStatus = ?3 and topic.user = ?4  ORDER BY occurtime DESC")
	public List<UserRecord> getListByMonthDesc(Integer delflag, String date, Integer integer,User user);
	
	
	/**
	 * @Description: 获取本月以前的数据
	 * @author yanpeng
	 * @date 2016年3月21日 下午2:23:56
	 * @param year
	 * @param month
	 * @param status
	 * @param ids 
	 * @return
	 */
	@Query(nativeQuery=true , value="select max(distinct(extract( month from record.occurtime ))) from trmap_user_record record,trmap_user_topic topics WHERE topics.id=record.topic_id and topics.id in (?1) and extract( YEAR from record.occurtime )= ?2 and extract( month from record.occurtime )< ?3 and topics.select_status= ?4 and topics.shareflag = ?5  and record.delflag = ?6")
	public Integer getPreMonth( Long[] ids,Integer year,Integer month,Integer status,Integer shareflag,Integer delflag);
	/**
	 * @Description: 获取本月以后的数据
	 * @author yanpeng
	 * @date 2016年3月21日 下午2:24:15
	 * @param year
	 * @param month
	 * @param status
	 * @return
	 */
	@Query(nativeQuery=true , value="select min(distinct(extract( month from record.occurtime ))) from trmap_user_record record,trmap_user_topic topics WHERE  topics.id=record.topic_id and topics.id in (?1) and extract( YEAR from record.occurtime )= ?2 and extract( month from record.occurtime )> ?3 and topics.select_status= ?4 and topics.shareflag = ?5  and record.delflag = ?6")
	public Integer getAfterMonth(Long[] ids,Integer year, Integer month,Integer status,Integer shareflag,Integer delflag);
	
	/**
	 * @Description: 获取有记录最小的年份
	 * @author yanpeng
	 * @date 2016年3月21日 下午2:24:31
	 * @param status
	 * @return
	 */
	@Query(nativeQuery=true , value="select min(distinct(extract( year from record.occurtime ))) from trmap_user_record record,trmap_user_topic topics WHERE topics.id=record.topic_id and topics.select_status= ?1")
	public Integer getMinYear(Integer status);
	/**
	 * 
	 */
	@Modifying
	@Query("update UserRecord set shareflag = ?1 where topic_id =?2 and delflag =?3")
	public void upRecordshareflagByTid(Integer shareflag ,Long topicid,Integer delflag);
	
	
	/**
	 * @Description: 查找我收藏的专题下的未删除，且已分享，选中状态事件  升序排列
	 * @author yanpeng
	 * @date 2016年3月26日 下午4:50:38
	 * @param value 删除状态
	 * @param strDate 时间段
	 * @param value2  选中状态
	 * @param ids 收藏专题的id数组
	 * @return
	 */
	@Query("from UserRecord  where delflag = ?1 and to_char(occurtime,'yyyy-mm') = ?2 and topic.selectStatus = ?3 and topic.id in (?4) ORDER BY occurtime ASC")
	public List<UserRecord> getListByMonth(Integer value, String strDate, Integer value2,Long[] ids); 
	
	/**
	 * @Description: 查找我收藏的专题下的未删除，且已分享，选中状态事件，降序排列
	 * @author yanpeng
	 * @date 2016年3月26日 下午4:50:38
	 * @param value 删除状态
	 * @param strDate 时间段
	 * @param value2  选中状态
	 * @param ids 收藏专题的id数组
	 * @return
	 */
	@Query("from UserRecord  where delflag = ?1 and to_char(occurtime,'yyyy-mm') = ?2 and topic.selectStatus = ?3 and topic.id in (?4) ORDER BY occurtime DESC")
	public List<UserRecord> getListByMonthDesc(Integer delflag, String date, Integer integer,Long[] ids);

	/**
	 * @Description: 查询我收藏专题前一月有数据的最大月份
	 * @author yanpeng
	 * @date 2016年3月26日 下午4:52:33
	 * @param ids 收藏专题的id数组
	 * @param year 
	 * @param month
	 * @param status 收藏表的选中状态
	 * @param shareflag 
	 * @return
	 */
	@Query(nativeQuery=true , value="select max(distinct(extract( month from record.occurtime ))) from trmap_user_record record,trmap_user_topic topics,trmap_user_collect collect WHERE topics.id=record.topic_id and collect.topic_id = topics.id and topics.id in (?1) and extract( YEAR from record.occurtime )= ?2 and extract( month from record.occurtime )< ?3 and collect.select_status= ?4 and topics.shareflag = ?5  and record.delflag = 0")
	public Integer getPreMonthCollect( Long[] ids,Integer year,Integer month,Integer status,Integer shareflag);
	
	
	/**
	 * @Description: 查询我收藏专题后一月有数据的最小月份
	 * @author yanpeng
	 * @date 2016年3月26日 下午4:52:33
	 * @param ids 收藏专题的id数组
	 * @param year 
	 * @param month
	 * @param status 收藏表的选中状态
	 * @param shareflag 
	 * @return
	 */
	@Query(nativeQuery=true , value="select min(distinct(extract( month from record.occurtime ))) from trmap_user_record record,trmap_user_topic topics,trmap_user_collect collect WHERE  topics.id=record.topic_id and collect.topic_id = topics.id and topics.id in (?1) and extract( YEAR from record.occurtime )= ?2 and extract( month from record.occurtime )> ?3 and collect.select_status= ?4 and topics.shareflag = ?5  and record.delflag = 0")
	public Integer getAfterMonthCollect(Long[] ids,Integer year, Integer month,Integer status,Integer shareflag);
}