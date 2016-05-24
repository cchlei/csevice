package com.trgis.trmap.enterprise.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.enterprise.model.EntCainfo;
import com.trgis.trmap.userauth.model.User;

@Repository
public interface EntCainfoDao  extends JpaRepository<EntCainfo,Long>,JpaSpecificationExecutor<EntCainfo>{
	
	/**
	 * 修改企业信息认证申请的状态
	 * @param castatus
	 * @param id
	 * @return
	 */
	@Modifying 
	@Query("update EntCainfo e set e.castatus = ?1 where e.id = ?2") 
	public int editCastatus (String castatus, Long id); 
	
	/**
	 * 根据用户id查询用户认证信息
	 * @param castatus
	 * @param id
	 * @return
	 */
	public EntCainfo findByUser (User user);
	
	/**
	 * 根据企业名查找用户认证信息
	 * 
	 */
	public EntCainfo findByEnterpriseName(String enterpriseName);
	
	/**
	 * 根据用户状态和用户id查找认证信息
	 * @param castatus
	 * @param id
	 * @return
	 */
	public EntCainfo findByUserAndCastatus(User user, String castatus);
	
	/**
	 * 根据用户状态查找认证信息
	 * @param castatus
	 * @author mm
	 * @return
	 */
	public List<EntCainfo> findByCastatus(String castatus);
	
	
	
	
}
