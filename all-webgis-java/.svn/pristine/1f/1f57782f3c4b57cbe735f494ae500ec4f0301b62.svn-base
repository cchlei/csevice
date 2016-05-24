package com.trgis.trmap.enterprise.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.enterprise.model.EntMapGraphics;
import com.trgis.trmap.enterprise.model.EntUserMap;

/**
 * 图层矢量服务层
 * @author doris
 * @date 2015-12-01
 */
public interface EntMapGraphicsService {
	/**
	 *创建矢量要素
	 */
	public void createEntMapGraphics(EntMapGraphics entMapGraphics);
	/**
	 *修改矢量要素
	 */
	public void editEntMapGraphics(EntMapGraphics entMapGraphics);
	/**
	 *按id查询矢量要素
	 */
	public EntMapGraphics findById(Long id);
	
	/**
	 *按key查询矢量要素
	 */
	public EntMapGraphics findByIdentifykey(EntUserMap entUserMap, String identifykey);
	/**
	 *按EntUserMap查询矢量要素列表
	 */
	public List<EntMapGraphics> findByEntUserMap(EntUserMap entUserMap);
	
	/**
	 * 分页查询 .
	 * @param page .
	 * @param hql .
	 * @return Page .
	 */
	public Page<EntMapGraphics> findByEntUserMapConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order);
	
	/**
	 *获的所用 矢量要素列表
	 *@author Administrator chenhl
	 */
	public List<EntMapGraphics> findAllMapGraphics();
	
	/**
	 * 删除矢量 属性对应得值
	 * @param 
	 */
	public void delete(Long id);
	public void deleteByEntUserMap(EntUserMap usermap);
	/**
	 *范围查询内获的所用 矢量要素列表
	 *@author  chenhl
	 */
	public List<EntMapGraphics> findByEntUserMap(String keyword,String geom ,Long mapid,Integer pageSize,Integer pageNum);
	
	/**
	 *范围查询内获的所用 矢量要素总数
	 *@author  chenhl
	 */
	public Long findCountByMap(String keyword, String geom ,Long mapid);
}
