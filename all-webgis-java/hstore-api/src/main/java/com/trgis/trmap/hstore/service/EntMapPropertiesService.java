package com.trgis.trmap.hstore.service;

import java.util.List;

import com.trgis.trmap.hstore.exception.HStoreSQLException;
import com.trgis.trmap.hstore.model.EntMapProperties;
import com.trgis.trmap.hstore.service.query.Conditions;
import com.trgis.trmap.hstore.service.query.Page;
import com.trgis.trmap.hstore.service.query.Sort;

/**
 * 地图属性扩展
 * @author doris
 * @date 2015-09-14
 *
 */
public interface EntMapPropertiesService {
	/**
	 * 保存扩展属性
	 * @param mapProperties
	 */
	public void save(EntMapProperties mapProperties);
	
	/**
	 * 修改矢量扩展属性
	 * @param mapProperties
	 */
	public void edit(EntMapProperties mapProperties);
	
	/**
	 * 根据id获取地图矢量扩展属性
	 * @param id
	 */
	public EntMapProperties findById(Long id); 
	/**
	 * 根据id获取地图矢量扩展属性
	 * @param id
	 */
	public  EntMapProperties findByFeature(Long featureid); 

	/**
	 * 分页查询数据
	 * 
	 * select h from hstore_model h 
	 * 
	 * @param page 分页
	 * @param sort 排序
	 * @param conditions 条件
	 * @param relation 条件的关系 or || and(DEFAULT)
	 * @return
	 * @throws HStoreSQLException 
	 */
	Page queryPage(Page page, Sort sort, List<Conditions> conditions,String relation);
	
	/**
	 * 删除矢量
	 * @param MapGraphics
	 * @return 
	 */   
	public void deleteByFeature(Long featureid);
	
	/**
	 * 删除矢量
	 * @param MapGraphics
	 * @return 
	 */   
	public void deleteBymapId(Long mapId);
	
}
