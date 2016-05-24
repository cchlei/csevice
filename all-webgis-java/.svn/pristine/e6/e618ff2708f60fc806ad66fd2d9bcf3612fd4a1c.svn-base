package com.trgis.trmap.qtuser.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.userauth.model.User;

/**
 * @author doris
 * 个人地图dao
 */
@Repository
public interface UserMapDao extends JpaRepository<UserMap,Long>,JpaSpecificationExecutor<UserMap>{
	
	/**
	 * 分享、审核地图
	 * @param isshare
	 * @param id
	 * @return
	 */
	@Modifying 
	@Query("update UserMap u set u.isshare = ?1 where u.id = ?2") 
	public int shareUserMap(Integer isshare, Long id); 
	/**
	 * 查询出所有未删除个人地图记录
	 * @param mapdelflag
	 * @return
	 */
	public List<UserMap> findUserMapByMapdelflag(Integer mapdelflag);
	/**
	 * 查找用户专属app地图
	 * @param user
	 * @param app
	 * @return
	 */
	public UserMap findUserMapByUserAndApp(User user, Boolean app);

}
