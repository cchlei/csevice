package com.trgis.trmap.hstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.hstore.model.EntRelMapProperties;
/**
 * @author Alice
 *
 * 2015年12月11日
 */
@Repository
public interface EntRelMapPropertiesDao extends JpaRepository<EntRelMapProperties, Long>,JpaSpecificationExecutor<EntRelMapProperties>{
	/**
	 * 根据地图矢量id删除矢量扩展属性
	 * 
	 * @param featureId
	 */
	public void deleteByFeatureId(Long featureId);
	/**
	 * 根据地图矢量id删除矢量扩展属性
	 * 
	 * @param featureId
	 */
	public void deleteByMapId(Long mapId);
}
