package com.trgis.trmap.enterprise.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.enterprise.model.EntRelUserMap;
/**
 * 企业地图服务(已发布)
 * 
 * @author Alice
 *
 */
public interface EntRelMapService {
	
	/**
	 * 按照id查找
	 * @return
	 */
	public EntRelUserMap findRelUserMapById(Long id);
	/**
	 * 按照mapid查找
	 * @return
	 */
	public EntRelUserMap findRelUserMapByMapId(Long mapid);
	/**
	 * 编辑对象
	 * @return
	 */
	public void editRelUserMap(EntRelUserMap entRelUserMap);
	/**
	 * 对象
	 * @return
	 */
	public void deleteEntRelUserMap(Long id);
	/**
	 * 获取矢量要素列表
	 * @return
	 */
	public Page<EntRelUserMap> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order);
	
	/**
	 * 查询未过期的服务，包含挂起 及 正常
	 * @return
	 */
	List<EntRelUserMap> findNoOutdate(Integer delflag, Integer isonline);
	
	/**
	 * 查询所有已发布的服务数量
	 * @param delflag
	 * @return
	 */
	public Long findcount(Integer delflag, Long user_id);
}
