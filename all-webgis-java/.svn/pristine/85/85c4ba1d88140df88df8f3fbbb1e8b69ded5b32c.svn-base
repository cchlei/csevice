package com.trgis.trmap.qtuser.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.qtuser.model.MapGraphics;
import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.userauth.model.User;

/**
 * 用户地图矢量服务类
 * @author doris
 * @date 2015-09-11
 */

/**
 * @author Administrator
 *
 */
public interface MapGraphicsService {
	/**
	 * 绘制矢量
	 * @param MapGraphics
	 */
	public void save(MapGraphics graphics);
	/**
	 * 修改矢量
	 * @param MapGraphics
	 */
	public void edit(MapGraphics graphics);
	/**
	 * 删除矢量
	 * @param MapGraphics
	 */
	public void delete(Long id);
	/**
	 * 获取所有地图矢量
	 * @return
	 */
	public List<MapGraphics> findAll(); 
	
	/**
	 * 根据id获取地图矢量
	 * @param id
	 */
	public MapGraphics findById(Long id); 
	/**
	 * 根据id获取地图矢量
	 * @param id
	 */
	public  List<MapGraphics> findByUserMap(UserMap userMap);
	/**
	 * @param group
	 * @param pageNo
	 * @param pageSize
	 * @param orderby
	 * @return
	 */
	public Page<MapGraphics> findByConditions(ConditionGroup group, int pageNo, int pageSize, OrderBy orderby);
	/**
	 * @param user 
	 * @return
	 */
	public Integer countGraphics(User user);
}
