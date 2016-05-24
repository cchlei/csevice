package com.trgis.trmap.userauth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.userauth.model.AccountConfine;

/**
 * @author chenhl
 *	dao 查询记录
 */
@Repository
public interface AccountConfineDao extends JpaRepository<AccountConfine, Long> ,JpaSpecificationExecutor<AccountConfine> {
	
	/**
	 * 统计访问者每天的访问的数量
	 * @param userid
	 * @param isapproved
	 * @return
	 */
	@Query("from AccountConfine u where u.ip = ?1 and u.registType = ?2 and u.recordTime LIKE ?3% order by recordTime desc") 
	List<AccountConfine> findByIp(String recordip,Integer rtype,String strdate);
	
	
	/**
	 * 统计访问者每天的访问的数量
	 * @param userid
	 * @param isapproved
	 * @return
	 */
	@Query("from AccountConfine u where u.ip = ?1 and u.email = ?2 and u.recordTime LIKE ?3% order by recordTime desc") 
	List<AccountConfine> findByEmail(String recordip,String email,String strdates);
}
