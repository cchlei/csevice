package com.trgis.trmap.enterprise.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.enterprise.model.EntRelMapGraphics;
import com.trgis.trmap.enterprise.model.EntUserMap;

/**
 * 企业地图矢量服务(已发布)
 * @author doris
 * @date 2015-12-25
 */
public interface EntRelGraphicsService {
	
	/**
	 * 获取矢量要素列表
	 * @return
	 */
	public Page<EntRelMapGraphics> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order);
	
	/**
	 *按EntRelUserMap查询矢量要素列表
	 */
	public List<EntRelMapGraphics> findByEntRelUserMap(EntUserMap entUserMap);
	
	/**
	 * 删除矢量 属性对应得值
	 * @param 
	 */
	public void delete(Long id);
	/**
	 * @author Alice
	 * 按照map删除
	 * @param 
	 */
	public void deleteByEntRelUserMap(EntUserMap entUserMap);
}
