package com.trgis.trmap.enterprise.service;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.enterprise.model.EntUserMap;

/**
 * 企业地图服务
 * 
 * @author zhangqian
 *
 */
public interface EntMapService {
	
	/**
	 * 创建企业地图
	 * 
	 * @param enterpriseMap
	 * @return
	 */
	public void createEnterpriseMap(EntUserMap enterpriseMap);
	
	/**
	 * 查询用户的地图列表
	 * 
	 * @param userId
	 * @return
	 */
	public String findUserMapList(Long userId);
	
	/**
	 * 按照id查找图层
	 * @param userId
	 * @return
	 */
	public EntUserMap findUserMapById(Long id);
	
	/**
	 * 编辑对象
	 * @param userId
	 * @return
	 */
	public void editUserMap(EntUserMap entUserMap);
	/**
	 * 对象
	 * @param userId
	 * @return
	 */
	public void deleteEntUserMap(Long id);
	/**
	 * 分页查询 .
	 * @param page .
	 * @param hql .
	 * @return Page .
	 */
	public Page<EntUserMap> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order);
	/**
	 * @author Alice
	 * 发布地图
	 * @param map
	 */
	public void releaseUserMap(EntUserMap map);
}
