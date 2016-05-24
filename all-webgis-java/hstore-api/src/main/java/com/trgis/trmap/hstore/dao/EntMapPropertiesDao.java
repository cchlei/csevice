package com.trgis.trmap.hstore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.hstore.model.EntMapProperties;
/**
 * @author Alice
 *
 * 2015年12月11日
 */
@Repository
public interface EntMapPropertiesDao extends JpaRepository<EntMapProperties, Long>,JpaSpecificationExecutor<EntMapProperties>{
	/**
	 * 根据矢量查扩展属性值
	 * @param featureId
	 * @return
	 */
	public  List<EntMapProperties> findByFeatureId(Long featureId);
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
