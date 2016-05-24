package com.trgis.trmap.personal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.personal.model.UserComment;
import com.trgis.trmap.userauth.model.User;

/**
 * @author yanpeng
 * 关于评论的dao
 */
@Repository
public interface UserCommentDao extends JpaRepository<UserComment,Long>,JpaSpecificationExecutor<UserComment>{

	/**
	 * 根据事件获取相关的评论
	 * @param pid 为空
	 * @return 
	 */
	@Query("from UserComment where user_rid =?1 and parent_id is null order by comtime asc ")
	public List<UserComment> getCommentsByRecord(Long id);
	
	/**
	 * 根据事件获取相关的评论
	 * @param pid 不为空 
	 * @return 
	 */
	@Query("from UserComment where user_rid =?1 and parent_id =?2 order by comtime asc ")
	public List<UserComment> getCommentsByRecord(Long id, Long pid);
   /**
    * @Author liuyan 
    * @Date 2016年3月28日 下午4:37:46
    * @param id
    */
	@Modifying
	@Query("delete from UserComment where user_rid =?1") 
	public void clearUserCommentById(Long id);
	
	/**
	 * @Description: 批量删除
	 * @author yanpeng
	 * @date 2016年4月11日 下午12:57:09
	 * @param id 数组
	 */
	@Modifying
	@Query(nativeQuery=true,value="delete from trmap_user_comment where user_rid in (?1)") 
	public void clearUserCommentByIds(Long[] ids);
	
	/**
	 * 统计时间评论数
	 * @param id
	 * @return
	 */
	@Query("select count(*) from UserComment where user_rid =?1")
	public Long countByRecord(Long id);
/**
 * @Description:根据父级id级联删除子集评论
 * @Author liuyan 
 * @Date 2016年4月7日 下午5:37:36
 * @param id
 */
	@Modifying
	@Query(nativeQuery=true , value="delete from trmap_user_comment where parent_id =?1") 
	public void deleteComentBypid(Long id);
	/**
	 * @Description:查询是否有子节点
	 * @Author liuyan 
	 * @Date 2016年4月7日 下午6:53:43
	 * @param id
	 * @return
	 */
	@Query(nativeQuery=true , value="select count(*) from  trmap_user_comment where parent_id =?1")
    public Long findchild(Long id);
	
	/**
	 * @Description: 根据记录的id集合查询所有的评论id
	 * @author yanpeng
	 * @date 2016年4月11日 下午3:30:17
	 * @param ids
	 * @return
	 */
	@Modifying
	@Query(nativeQuery=true , value="select id from  trmap_user_comment where user_rid in (?1)")
	public Object[] getIdsBy(Long[] ids);
	
	/**
	* @Description: 根据pid集合查询所有的评论id
	* @author liuyan
	* @date 2016年4月11日 下午18:30:17
	* @param ids
	* @return
	*/
	@Modifying
	@Query(nativeQuery=true , value="select id from  trmap_user_comment where parent_id = ?1")
	public Object[] getIdsBy(Long pid);

    /**
     * @Description:根据被评论者删除当前评论下的所有的评论
     * @Author liuyan 
     * @Date 2016年4月14日 下午5:14:15
     * @param buser_id
     */
	@Modifying
	@Query(nativeQuery=true , value="delete from trmap_user_comment where buser_id =?1 and parent_id = ?2") 
	public void deleteByBuser(Long  buser_id,Long Parent_id);
	
	

}
