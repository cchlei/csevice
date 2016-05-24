package com.trgis.trmap.qtuser.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.qtuser.model.BaseMap;

/**基础底图dao
 * 
 * @author Alice
 *
 * 2015年9月28日
 */
@Repository
public interface BaseMapDao extends JpaRepository<BaseMap,Long>, JpaSpecificationExecutor<BaseMap>{
	/**
	 * 根据地图ID超找图层组
	 * @param mapid
	 * @return
	 */
	public List<BaseMap> getBaseMapByMapId(Long mapId);
	
	/**
	 * 删除
	 * @param mapId
	 */
	@Modifying 
	@Query("delete BaseMap where mapId = ?1") 
	public void deleteBaseMapByMapId(Long mapId);
}
