package com.trgis.trmap.enterprise.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.enterprise.model.EntRelUserMap;
import com.trgis.trmap.enterprise.model.EntUserMap;

/**
 * 企业地图（已发布）
 * 
 * @author Alice
 *
 * 2015年12月10日
 */
@Repository
public interface EntRelUserMapDAO  extends JpaRepository<EntRelUserMap,Long>,JpaSpecificationExecutor<EntRelUserMap>{
	
	public EntRelUserMap findByEmap(EntUserMap emap); 
	
	/**
	 * @author Alice
	 * @param delflag
	 * @param isonline
	 * @return
	 */
	@Query("from EntRelUserMap where delflag = ?1 and isonline <> ?2")
	public List<EntRelUserMap> findNoOutdate(Integer delflag, Integer isonline); 
	
	/**
	 * 统计发布的服务数量
	 * @param userid
	 * @param isapproved
	 * @return
	 */
	@Query("select count(*) from EntRelUserMap where delflag = ?1 and user_id = ?2")
	public Long getcount(Integer delflag, Long user_id);
}
