package com.trgis.trmap.personal.service;

import java.util.Date;
import java.util.List;

import com.trgis.trmap.personal.exception.UserRecordException;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.userauth.model.User;

/**
 * 事件服务类
 * @author liuyan
 * @date 2016-03-08
 */
public interface UserRecordService {
	
	/**
	 * @Description:创建事件
	 * @Author liuyan 
	 * @Date 2016年3月10日
	 * @param userRecord
	 * @return
	 * @throws UserRecordException
	 */
	public Long createUserRecord(UserRecord userRecord) throws UserRecordException;
	
	/**
	 * @Description:删除事件记录
	 * 设置事件的DELFLAG---del
     * 同时清除所有的相关附件
	 * @Author liuyan 
	 * @Date 2016年3月10日
	 * @param id
	 * @throws UserRecordException
	 */
	public void deleteUserRecord(Long id) throws UserRecordException;
	
	/**
	 * @Description:修改事件记录
	 * @Author liuyan 
	 * @Date 2016年3月10日
	 * @param userRecord
	 * @throws UserRecordException
	 */
	public void editUserRecord(UserRecord userRecord) throws UserRecordException ;

	/**
	 * 根据获取个人事件记录
	 * @param id
	 */
	public UserRecord findUserRecordById(Long id)throws UserRecordException; 
	
	
	public Long findUserRecordByUserTopic(Long topicId,Integer delflag);
	
	/**
	 * @author Alice
	 * 根据专题查找所属事件
	 * @param userTopic
	 * @param delflag
	 * @param shareflag
	 * @return
	 */
	public List<UserRecord> findByTopic(UserTopic userTopic,Integer delflag, Integer shareflag, String keywords);
	/**
	 * @Description: 按照事件条件查询符合用户选择状态的事件数据
	 * @author yanpeng
	 * @date 2016年3月12日 上午11:07:42
	 * @param date
	 * @return
	 * @throws UserRecordException
	 */
	public List<UserRecord> getListByMonth(Date date,User user) throws UserRecordException ;
	
	/**
	 * @Description: 获取相邻月的数据
	 * @author yanpeng
	 * @date 2016年3月15日 下午2:49:21
	 * @param date 时间
	 * @param type 0 前边的月 or 1 后边的月
	 * @param ids 
	 * @return
	 * @throws UserRecordException
	 */
	public Date getListNextMonth(Date date, Integer type,User user,Long[] myids,Long[] collids) throws UserRecordException;
	
	/**
	 * 统计专题下的各种状态数量
	 * @param userTopic
	 * @param key 
	 * @param shareFlag
	 * @return
	 * @throws UserRecordException
	 */
	public Long countByTopic(UserTopic userTopic, Integer shareflag, Integer delflag, String key) throws UserRecordException;
/**
 * @Description:根据专题id修改说有事件的shareflag
 * @Author liuyan 
 * @Date 2016年3月24日 下午12:54:38
 * @param id
 * @param shareflags
 */
	public void updateRecordByTopicId(Long topicid, Integer shareflags);

	/**
	 * @Description: 按月获取我收藏专题的记录列表
	 * @author yanpeng
	 * @date 2016年3月26日 上午10:28:56
	 * @param dateFromString
	 * @param topicids
	 * @return
	 */
	public List<UserRecord> getListByMonth(Date dateFromString,List<UserTopic> topicList) throws UserRecordException ;

}
