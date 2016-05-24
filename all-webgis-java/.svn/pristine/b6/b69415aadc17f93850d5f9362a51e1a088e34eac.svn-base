package com.trgis.trmap.personal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.personal.model.UserMessage;
import com.trgis.trmap.userauth.model.User;

/**
 * 
 * @Title: UserMessageDao
 * @Description:消息
 * @author liuyan
 * @date 2016年3月25日 下午1:49:15
 *
 */
@Repository
public interface UserMessageDao extends JpaRepository<UserMessage,Long>,JpaSpecificationExecutor<UserMessage>{
    /**
     * @Description:假删除消息
     * @Author liuyan 
     * @Date 2016年3月26日 下午4:50:57
     * @param delflag
     * @param id
     */
	@Modifying
	@Query("update UserMessage set delflag = ?1 where id =?2")
	public void deleteMessage(Integer delflag,Long id); 
	/**
	 * @Description:修改阅读状态
	 * @Author liuyan 
	 * @Date 2016年4月1日 下午6:59:26
	 * @param delflag
	 * @param id
	 */
	@Modifying
	@Query("update UserMessage set readflag = ?1 where id =?2")
	public void changeReadType(Integer readflag,Long id); 
	/**
	 * @Description:
	 * @Author liuyan 
	 * @Date 2016年3月26日 下午4:51:20
	 * @param user
	 * @param readflag
	 * @param msgType
	 * @param delflag
	 * @return
	 */
	@Query(nativeQuery=true , value="from trmap_user_message WHERE user= ?1 and readflag = ?2  and delflag = ?3")
	public String findMessageByUser(User user, Integer readflag,Integer delflag);
   /**
    * @Description:阅读状态统计
    * @Author liuyan 
    * @Date 2016年3月26日 下午6:03:01
    * @param integer
    * @param shareflag
    * @return
    */
	@Query("select count(*) from UserMessage where delflag =?1 and readflag=?2 and user=?3")
	public Long getReadCount(Integer delflag,Integer readflag,User user);
	
	/**
	 * @Description: 取消消息与
	 * @author yanpeng
	 * @date 2016年4月11日 下午3:52:41
	 * @param cids
	 */
	@Modifying
	@Query(nativeQuery=true , value="update trmap_user_message set  delflag = 1  where comment_id in (?1)")
	public void updateMsg(Object[] cids);
	
}
