package com.trgis.trmap.enterprise.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.enterprise.model.EntRelMapGraphics;
import com.trgis.trmap.enterprise.model.EntUserMap;

/**
 * 企业地图矢量要素（已发布）
 * 
 * @author Alice
 *
 * 2015年12月10日
 */
@Repository
public interface EntRelMapGraphicsDAO  extends JpaRepository<EntRelMapGraphics,Long>,JpaSpecificationExecutor<EntRelMapGraphics>{
	
	@Query("from EntRelMapGraphics  where userMap = ?1 and delflag = ?2")
	public List<EntRelMapGraphics> findByEntRelUserMap(EntUserMap entUserMap,Integer delflag);
	
	@Modifying
	@Query("delete from EntRelMapGraphics  where userMap = ?1")
	public void deleteByEntRelUserMap(EntUserMap entUserMap);
	
}
