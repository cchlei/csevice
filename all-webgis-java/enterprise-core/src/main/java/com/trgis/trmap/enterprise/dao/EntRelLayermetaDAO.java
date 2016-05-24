package com.trgis.trmap.enterprise.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.enterprise.model.EntRelLayermeta;
import com.trgis.trmap.enterprise.model.EntUserMap;

/**
 * 企业地图对应元数据层（已发布）
 * 
 * @author Alice
 *
 * 2015年12月10日
 */
@Repository
public interface EntRelLayermetaDAO  extends JpaRepository<EntRelLayermeta,Long>,JpaSpecificationExecutor<EntRelLayermeta>{
	
	@Query("from EntRelLayermeta  where map = ?1")
	public EntRelLayermeta findByEntRelUserMap(EntUserMap entUserMap);
}
