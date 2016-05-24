package com.trgis.trmap.personal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.personal.model.UserGroup;

/**
 * @ClassName: UserGroupDao
 * @Description: 分组
 * @author yanpeng
 * @date 2016年3月24日 下午3:49:37
 */
@Repository
public interface UserGroupDao extends JpaRepository<UserGroup,Long>,JpaSpecificationExecutor<UserGroup>{
	
	/**
	 * @Description: 修改分组删除状态为已删除
	 * @author yanpeng
	 * @date 2016年3月25日 上午9:36:31
	 * @param value
	 * @param groupId
	 */
	@Modifying
	@Query("update UserGroup set delflag = ?1 where gid= ?2")
	void updateDelflag(Integer value, Long groupId);

	/**
	 * @Description: 查询当前用户没有删除的分组列表
	 * @author yanpeng
	 * @date 2016年3月25日 下午2:29:21
	 * @param userid
	 * @param value
	 * @return
	 */
	@Query("from UserGroup where user_id= ?1 and delflag = ?2 order by gid asc")
	List<UserGroup> getGroups(Long userid,Integer value);

}
