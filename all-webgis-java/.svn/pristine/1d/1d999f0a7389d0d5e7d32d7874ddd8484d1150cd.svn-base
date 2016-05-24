package com.trgis.trmap.personal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.personal.model.UserAttention;
import com.trgis.trmap.userauth.model.User;

/**
 * @ClassName: UserAttentionDao
 * @Description: 关注好友
 * @author yanpeng
 * @date 2016年3月24日 下午3:49:37
 */
@Repository
public interface UserAttentionDao extends JpaRepository<UserAttention,Long>,JpaSpecificationExecutor<UserAttention>{
	/**
	 * @Description: 查询我关注好友的关注记录
	 * @author yanpeng
	 * @date 2016年3月25日 下午2:26:55
	 * @param id 
	 * @param uid 被关注用户id
	 * @return
	 */
	@Query("from UserAttention where cuser_id= ?1 and buser_id = ?2")
	UserAttention getUserAttention(Long id, Long uid);
	
	
	/**
	 * @Description: 修改关注状态
	 * @author yanpeng
	 * @date 2016年3月25日 下午2:28:11
	 * @param value
	 * @param id
	 */
	@Modifying
	@Query("update UserAttention set atteflag = ?1 where id= ?2")
	void updateAttentionFlag(Integer value,Long id);

	/**
	 * @Description: 修改好友分组
	 * @author yanpeng
	 * @date 2016年3月25日 下午3:32:43
	 * @param groupId
	 * @param cuid
	 * @param buid
	 */
	@Modifying
	@Query("update UserAttention set group_id = ?1 where cuser_id= ?2 and buser_id = ?3")
	void updateGroup(Long groupId, Long cuid, Long buid);
	
	/**
	 * @Description: 批量修改关注好友的分组
	 * @author yanpeng
	 * @date 2016年3月25日 下午4:03:00
	 * @param newid
	 * @param gid
	 */
	@Modifying
	@Query("update UserAttention set group_id = ?1 where group_id= ?2")
	void updateGroups(Long newid,Long gid);
	
	/***
	 * @Description: 模糊查询好友
	 * @author yanpeng
	 * @date 2016年3月30日 下午4:15:24
	 * @param userid
	 * @param name
	 * @param size
	 * @return
	 */
//	@Query(nativeQuery=true,value="SELECT id,name,headimg FROM qtuser_account WHERE NOT EXISTS ( select buser_id from trmap_user_attention where cuser_id = ?1) and id != ?2 and name like ?3 limit ?4")
	@Query(nativeQuery=true,value="SELECT id,name,userName,headimg FROM qtuser_account WHERE id NOT in ( select buser_id from trmap_user_attention where cuser_id = ?1) and id != ?2 and status='ACTIVE' and (name like ?3 or userName like ?4) limit ?5")
	List<Object[]> findUsers(Long userid,Long myid, String name,String userName, Integer size);

	/**
	 * @Description: 获取我关注的好友的id
	 * @author yanpeng
	 * @date 2016年4月5日 下午2:24:51
	 * @param id
	 * @return
	 */
	@Query(nativeQuery=true,value="SELECT buser_id FROM trmap_user_attention where cuser_id = ?1")
	Long[] findUsers(Long id);

	@Query("from UserAttention where cuser=?1 and buser=?2")
	UserAttention check(User cuser, User buser);


	@Query("from UserAttention where group_id = ?1")
	List<UserAttention> getAttentionByGroup(Long gid);
}
