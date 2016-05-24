package com.trgis.trmap.enterprise.service;

import java.util.List;

import com.trgis.trmap.enterprise.model.EntMapGraphics;

/**
 * 企业数据服务
 * 
 * @author zhangqian
 *
 */
public interface EntDataService {

	/**
	 * 批量导入地图数据
	 * 
	 * @param mapId
	 * @param graphics
	 */
	public void importDateToMap(Long mapId, List<EntMapGraphics> graphics);

	/**
	 * 查询指定地图的数据
	 * 
	 * @param mapId
	 * @return
	 */
	public List<EntMapGraphics> findMapGraphics(Long mapId);
}
